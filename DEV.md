# 勉強用アプリ001

## 開発PC
- Windows10
  - Core-i7、メモリ16GB

## 参考にした情報など
### 開発用コンテナ
今後色んなバージョンのJavaやらNodeやら触りそうな気がするので、VSCodeのRemote-Containerを使って開発に必要なものは全部コンテナ内に押し込めたい。
- https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers

特にディストリビューションにこだわりはないので、軽量と言われるAlpineでやってみる。
一部拡張機能が動かないことがある...っぽいけど何か起きたらその時考える。
- https://code.visualstudio.com/docs/remote/linux

### 親プロジェクト作成
pom.xmlから作る
- https://spring.pleiades.io/guides/gs/multi-module/

### .gigignore
以下でJavaとVisualStudioCodeだけを指定してgenerate
- https://www.toptal.com/developers/gitignore

### apk addするもの
- node
- openjdk8
- openjdk11(Javaのデバッグに必要)

####
VSCode拡張でvscjava.vscode-java-packをインストールするとJDK11が無いと動かないと言われる
```
Java 11 or more recent is required to run. Please download and install a recent JDK
```

2020/8/22時点で以下にも記載あり
- https://marketplace.visualstudio.com/items?itemName=redhat.java

> Download and install a recent Java Development Kit (latest Java 11 is the current minimum requirement).

### maven wrapper
VSCodeのターミナルで以下を実行

```sh
mvn -N io.takari:maven:wrapper
```
### VSCode設定
```
Enable Semantic highlighting for Java by default?
```

最近追加されたらしい。trueにしてみた
- https://github.com/redhat-developer/vscode-java/wiki/Semantic-Highlighting

### コマンド履歴を残したい
~/.bash_historyのフォルダを変更した上でvolumeマウントする
- https://antistatique.net/en/we/blog/2019/11/12/tips-docker-keep-your-bash-history

## Windows側で実行したコマンド
Gitbashで実行

```sh
# VSCode拡張機能インストール
code --install-extension ms-vscode-remote.remote-containers
code --install-extension ms-azuretools.vscode-docker
```

