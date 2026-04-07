# 接口暴露面基线

本文档用于记录当前主应用内已经暴露出来的主要接口面，服务于以下目标：

- 区分单商户商城主线接口与平台辅助/示例接口
- 为后续 Swagger 收敛、模块裁剪、环境暴露控制提供基线
- 帮助新接手成员和 AI 快速判断哪些接口属于当前治理重点

说明：

- 本文档记录的是当前仓库代码中的可见事实，不代表这些接口都应该继续长期保留
- 未实际启动验证的内容，以源码扫描结果为准
- 对于用途不明确的接口，统一标记为“待确认”，不在本轮直接删除或下线

## 1. 当前可确认的主要暴露面

### 1.1 商城主业务接口

当前主业务接口主要集中在 `ruoyi-modules/ruoyi-flower`，但前缀并不统一。

后台管理侧常见前缀：

- `/flower/*`
- `/flowerPc/*`

小程序侧常见前缀：

- `/flowerapplet/*`
- `/flowerApplet/*`
- `/applet/flower/*`

支付相关前缀：

- `/wxpayback/*`

可确认的典型控制器示例：

- 商品与分类：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerProductController.java`
- 商品与分类：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerCategoryController.java`
- 商品与分类：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerSkuController.java`
- 商品与分类：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletProductController.java`
- 商品与分类：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletCategoryController.java`
- 商品与分类：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletSkuController.java`
- 订单与履约：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerOrderController.java`
- 订单与履约：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerOrderRefundController.java`
- 订单与履约：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerOrderDvyController.java`
- 订单与履约：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletOrderController.java`
- 订单与履约：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletOrderRefundController.java`
- 订单与履约：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletOrderDvyController.java`
- 会员、积分、营销：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/MemberLevelController.java`
- 会员、积分、营销：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/MarketingCouponController.java`
- 会员、积分、营销：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerCreditProductController.java`
- 会员、积分、营销：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/MemberAppletLevelController.java`
- 会员、积分、营销：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletCreditProductController.java`

### 1.2 平台底座接口

当前主应用还会随 `ruoyi-system` 暴露系统管理能力。这部分属于后台底座核心能力，默认保留，不纳入“非主线噪音”范围。

说明：

- 本轮没有逐个枚举 `ruoyi-system` 全量接口
- 后续如需要做更细的接口面梳理，可补充后台系统能力清单

### 1.3 示例与辅助接口

当前主应用仍直接依赖并暴露以下非商城主线接口：

- `ruoyi-demo`
- `ruoyi-generator`

这两类接口当前仍参与主应用运行认知，但不应再被视为单商户标准商城主干的一部分。

`ruoyi-demo` 典型前缀：

- `/demo/*`
- `/swagger/demo`

控制器示例：

- `ruoyi-modules/ruoyi-demo/src/main/java/org/dromara/demo/controller/TestDemoController.java`
- `ruoyi-modules/ruoyi-demo/src/main/java/org/dromara/demo/controller/TestExcelController.java`
- `ruoyi-modules/ruoyi-demo/src/main/java/org/dromara/demo/controller/MailController.java`
- `ruoyi-modules/ruoyi-demo/src/main/java/org/dromara/demo/controller/SmsController.java`
- `ruoyi-modules/ruoyi-demo/src/main/java/org/dromara/demo/controller/Swagger3DemoController.java`

`ruoyi-generator` 典型前缀：

- `/tool/gen`

控制器示例：

- `ruoyi-modules/ruoyi-generator/src/main/java/org/dromara/generator/controller/GenController.java`

## 2. SpringDoc 当前暴露现状

`ruoyi-admin/src/main/resources/application.yml` 当前仍为以下包配置了文档分组：

- `org.dromara.demo`
- `org.dromara.generator`
- `org.dromara.system`
- `org.dromara.flowable`
- `org.dromara.flower`
- `org.dromara.flowerapplet`

这意味着当前接口文档认知仍然是“底座 + 示例 + 生成器 + flower 混合业务”的组合，而不是“单商户标准商城主线”。

结论：

- `demo` 与 `generator` 当前不只是目录存在，而是仍在接口文档暴露面里
- 后续若要收敛生产基线，Swagger 分组会是一个明确治理点
- 但在未确认运行依赖前，本轮不直接改动 `application.yml`

## 3. 当前暴露面存在的问题

### 3.1 商城主线接口命名不统一

当前 `ruoyi-flower` 内部同时存在：

- `/flower/*`
- `/flowerPc/*`
- `/flowerapplet/*`
- `/flowerApplet/*`
- `/applet/flower/*`

这会带来几个直接问题：

- AI 和人工都难以快速判断接口归属
- 搜索与批量治理成本高
- 后续做统一网关、文档收敛、测试脚本编排时容易遗漏

### 3.2 非主线接口仍和主线一起暴露

`ruoyi-demo` 与 `ruoyi-generator` 当前仍通过主应用参与整体暴露面，会带来：

- 生产运行认知噪音
- Swagger 文档干扰
- 后续测试与回归范围模糊

### 3.3 flower 遗留域与标准商城主线混杂

即便只看 `ruoyi-flower`，当前暴露面中仍同时混有：

- 商品、订单、物流、会员、积分等商城主干
- 课程相关接口
- 社区相关接口
- 鲜花配送与遗留扩展能力

这和本次“单商户标准商城系统”的目标并不一致。

## 4. 本轮治理结论

当前可将接口暴露面暂时分为三类：

### 4.1 当前主线治理范围

- `ruoyi-system` 的后台底座能力
- `ruoyi-flower` 中与用户、商品、订单、支付、物流、会员、积分直接相关的接口

### 4.2 当前保留但非商城主线

- `ruoyi-generator`
- `ruoyi-demo`

### 4.3 当前存在但应视为遗留/待剥离域

- `ruoyi-flower` 中课程相关接口
- `ruoyi-flower` 中社区相关接口
- 鲜花专属配送、专属营销、专属语义路径

## 5. 后续建议

低风险阶段可继续做：

- 在文档中逐步补充主线接口域清单
- 将回归清单与接口前缀对应起来
- 将 `demo` / `generator` 标记为“默认不纳入商城回归主线”

中风险阶段再评估：

- 是否收敛 SpringDoc 分组
- 是否在生产基线中默认关闭或隔离 `demo` 接口
- 是否将 `generator` 从主应用常规暴露面中收缩
- 是否统一 `flower` 相关接口前缀与命名
