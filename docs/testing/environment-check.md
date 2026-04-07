# 环境检查入口

本文档用于补齐“构建与启动验证之前的前置检查入口”。

目标：

- 让新接手成员先检查环境，再执行编译或启动
- 让 AI 在尝试构建前先确认本地条件是否满足
- 将“环境缺失”和“项目失败”区分开

## 1. 当前标准入口

PowerShell：

```powershell
.\script\bin\check-env.ps1
```

如遇 PowerShell 执行策略阻止脚本，可使用一次性绕过方式：

```powershell
powershell -ExecutionPolicy Bypass -File .\script\bin\check-env.ps1
```

说明：

- 该脚本只做只读检查，不修改系统环境
- 当前用于检查 `java`、`mvn`、`mvnw.cmd`、`docker`、`mysql`、`redis-cli` 是否可用
- 该脚本不会启动服务，也不会执行编译

## 2. 当前判断规则

通过时关注：

- `java` 可用
- `mvn` 可用，或仓库存在 `mvnw.cmd`
- 如需本地联调，建议同时具备 `docker`、`mysql`、`redis-cli`

说明：

- 对当前仓库，`Maven CLI` 或 `Maven Wrapper` 至少应具备一项，否则无法稳定执行编译验证
- `mysql` 和 `redis-cli` 缺失不一定阻止文档整理，但通常会阻止完整联调

## 3. 推荐使用顺序

1. 先执行 `.\script\bin\check-env.ps1`
2. 若 Maven 条件满足，再执行 `mvn -q -DskipTests compile`
3. 若打包产物可用，再执行对应 `java -jar ...`
4. 启动后按 `docs/testing/startup-checklist.md` 验证

## 4. 当前已知事实

按当前治理过程已确认：

- 当前会话环境 `java` 可用
- 当前会话环境 `mvn` 不在 PATH 中
- 仓库当前未包含 Maven Wrapper
- 当前会话环境 `docker`、`mysql`、`redis-cli` 也未在 PATH 中

因此，当前构建验证的主要阻塞项是“环境前置条件未满足”，而不是“仓库已经编译失败”。

补充进展：

- 已在仓库内下载本地 Maven：`E:/flower-sharing/.tools/apache-maven-3.9.14`
- 已通过仓库内 `settings.xml` 与本地仓库目录绕过默认不可写的用户目录
- 当前已验证可用的构建入口为 `.\script\bin\mvn-local.ps1 -q -DskipTests compile`
- 当前已验证 `dev` 环境下主应用可启动，且 `/v3/api-docs` 可访问
