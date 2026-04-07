# 构建与验证记录

本文档记录标准化治理过程中已经实际执行过的构建、启动和接口验证结果。

## 2026-04-03

### 环境前置检查

执行命令：

```powershell
powershell -ExecutionPolicy Bypass -File .\script\bin\check-env.ps1
```

结果：

- `java` 可用
- `mvn` 不在 PATH
- 仓库未包含 Maven Wrapper
- `docker`、`mysql`、`redis-cli` 不在 PATH

结论：

- 原始阻塞项是本机缺少 Maven，不是项目本身无法编译

### 本地 Maven 方案

已在仓库内落地：

- `E:/flower-sharing/.tools/apache-maven-3.9.14`
- `E:/flower-sharing/.mvn-local-settings.xml`
- `E:/flower-sharing/script/bin/mvn-local.ps1`

用途：

- 将 Maven 本地仓库固定到 `E:/flower-sharing/.m2/repository`
- 将 `user.home` 固定到工作区，绕过注解处理器向默认用户目录写文件的问题

### 编译验证

执行命令：

```powershell
.\script\bin\mvn-local.ps1 -q -DskipTests compile
```

结果：

- 编译通过

### 打包验证

执行命令：

```powershell
.\script\bin\mvn-local.ps1 -pl ruoyi-admin -am -DskipTests package
```

结果：

- `ruoyi-admin` 及其依赖模块打包通过

### 主应用启动与 OpenAPI 验证

验证事实：

- 使用 `dev` profile 启动 `ruoyi-admin`
- 成功连接测试 MySQL
- 成功连接测试 Redis
- `127.0.0.1:8080` 可监听
- `GET /v3/api-docs` 返回 `200`

运行时补充结论：

- Spring Boot Admin 未在线只会产生注册告警，不阻塞主应用启动

### 第一批公开 API 冒烟验证

已通过的接口：

- `GET /v3/api-docs`
- `GET /auth/isLogin`
- `GET /flowerapplet/category/allList`
- `GET /flowerapplet/product/list?pageNum=1&pageSize=1`
- `GET /flowerapplet/announcement/list?pageNum=1&pageSize=1`
- `GET /flowerapplet/sku/list?pageNum=1&pageSize=1`

结论：

- 当前测试环境下，主应用已经具备“真实数据库 + 真实 API”的最小回归条件

## 2026-04-07

### 第二批只读公开接口扩展

新增脚本化能力：

- 启动后不再固定死等 55 秒，而是轮询 `/v3/api-docs` 直到应用真正就绪
- 将第一批“列表类探测”扩展为“列表 + 详情”
- 详情接口通过解析真实响应中的主键自动生成探测路径

当前脚本覆盖：

- 分类列表 / 分类详情
- 商品列表 / 商品详情 / 按分类查询
- 公告列表 / 公告详情
- SKU 列表 / SKU 详情
- 商品详情列表 / 商品详情详情 / 按 SKU 查询商品详情
- 商品和 SKU 的颜色、等级只读接口

执行入口：

```powershell
powershell -ExecutionPolicy Bypass -File .\script\bin\smoke-api.ps1
```

结论：

- 当前 API 冒烟脚本已经从“最小探活”升级为“公开只读查询回归”
- 后续可以在这个基础上继续扩受保护接口，而不需要重写整套脚本

### 第二批只读公开接口实际验证结果

执行命令：

```powershell
powershell -ExecutionPolicy Bypass -File .\script\bin\smoke-api.ps1
```

验证结果：

- OpenAPI 探测通过
- 认证探测通过
- 分类列表与分类详情通过
- 商品列表、商品详情、按分类查询通过
- 公告列表与公告详情通过
- SKU 列表与 SKU 详情通过
- 商品详情列表、详情查询、按 SKU 查询通过
- 商品和 SKU 的颜色、等级只读接口通过

补充说明：

- 详情接口主键来自列表响应自动解析
- 脚本已改为通过 `/v3/api-docs` 轮询等待服务真正就绪，而不是固定死等
