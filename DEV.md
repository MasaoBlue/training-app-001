# 勉強用アプリ001
開発関連のメモ

## 開発PC
- Windows10
  - Core-i7、メモリ16GB

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


### ソースコード
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
### VSCode設定
ソースコードを開いた際に以下が表示されることがあった

```
Enable Semantic highlighting for Java by default?
```

最近オプションが追加されたらしい。trueにしてみた(.vscode/settings.jsonに記載)
- https://github.com/redhat-developer/vscode-java/wiki/Semantic-Highlighting

