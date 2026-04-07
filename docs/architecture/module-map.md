# 模块与目录结构说明

## 1. 根目录概览

```text
flower-sharing
├─ ruoyi-admin
├─ ruoyi-common
├─ ruoyi-extend
├─ ruoyi-modules
├─ script
├─ pom.xml
├─ README.md
└─ AGENTS.md
```

## 2. 根模块职责

### `ruoyi-admin`

- 类型：Spring Boot 可执行主应用
- 作用：当前主业务启动入口
- 说明：直接依赖 `ruoyi-system`、`ruoyi-generator`、`ruoyi-demo`、`ruoyi-flower`

### `ruoyi-common`

- 类型：公共能力聚合模块
- 作用：提供底座公共库
- 典型子模块：`ruoyi-common-core`、`ruoyi-common-security`、`ruoyi-common-web`、`ruoyi-common-pay`

### `ruoyi-extend`

- 类型：扩展服务聚合模块
- 当前子模块：
  - `ruoyi-monitor-admin`
  - `ruoyi-snailjob-server`

### `ruoyi-modules`

- 类型：业务模块聚合
- 当前启用模块：
  - `ruoyi-demo`
  - `ruoyi-generator`
  - `ruoyi-system`
  - `ruoyi-flower`
- 当前未启用但目录存在：
  - `ruoyi-job`
  - `ruoyi-workflow`

说明：

- `ruoyi-job` 和 `ruoyi-workflow` 在目录层存在，但在 `ruoyi-modules/pom.xml` 中被注释掉，属于遗留候选模块。

## 3. 当前主业务模块 `ruoyi-flower`

目录位置：

- `ruoyi-modules/ruoyi-flower`

当前只按包分成两大块：

- `org.dromara.flower`
- `org.dromara.flowerapplet`

但这两块内部并不是清晰分层，而是混合了：

- 后台管理接口
- 小程序接口
- 商品/分类/SKU
- 订单/退款/配送
- 会员/积分
- 优惠券/营销
- 社区
- 课程

结论：

- `ruoyi-flower` 当前不是标准商城模块，而是“鲜花业务遗留 + 商城能力 + 扩展功能”混合模块。
- 后续治理不应继续扩大其边界，应逐步梳理为标准商城核心域与遗留扩展域。

## 4. 当前目录结构的主要问题

- 模块聚合视图与实体目录不完全一致
- `ruoyi-flower` 职责过载
- 包命名和接口命名前缀不统一
- 存在长期拼写错误 `Folwer`
- 缺少仓库内正式模块说明文档

## 5. 后续治理建议

- 保留根多模块结构，不做一次性大改
- 先通过文档定义“保留主干域 / 待剥离域 / 待确认域”
- 后续如做模块拆分，先从领域边界而不是目录重命名入手
