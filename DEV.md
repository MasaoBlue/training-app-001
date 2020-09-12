# 勉強用アプリ001 開発メモ



[TOC]

## 開発PC

- Windows10
  - Core-i7、メモリ16GB
  - 残念ながらHDD

## 参考情報など

### 開発用コンテナの設定

#### VSCode Remote Development
今後色んなバージョンのJavaやらNodeやら触りそうな気がするので、VSCodeのRemote-Containerを使って開発に必要なものは全部コンテナ内に押し込めたい。
- https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers

特にディストリビューションにこだわりはないので、軽量と言われるAlpineでやってみる。
一部拡張機能が動かないことがある...っぽいけど何か起きたらその時考える。今の所は問題無く動いている。
- https://code.visualstudio.com/docs/remote/linux

#### .devcontainerでdocker-compose.ymlを使う
自動生成するとDockerfileのみが作成されるが、将来的にDB用のコンテナ等も一緒に起動したりしたいためdocker-compose.ymlを使うようにする
- https://bufferings.hatenablog.com/entry/2020/06/11/233201

#### apk addするもの
- node
- openjdk8
- openjdk11(拡張機能`vscjava.vscode-java-pack`の動作に必要)
- maven(mvnwをインストールする時だけ使う)

#### vscjava.vscode-java-packの参考
vscjava.vscode-java-packをインストールするとJDK11が無いと動かないと言われる
```
Java 11 or more recent is required to run. Please download and install a recent JDK
```

2020/8/22時点で以下にも記載あり
- https://marketplace.visualstudio.com/items?itemName=redhat.java

> Download and install a recent Java Development Kit (latest Java 11 is the current minimum requirement).

devcontainer.jsonに`java.home`でJDK11のインストール先を指定すると解決できる
- https://qiita.com/tukki0210/items/c758e4866d6cfc844069

#### コマンド履歴を残したい
そのままだとコンテナ再起動した時にコマンドの履歴が消えてしまうため、.bash_historyのフォルダを変更した上でvolumeとしてマウントする
- https://antistatique.net/en/we/blog/2019/11/12/tips-docker-keep-your-bash-history

#### コンテナ再起動時にVSCodeServerや拡張機能が再インストールされるのをやめたい
何も指定しない場合、コマンドパレットで`Remote-Containers: Rebuild Container`を選択した際、右下に表示されるステータスが
Starting container > Installing server > Installing extension > Starting server となっていた。

上記のうちInstalling serverとInstalling extensionは再起動時に保持されていて欲しい。

以下を見るとVSCodeServerは`~/.vscode-server`にインストールされているらしく、
`/root/.vscode-server/extensions`をvolumeマウントすれば拡張機能は保持されるようになる。
- https://code.visualstudio.com/docs/remote/containers-advanced
  - Avoiding extension reinstalls on container rebuild を参照

一応`.vscode-server`フォルダ自体をvolume化すればInstalling serverも残らなくなるようだが、上記に記載されてないということはあまり良いやり方ではないと思われるためextensionsフォルダのみをマウントすることにする。

-【参考】.vscode-serverをマウントした場合のissueなど
  - https://github.com/microsoft/vscode-remote-release/issues/1153
  - https://stackoverflow.com/questions/58765127/permanently-install-vs-codes-server-in-container

#### Globalのnode_modulesにインストールしたコマンドをvolumeマウントしたい
以下はnpxを使う前提なら考えなくて良い。メモとして残しておく

`npm install -g`した場合、インストール先は`/usr/lib/node_modules`になる。
ただ、実際にPATHが通っているのは/usr/binで、installコマンドの実行時にシンボリックリンクが作られるようになっている。

例)create-react-app実行時のログ
```
$ npm install -g create-react-app
/usr/bin/create-react-app -> /usr/lib/node_modules/create-react-app/index.js
+ create-react-app@3.4.1
updated 1 package in 5.168s
```

このため`/usr/lib/node_modules`だけをvolumeにしても再起動するとシンボリックリンクが消えてしまうので駄目。

- 案
  1. 必要になったら都度npm installを実行する
  2. Dockerfileでnpm install -gを実行して必要なものを入れておく
  3. /usr/binを名前付きvolumeにマウントする

3は必要のないものまでmountしてしまうためやりたくない。
直近必要なのはcreate-react-appくらいで、一度プロジェクトを作ってしまえば
あとはlocalに入れることになるはずなので、とりあえず1で進める。

#### 参考
- [Dockerコンテナ内でのnpm installを改善してみる | 69log](https://blog.kazu69.net/2016/05/02/npm-install-speedup-in-docker/)

### Backend
#### mavenの親プロジェクト作成
以下を参考にpom.xmlを作る
- https://spring.pleiades.io/guides/gs/multi-module/

backendのアプリはSpring Initializrで作成する
- https://start.spring.io/
  - Dependenciesは以下を選択
    - Spring Web
    - Spring Data JPA
    - Spring Boot DevTools

#### .gigignore
以下でJavaとVisualStudioCodeだけを指定して生成
- https://www.toptal.com/developers/gitignore

#### maven wrapper
VSCodeのターミナルで以下を実行

```sh
mvn -N io.takari:maven:wrapper
```

#### VSCode設定
ソースコードを開いた際に右下に以下が表示されることがあった

```
Enable Semantic highlighting for Java by default?
```

最近オプションが追加されたらしい。trueにしてみた(.vscode/settings.jsonに記載)
- https://github.com/redhat-developer/vscode-java/wiki/Semantic-Highlighting


### Frontend
#### プロジェクト作成とvolumeマウントの話
VSCodeのターミナルで以下を実行すると重たくて終わらなかったりする。

```sh
npx create-react-app training-app-001-frontend --template typescript
```

以下のような感じで、ホスト側からmountしてるフォルダでnpm installすると大量のファイルを同期しようとして遅くなる感じらしい。
- [【JavaScript】Docker上でのnpm/yarnの操作を10倍早くする方法 | ゆとって生きたい。](https://tkkm.tokyo/post-495/)

対処法としてはnode_modulesを名前付きvolumeに指定すれば良い...んだけど、volume指定するとコンテナ起動した時点でnode_modulesフォルダが自動作成されてしまい、その状態でcreate-react-appすると空フォルダじゃねえぞオラアって怒られるので、とりあえず今回は以下で対応。イケてないけどここに時間はかけたくない感じ
1. ホスト側でcreate-react-appする
2. docker-compose.ymlにnode_modulesディレクトリの名前つきvolumes指定をする
3. コンテナ起動した後でVSCodeのターミナルを開いてコンテナ内で再度`npm install`を実行する

### npm startしたらtypescriptが無くて怒られる
```
It looks like you're trying to use TypeScript but do not have typescript installed.
Please install typescript by running yarn add typescript.
If you are not trying to use TypeScript, please remove the tsconfig.json file from your package root (and any TypeScript files).
```

create-react-appしたらdependenciesにtypescriptが入るのかと思ってたけどそうではないらしい。
という訳で以下を実行
```
npm i -D typescript
```

### @typesが無くて怒られる
```
TypeScript error in /workspace/training-app-001-frontend/src/App.tsx(1,19):
Could not find a declaration file for module 'react'. '/workspace/training-app-001-frontend/node_modules/react/index.js' implicitly has an 'any' type.
  Try `npm install @types/react` if it exists or add a new declaration (.d.ts) file containing `declare module 'react';`  TS7016
```

@typesも入れないと怒られるらしい。これcreate-react-appで勝手に入れてくれなかったっけ？

```
npm i -D @types/react @types/react-dom
```

