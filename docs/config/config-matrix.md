# 配置文件矩阵

## 1. 配置文件分布

### 主应用

- `ruoyi-admin/src/main/resources/application.yml`
- `ruoyi-admin/src/main/resources/application-dev.yml`
- `ruoyi-admin/src/main/resources/application-prod.yml`

### 监控中心

- `ruoyi-extend/ruoyi-monitor-admin/src/main/resources/application.yml`

### SnailJob 服务端

- `ruoyi-extend/ruoyi-snailjob-server/src/main/resources/application.yml`
- `ruoyi-extend/ruoyi-snailjob-server/src/main/resources/application-dev.yml`
- `ruoyi-extend/ruoyi-snailjob-server/src/main/resources/application-prod.yml`

## 2. 当前分层现状

### `application.yml`

主要承载：

- 通用 Spring Boot 配置
- Web 服务基础配置
- 日志配置
- 权限与鉴权基础配置
- Swagger / SpringDoc
- SSE / WebSocket
- Flowable
- API 加解密
- 部分业务基础配置

问题：

- 公共配置中混入了明显业务型配置
- 存在密钥类配置
- Swagger 分组仍明显带有鲜花遗留结构

### `application-dev.yml`

主要承载：

- 数据源
- Redis
- Redisson
- 邮件
- 短信
- 第三方授权
- 微信小程序
- 微信支付
- SnailJob 客户端

问题：

- 存在数据库、Redis、支付等硬编码
- 保留多套注释掉的历史地址
- 开发环境与测试环境边界不清

### `application-prod.yml`

主要承载：

- 与 dev 类似的环境差异配置

问题：

- 与 dev 重复度高
- 仍存在硬编码凭据
- 配置职责没有清晰边界

## 3. 当前已识别风险

- 敏感信息直接存在于仓库
- 环境切换依赖手工注释
- 公共配置和环境差异配置边界混乱
- 扩展服务配置和主应用配置未形成统一说明

## 4. 本轮治理结论

当前阶段不直接改动配置值，只先建立说明和治理边界。

后续配置治理建议：

1. 公共配置只保留真正公共的开关和框架配置
2. 环境差异项下沉到 `dev/prod`
3. 密钥、口令、第三方凭据逐步迁移到环境变量或外部配置
4. 禁止继续通过注释切换数据库地址
5. 为主应用和扩展服务分别补充配置说明

## 5. 待确认事项

- 当前生产真实生效的是哪一套数据库、Redis、支付参数
- 是否已有外部配置中心或运维注入方式
- 微信支付、短信、OSS 是否允许后续改为环境变量注入
