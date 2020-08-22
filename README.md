# 勉強用アプリ001

## やったこと
- VSCodeを使ってDockerコンテナ上でSpringBootアプリのデバッグ実行ができるようにする

## 開発環境起動方法

```sh
# VSCode拡張機能インストール
code --install-extension ms-vscode-remote.remote-containers
code --install-extension ms-azuretools.vscode-docker

# ソースコードclone
git clone https://github.com/MasaoBlue/training-app-001.git
cd training-app-001

# VSCodeを起動
code .
```

VSCode起動後以下を実施

- `Ctrl + Shift + P`を押し、コマンドパレットから`Remote-Containers: Rebuild and Reopen in Container`を選択
  - VSCodeがリロードされ、TERMINALが起動するまで待つ
- `F5`キーを押してアプリを起動
- ブラウザで`http://localhost:8080`にアクセス
