#-------------------------------------------------------------------------------------------------------------
# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License. See https://go.microsoft.com/fwlink/?linkid=2090316 for license information.
#-------------------------------------------------------------------------------------------------------------

# Update the VARIANT arg in devcontainer.json to pick an Alpine version: 3.10, 3.11, 3.12
# To fully customize the contents of this image, use the following Dockerfile instead:
# https://github.com/microsoft/vscode-dev-containers/tree/v0.128.0/containers/alpine/.devcontainer/base.Dockerfile
ARG VERSION
FROM mcr.microsoft.com/vscode/devcontainers/base:0-alpine-${VERSION}

RUN apk update \
  && apk add --no-cache npm openjdk8 openjdk11 maven

# javaコマンドはopenjdk8を使うよう設定
RUN ln -nfs /usr/lib/jvm/java-1.8-openjdk /usr/lib/jvm/default-jvm
