# 模块保留策略

本文件用于描述当前非商城核心模块在本轮清洗中的保留策略。

目标：

- 避免误删当前仍参与构建的模块
- 避免把底座能力误当成商城主干
- 为后续模块收缩或裁剪提供依据

## 1. 判断原则

后续讨论模块去留时，按以下维度判断：

- 是否参与当前 Maven 聚合构建
- 是否被 `ruoyi-admin` 直接依赖
- 是否暴露对外接口
- 是否属于商城主干能力
- 是否只是底座能力或示例能力

## 2. `ruoyi-system`

定位：

- 底座核心模块
- 后台系统管理、权限、组织等通用能力

策略：

- 保留
- 视为单商户商城基线的必备底座

## 3. `ruoyi-flower`

定位：

- 当前主业务模块
- 混合了承担商城主干和鲜花遗留扩展的历史模块

策略：

- 保留
- 作为后续领域清洗和拆边界的主战场
- 不建议继续直接往里扩新业务

## 4. `ruoyi-generator`

定位：

- 平台代码生成能力
- 当前被 `ruoyi-admin` 直接依赖
- 当前仍在 Swagger 分组中暴露

事实依据：

- `ruoyi-admin/pom.xml` 直接依赖 `ruoyi-generator`
- `application.yml` 中仍配置 `org.dromara.generator` 的 SpringDoc 分组
- `ruoyi-generator` 自带 `GenController` 与生成逻辑

策略：

- 当前保留
- 但归类为“平台辅助能力”，不是商城主干
- 后续可评估是否从生产基线中收缩，而不是现在直接移除

## 5. `ruoyi-demo`

定位：

- 示例与演示模块
- 含大量 `/demo/*` 控制器和测试示例能力
- 当前被 `ruoyi-admin` 直接依赖
- 当前仍在 Swagger 分组中暴露

事实依据：

- `ruoyi-admin/pom.xml` 直接依赖 `ruoyi-demo`
- `application.yml` 中仍配置 `org.dromara.demo` 的 SpringDoc 分组
- 目录下存在大量 `Test*Controller`、`MailController`、`SmsController`、`Redis*Controller`

策略：

- 当前保留，但明确标记为“非商城主干”
- 不应再把该模块中的能力视为商城标准能力的一部分
- 后续应优先评估是否从生产基线中剔除，而不是继续扩展

## 6. `ruoyi-job`

定位：

- 目录存在
- 当前未纳入 `ruoyi-modules` 聚合

策略：

- 先标记为遗留目录模块
- 不直接删除
- 后续根据实际部署与引用情况再处理

## 7. `ruoyi-workflow`

定位：

- 目录存在
- 当前未纳入 `ruoyi-modules` 聚合
- 与 `script/bpmn` 目录存在关联线索

策略：

- 先标记为遗留目录模块
- 不纳入当前单商户商城主干认知
- 后续按真实使用情况确认去留

## 8. 当前结论

本轮清洗中的模块分组建议如下：

### 商城主线保留

- `ruoyi-admin`
- `ruoyi-common`
- `ruoyi-system`
- `ruoyi-flower`
- `ruoyi-extend` 下独立服务

### 平台辅助能力保留但不属于商城主干

- `ruoyi-generator`

### 示例能力保留但不属于商城主干

- `ruoyi-demo`

### 遗留目录待确认

- `ruoyi-job`
- `ruoyi-workflow`

## 9. 后续建议

低风险：

- 持续在文档中明确模块定位
- 任何涉及 `ruoyi-demo` / `ruoyi-generator` 的改动，都先确认是否真的属于商城目标

中风险：

- 待验证主应用运行与依赖后，评估能否收缩 `ruoyi-demo`
- 待确认生产使用后，评估 `ruoyi-generator` 是否保留在生产基线
