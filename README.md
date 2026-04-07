# flower-sharing

单商户标准商城后端基座清洗中的工程仓库。

当前项目基于 RuoYi-Vue-Plus 底座演进，现阶段的治理目标不是继续扩展鲜花垂直业务，而是将仓库逐步清洗为一个可维护、可交接、可部署、便于 AI 理解和辅助改造的单商户标准商城系统。

## 当前定位

- 主形态：网页后台管理端 + 微信小程序 C 端
- 当前状态：保留底座，清洗鲜花遗留，重构商城核心域
- 改造原则：先文档和结构治理，再做配置、脚本、检测与代码层整理

## 技术栈

- Java 17
- Maven 多模块工程
- Spring Boot 3.2.x
- MyBatis-Plus
- Sa-Token
- Redis / Redisson
- Spring Boot Admin
- SnailJob
- MySQL

## 模块结构

```text
flower-sharing
├─ ruoyi-admin                 # 主应用启动模块
├─ ruoyi-common                # 公共基础能力模块
├─ ruoyi-extend                # 扩展独立服务
│  ├─ ruoyi-monitor-admin      # 监控中心
│  └─ ruoyi-snailjob-server    # SnailJob 服务端
├─ ruoyi-modules               # 业务模块聚合
│  ├─ ruoyi-system             # 系统管理能力
│  ├─ ruoyi-generator          # 代码生成能力
│  ├─ ruoyi-demo               # 示例模块
│  └─ ruoyi-flower            # 当前业务主模块，含鲜花遗留与商城逻辑
└─ script                      # 启动脚本、SQL、Docker、流程资源
```

建议先阅读：

- [docs/index.md](/E:/flower-sharing/docs/index.md)
- [docs/architecture/module-map.md](/E:/flower-sharing/docs/architecture/module-map.md)
- [docs/architecture/module-status.md](/E:/flower-sharing/docs/architecture/module-status.md)
- [docs/architecture/module-retention.md](/E:/flower-sharing/docs/architecture/module-retention.md)
- [docs/architecture/api-surface.md](/E:/flower-sharing/docs/architecture/api-surface.md)
- [docs/architecture/environment-requirements.md](/E:/flower-sharing/docs/architecture/environment-requirements.md)
- [docs/architecture/maven-entry-strategy.md](/E:/flower-sharing/docs/architecture/maven-entry-strategy.md)
- [docs/architecture/build-and-run.md](/E:/flower-sharing/docs/architecture/build-and-run.md)
- [docs/domain/standard-mall-target.md](/E:/flower-sharing/docs/domain/standard-mall-target.md)

## 运行环境

- JDK 17
- Maven 3.9+
- MySQL 8
- Redis 6+

说明：

- 仓库当前包含多环境配置和 Docker 编排，但仍存在遗留硬编码与说明缺失。
- 未经梳理前，不建议直接把仓库中的示例配置用于生产。
- 当前会话环境已确认 `java` 可用，但 `mvn` 不在 PATH 中。

环境要求详见：

- [docs/architecture/environment-requirements.md](/E:/flower-sharing/docs/architecture/environment-requirements.md)

## 构建命令

在仓库根目录执行：

```bash
mvn clean package -DskipTests
```

如只做编译校验：

```bash
mvn -q -DskipTests compile
```

更多构建与运行入口见：

- [docs/architecture/build-and-run.md](/E:/flower-sharing/docs/architecture/build-and-run.md)
- [docs/testing/verification-commands.md](/E:/flower-sharing/docs/testing/verification-commands.md)

## 标准启动方式

当前可识别的启动入口有 3 个：

1. 主应用：`ruoyi-admin`
2. 监控中心：`ruoyi-monitor-admin`
3. 任务服务：`ruoyi-snailjob-server`

建议阅读：

- [docs/architecture/service-startup.md](/E:/flower-sharing/docs/architecture/service-startup.md)
- [docs/architecture/build-and-run.md](/E:/flower-sharing/docs/architecture/build-and-run.md)
- [docs/testing/startup-checklist.md](/E:/flower-sharing/docs/testing/startup-checklist.md)

## 配置文件说明

主应用配置位于：

- `ruoyi-admin/src/main/resources/application.yml`
- `ruoyi-admin/src/main/resources/application-dev.yml`
- `ruoyi-admin/src/main/resources/application-prod.yml`

扩展服务配置位于：

- `ruoyi-extend/ruoyi-monitor-admin/src/main/resources/application.yml`
- `ruoyi-extend/ruoyi-snailjob-server/src/main/resources/application.yml`
- `ruoyi-extend/ruoyi-snailjob-server/src/main/resources/application-dev.yml`
- `ruoyi-extend/ruoyi-snailjob-server/src/main/resources/application-prod.yml`

配置现状与差异见：

- [docs/config/config-matrix.md](/E:/flower-sharing/docs/config/config-matrix.md)
- [docs/config/config-governance.md](/E:/flower-sharing/docs/config/config-governance.md)

## Docker 使用方式

当前仓库存在多份 Docker Compose 文件，但用途尚未统一：

- `script/docker/docker-compose.yml`
- `script/docker/docker-compose1.yml`
- `script/docker/docker-compose2.yml`

请先阅读：

- [docs/docker/docker-manifest.md](/E:/flower-sharing/docs/docker/docker-manifest.md)
- [script/docker/README.md](/E:/flower-sharing/script/docker/README.md)

## 脚本说明

当前 `script/bin` 只提供 `ruoyi-admin.jar` 的基础启动/停止脚本：

- `script/bin/ry.bat`
- `script/bin/ry.sh`

脚本现状说明见：

- [docs/scripts/script-manifest.md](/E:/flower-sharing/docs/scripts/script-manifest.md)
- [script/README.md](/E:/flower-sharing/script/README.md)

## 检测与测试基线

当前治理已将“检测与测试”纳入标准化清洗范围。

请先阅读：

- [docs/testing/test-baseline.md](/E:/flower-sharing/docs/testing/test-baseline.md)
- [docs/testing/startup-checklist.md](/E:/flower-sharing/docs/testing/startup-checklist.md)
- [docs/testing/regression-checklist.md](/E:/flower-sharing/docs/testing/regression-checklist.md)
- [docs/testing/verification-commands.md](/E:/flower-sharing/docs/testing/verification-commands.md)
- [docs/testing/environment-check.md](/E:/flower-sharing/docs/testing/environment-check.md)
- [docs/testing/build-verification-log.md](/E:/flower-sharing/docs/testing/build-verification-log.md)
- [docs/testing/smoke-scenarios.md](/E:/flower-sharing/docs/testing/smoke-scenarios.md)
- [docs/testing/api-smoke-baseline.md](/E:/flower-sharing/docs/testing/api-smoke-baseline.md)

## 当前已识别的问题

- `ruoyi-flower` 同时混合后台、小程序、商品、订单、配送、会员、积分、营销、课程、社区等职责
- 命名存在 `Folwer` 拼写错误和多套接口前缀并存
- README 和仓库文档曾严重不足，现已开始补齐
- 配置中存在敏感信息和环境硬编码
- Docker 和脚本缺少统一标准说明
- 自动化测试薄弱，需先依靠基线清单保护重构过程
- 构建验证依赖本机 Maven 环境，仓库尚未提供 Maven Wrapper

详见：

- [docs/domain/legacy-flower-boundary.md](/E:/flower-sharing/docs/domain/legacy-flower-boundary.md)
- [docs/cleanup/redundant-candidates.md](/E:/flower-sharing/docs/cleanup/redundant-candidates.md)
- [docs/cleanup/api-exposure-governance.md](/E:/flower-sharing/docs/cleanup/api-exposure-governance.md)
- [docs/architecture/module-status.md](/E:/flower-sharing/docs/architecture/module-status.md)
- [docs/architecture/module-retention.md](/E:/flower-sharing/docs/architecture/module-retention.md)
- [docs/architecture/api-surface.md](/E:/flower-sharing/docs/architecture/api-surface.md)
- [docs/architecture/environment-requirements.md](/E:/flower-sharing/docs/architecture/environment-requirements.md)

## AI Harness 化说明

本仓库正在尝试采用“工程结构服务于 AI 更快理解和调用系统”的治理方向。

当前策略不是引入复杂智能体编排，而是先做四件事：

1. 将项目知识写入仓库内文档
2. 将模块、配置、脚本、部署关系索引化
3. 将检测与回归标准沉淀为可重复执行的基线
4. 将后续治理规则逐步沉淀为可检查、可复用的工程约束

入口文档见：

- [docs/index.md](/E:/flower-sharing/docs/index.md)

## 维护建议

- 不要继续在 `ruoyi-flower` 既有大杂烩结构中直接叠加新业务
- 新增领域命名不再使用 `flower` / `folwer` 作为核心业务命名
- 先做领域边界清理，再谈模块拆分
- 所有配置和部署知识优先沉淀到仓库内文档
- 所有改动都应绑定对应的启动检查或回归清单

## 常见问题

### 1. 这个项目现在是鲜花商城吗

不是。当前应视为“带鲜花遗留的商城后端基座”，目标是清洗为单商户标准商城系统。

### 2. 是否已经适合直接继续加新功能

不建议。应先完成基础文档、配置分层、脚本说明、检测基线和领域边界治理。

### 3. 是否已经存在标准启动文档

已补仓库内导航，但启动与部署仍需在后续批次继续标准化。

### 4. 是否已经有足够自动化测试

没有。当前应先依靠启动清单和回归清单建立基线，再逐步将关键链路脚本化和自动化。
