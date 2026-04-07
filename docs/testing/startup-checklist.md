# 启动检查清单

本清单用于在改动后快速确认系统是否仍具备基本可运行性。

## 1. 构建检查

建议命令：

```bash
mvn -q -DskipTests compile
```

如需打包验证：

```bash
mvn clean package -DskipTests
```

检查项：

- 根工程依赖可解析
- 所有启用模块可编译
- 主应用可打包
- 扩展服务可打包

## 2. 主应用启动检查

目标模块：

- `ruoyi-admin`

建议检查：

- 应用能正常启动
- 端口监听正常
- Swagger 页面可访问
- `/v3/api-docs` 可访问

可记录结果：

- 启动是否成功
- 失败堆栈摘要
- 是否受数据库/Redis 依赖影响

## 3. 监控中心启动检查

目标模块：

- `ruoyi-monitor-admin`

建议检查：

- 应用能正常启动
- `9090` 端口可访问
- `/admin` 页面可访问

## 4. SnailJob 服务端启动检查

目标模块：

- `ruoyi-snailjob-server`

建议检查：

- 应用能正常启动
- `8800` 端口可访问
- `17888` Netty 端口已监听

## 5. IDE 运行配置

仓库当前已有 `.run/` 配置：

- `ruoyi-server.run.xml`
- `ruoyi-monitor-admin.run.xml`
- `ruoyi-snailjob-server.run.xml`

说明：

- 可作为本地开发运行入口参考
- 仍需后续确认是否与当前真实运行方式一致

## 6. 启动失败时的记录建议

每次启动失败建议记录：

- 哪个服务失败
- 使用的环境配置
- 是否缺数据库/Redis/外部服务
- 失败日志摘要
- 是否属于本次改动引起
