# 勉強用アプリ001

## 開発PC
- Windows10
  - Core-i7、メモリ16GB

## 参考にした情報など
### 開発用コンテナ
今後色んなバージョンのJavaやらNodeやら触りそうな気がするので、VSCodeのRemote-Containerを使って開発に必要なものは全部コンテナ内に押し込めたい。
https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers

特にディストリビューションにこだわりはないので、軽量と言われるAlpineでやってみる。
一部拡張機能が動かないことがある...っぽいけど何か起きたらその時考える。
https://code.visualstudio.com/docs/remote/linux

### 親プロジェクト作成
pom.xmlから作る
https://spring.pleiades.io/guides/gs/multi-module/

### .gigignore
JavaとVisualStudioCodeだけを指定
https://www.toptal.com/developers/gitignore

### apk addするもの
- node
- openjdk8
- openjdk11(Javaのデバッグに必要)

### maven wrapper
VSCodeのターミナルで実行

```sh
mvn -N io.takari:maven:wrapper
```

## Windows側で実行したコマンド
Gitbashで実行

```sh
# VSCode拡張機能インストール
code --install-extension ms-vscode-remote.remote-containers
```

