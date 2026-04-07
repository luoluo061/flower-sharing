# 构建与运行入口说明

本文件用于定义当前仓库的构建、打包、Jar 运行和 Docker 构建入口。

目标：

- 让新接手成员知道“应该怎么构建”
- 让 AI 知道“哪些入口是正式的，哪些只是历史辅助手段”
- 避免后续在脚本、Docker、IDE 配置之间混淆

## 1. 当前可执行 Jar 模块

从 Maven 模块和现有 `target/` 目录看，当前可识别的可执行服务有 3 个：

### 主应用

- 模块：`ruoyi-admin`
- 产物：`ruoyi-admin/target/ruoyi-admin.jar`

### 监控中心

- 模块：`ruoyi-extend/ruoyi-monitor-admin`
- 产物：`ruoyi-extend/ruoyi-monitor-admin/target/ruoyi-monitor-admin.jar`

### SnailJob 服务端

- 模块：`ruoyi-extend/ruoyi-snailjob-server`
- 产物：`ruoyi-extend/ruoyi-snailjob-server/target/ruoyi-snailjob-server.jar`

说明：

- 当前三个模块目录下都已存在 `target/` 和历史打包产物
- 这说明仓库曾经做过本地或服务器侧构建
- 但由于当前环境没有 Maven，不代表本会话已重新验证这些产物可再现

## 2. 当前推荐的构建入口

### 标准构建入口

推荐仍以 Maven 为准：

```bash
mvn -q -DskipTests compile
```

```bash
mvn clean package -DskipTests
```

说明：

- 这是当前最接近标准工程入口的方式
- 但仓库没有 Maven Wrapper，因此依赖本机已安装 Maven

## 3. 当前 Jar 运行入口

### 主应用

```bash
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

### 监控中心

```bash
java -jar ruoyi-extend/ruoyi-monitor-admin/target/ruoyi-monitor-admin.jar
```

### SnailJob 服务端

```bash
java -jar ruoyi-extend/ruoyi-snailjob-server/target/ruoyi-snailjob-server.jar
```

## 4. 当前脚本入口

仓库脚本仅覆盖主应用：

- `script/bin/ry.bat`
- `script/bin/ry.sh`

结论：

- 脚本不是完整标准启动入口
- 只能视为 `ruoyi-admin.jar` 的历史辅助脚本

## 5. 当前 `.run/` 入口

`.run/` 下已有：

- `ruoyi-server.run.xml`
- `ruoyi-monitor-admin.run.xml`
- `ruoyi-snailjob-server.run.xml`

当前判断：

- 这些不是普通 Java Application 运行配置
- 而是 IntelliJ 的 Docker build 配置
- 可作为镜像构建提示，但不能替代标准启动入口

## 6. 当前 Dockerfile 策略

### `ruoyi-admin/Dockerfile`

- 基于 `openjdk:17`
- 镜像不直接复制 jar
- 通过挂载 `app.jar` 的方式运行

### `ruoyi-monitor-admin/Dockerfile`

- 直接复制 `target/ruoyi-monitor-admin.jar`
- 容器内运行 `app.jar`

### `ruoyi-snailjob-server/Dockerfile`

- 直接复制 `target/ruoyi-snailjob-server.jar`
- 容器内运行 `app.jar`

结论：

- 当前主应用与扩展服务的镜像构建方式不一致
- 后续如统一镜像交付方式，应先确认线上当前依赖哪种模式

## 7. 当前建议的标准入口优先级

建议优先级如下：

1. Maven 构建入口
2. Jar 启动入口
3. Dockerfile / Docker Compose 入口
4. `script/bin` 历史脚本
5. `.run/` Docker build 配置

## 8. 后续治理建议

### 低风险建议

- 继续把构建与启动入口文档化
- 明确每个入口的适用范围
- 明确本机运行、服务器运行、Docker 运行的关系

### 中风险建议

- 评估是否引入 Maven Wrapper
- 评估是否统一三个 Dockerfile 的构建策略
- 评估是否补标准本地运行配置

## 9. 待确认事项

- 当前线上是否通过 Jar 直接运行还是通过 Docker 运行
- 主应用 Dockerfile 使用挂载 jar 的策略是否仍在生产使用
- 是否存在仓库外运维脚本统一做打包和部署
