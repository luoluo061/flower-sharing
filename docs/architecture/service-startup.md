# 服务启动与关系说明

## 1. 当前可识别服务

### 主应用

- 模块：`ruoyi-admin`
- 启动类：`org.dromara.DromaraApplication`
- 默认端口：`8080`
- 角色：当前后台管理接口和业务接口主入口

### 监控中心

- 模块：`ruoyi-extend/ruoyi-monitor-admin`
- 启动类：`org.dromara.monitor.admin.MonitorAdminApplication`
- 默认端口：`9090`
- 角色：Spring Boot Admin 监控中心

### SnailJob 服务端

- 模块：`ruoyi-extend/ruoyi-snailjob-server`
- 启动类：`org.dromara.snailjob.SnailJobServerApplication`
- 默认端口：`8800`
- Netty 端口：`17888`
- 角色：任务调度/重试等扩展能力服务

## 2. 当前主应用依赖关系

从配置上看，`ruoyi-admin` 会作为 Spring Boot Admin 客户端接入监控中心，并存在 SnailJob 客户端配置。

因此本地联调时建议按以下顺序理解系统：

1. 数据库、Redis 等基础依赖
2. `ruoyi-monitor-admin`
3. `ruoyi-snailjob-server`
4. `ruoyi-admin`

说明：

- 当前 `snail-job.enabled` 在主应用 dev/prod 配置中为 `false`，是否生产启用需后续确认。
- 如果只做主业务开发，通常先保证 `ruoyi-admin` 可单独启动。

## 3. 当前标准启动命令

### 编译

```bash
mvn -q -DskipTests compile
```

### 打包

```bash
mvn clean package -DskipTests
```

### 启动主应用

```bash
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

### 启动监控中心

```bash
java -jar ruoyi-extend/ruoyi-monitor-admin/target/ruoyi-monitor-admin.jar
```

### 启动 SnailJob 服务端

```bash
java -jar ruoyi-extend/ruoyi-snailjob-server/target/ruoyi-snailjob-server.jar
```

## 4. 当前脚本情况

仓库已有：

- `script/bin/ry.bat`
- `script/bin/ry.sh`

局限：

- 仅针对 `ruoyi-admin.jar`
- 未覆盖监控中心和 SnailJob
- 未体现环境、端口、依赖顺序
- 只适合作为历史脚本参考，不应视为完整标准启动方案

## 5. 待确认事项

- 生产环境是否强依赖 `ruoyi-monitor-admin`
- `ruoyi-snailjob-server` 是否必须部署
- 是否存在前端网关或 Nginx 必须前置的访问拓扑
- 微信支付回调和文件上传是否依赖特定公网域名
