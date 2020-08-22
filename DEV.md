# 開発PC
- Windows10
  - Core-i7、メモリ16GB

# 参考にしたものとか
## 開発用コンテナ
今後色んなバージョンのJavaやらNodeやら触りそうな気がするので、VSCodeのRemote-Containerを使って開発に必要なものは全部コンテナ内に押し込めたい。
https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers

特にディストリビューションにこだわりはないので、軽量と言われるAlpineでやってみる。
一部拡張機能が動かないことがある...っぽいけど何か起きたらその時考える。
https://code.visualstudio.com/docs/remote/linux

## 各種コマンドメモ
Gitbashで実行したもの貼り付け
```sh
# VSCode拡張機能インストール
code --install-extension ms-vscode-remote.remote-containers
```

