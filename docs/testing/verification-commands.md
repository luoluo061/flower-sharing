# 验证命令清单

本文件汇总当前仓库后续可用于验证改动的命令。

目标：

- 让人工验证有统一入口
- 让 AI 能明确知道可执行验证手段
- 为后续脚本化和自动化铺路

## 1. 编译验证

前置条件：

- 本机已安装 Maven，且 `mvn` 或 `mvn.cmd` 可用
- 或仓库提供 Maven Wrapper

当前仓库现状：

- 本仓库未包含 Maven Wrapper
- 当前会话环境已确认 `java` 可用，但 `mvn` 不在 PATH 中

结论：

- 编译与打包验证依赖外部 Maven 安装
- 后续工程标准化中，建议补充 Maven 环境要求说明，或评估是否引入 Maven Wrapper

### 根工程编译

```bash
mvn -q -DskipTests compile
```

用途：

- 验证启用模块是否仍可编译
- 验证依赖是否完整
- 适合作为大多数文档、脚本、轻度代码改动后的最低检查

### 根工程打包

```bash
mvn clean package -DskipTests
```

用途：

- 验证主应用和扩展服务是否可打包
- 适合启动方式和构建方式调整后使用

## 2. 测试验证

### 运行现有测试

```bash
mvn test
```

说明：

- 当前仓库测试偏示例性质
- 不能替代业务回归

### 仅跑指定测试

```bash
mvn -Dtest=DemoUnitTest test
```

说明：

- 适合作为测试框架是否可运行的快速检查
- 不代表业务能力正确

## 3. 主服务启动验证

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

## 4. 启动后检查建议

### 主应用

- 访问 `/swagger-ui.html`
- 访问 `/v3/api-docs`
- 验证 `/auth` 登录链路

### 监控中心

- 访问 `/admin`

### SnailJob 服务端

- 确认 `8800` 和 `17888` 端口监听

## 5. `.run/` 目录说明

当前 `.run/` 下是 IntelliJ 的 Docker build 配置，不是标准 Java 本地运行配置。

已识别文件：

- `ruoyi-server.run.xml`
- `ruoyi-monitor-admin.run.xml`
- `ruoyi-snailjob-server.run.xml`

结论：

- 可作为镜像构建参考
- 不能直接替代本地启动与回归命令清单

## 6. 当前最推荐的最小验证组合

对低风险改动：

1. `mvn -q -DskipTests compile`

对启动、配置、脚本相关改动：

1. `mvn clean package -DskipTests`
2. 启动对应服务
3. 按 [startup-checklist.md](/E:/flower-sharing/docs/testing/startup-checklist.md) 检查

对业务代码改动：

1. 编译或打包
2. 启动主应用
3. 按 [smoke-scenarios.md](/E:/flower-sharing/docs/testing/smoke-scenarios.md) 做关键冒烟
4. 按 [regression-checklist.md](/E:/flower-sharing/docs/testing/regression-checklist.md) 回归
