# 文档索引

本目录用于沉淀项目知识，服务于以下目标：

- 新接手成员可以快速理解仓库
- 后续标准化清洗有清晰边界
- AI 可以更快定位模块、服务、配置、测试和部署关系

## 建议阅读顺序

1. [architecture/module-map.md](/E:/flower-sharing/docs/architecture/module-map.md)
2. [architecture/module-status.md](/E:/flower-sharing/docs/architecture/module-status.md)
3. [architecture/module-retention.md](/E:/flower-sharing/docs/architecture/module-retention.md)
4. [architecture/api-surface.md](/E:/flower-sharing/docs/architecture/api-surface.md)
5. [architecture/environment-requirements.md](/E:/flower-sharing/docs/architecture/environment-requirements.md)
6. [architecture/maven-entry-strategy.md](/E:/flower-sharing/docs/architecture/maven-entry-strategy.md)
7. [architecture/service-startup.md](/E:/flower-sharing/docs/architecture/service-startup.md)
8. [architecture/build-and-run.md](/E:/flower-sharing/docs/architecture/build-and-run.md)
9. [config/config-matrix.md](/E:/flower-sharing/docs/config/config-matrix.md)
10. [config/config-governance.md](/E:/flower-sharing/docs/config/config-governance.md)
11. [domain/standard-mall-target.md](/E:/flower-sharing/docs/domain/standard-mall-target.md)
12. [domain/legacy-flower-boundary.md](/E:/flower-sharing/docs/domain/legacy-flower-boundary.md)
13. [scripts/script-manifest.md](/E:/flower-sharing/docs/scripts/script-manifest.md)
14. [docker/docker-manifest.md](/E:/flower-sharing/docs/docker/docker-manifest.md)
15. [cleanup/redundant-candidates.md](/E:/flower-sharing/docs/cleanup/redundant-candidates.md)
16. [cleanup/api-exposure-governance.md](/E:/flower-sharing/docs/cleanup/api-exposure-governance.md)
17. [testing/test-baseline.md](/E:/flower-sharing/docs/testing/test-baseline.md)
18. [testing/startup-checklist.md](/E:/flower-sharing/docs/testing/startup-checklist.md)
19. [testing/regression-checklist.md](/E:/flower-sharing/docs/testing/regression-checklist.md)
20. [testing/verification-commands.md](/E:/flower-sharing/docs/testing/verification-commands.md)
21. [testing/environment-check.md](/E:/flower-sharing/docs/testing/environment-check.md)
22. [testing/build-verification-log.md](/E:/flower-sharing/docs/testing/build-verification-log.md)
23. [testing/smoke-scenarios.md](/E:/flower-sharing/docs/testing/smoke-scenarios.md)
24. [testing/api-smoke-baseline.md](/E:/flower-sharing/docs/testing/api-smoke-baseline.md)

## 文档分层

- `architecture/`
  - 仓库结构、模块职责、模块启用状态、接口暴露面、环境要求、Maven 入口策略、服务关系、构建与启动方式
- `config/`
  - 配置分层、环境差异、配置治理约束
- `domain/`
  - 标准商城目标域、鲜花遗留边界、待剥离范围
- `scripts/`
  - 启动脚本和脚本目录说明
- `docker/`
  - Docker 文件清单与用途
- `cleanup/`
  - 冗余候选、接口暴露收敛建议、遗留风险、后续治理事项
- `testing/`
  - 启动检查、回归清单、检测基线、验证命令和关键冒烟场景

## 使用原则

- 本目录优先记录“仓库事实”和“治理结论”
- 不写业务幻想，不写无依据猜测
- 待确认事项必须明确标注“待确认”
- 修改代码前，优先先更新或补齐相关文档
- 修改代码后，至少回归对应的测试基线或检查清单
