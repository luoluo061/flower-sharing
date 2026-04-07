# 接口暴露收敛建议

本文档用于约束后续如何收敛当前主应用中过宽的接口暴露面。

目标：

- 缩小“主应用默认暴露什么”的认知噪音
- 为后续 SpringDoc 分组收敛提供低风险执行顺序
- 避免在未完成验证前直接误删 `demo`、`generator` 或其配置

## 1. 当前已确认事实

当前主应用接口暴露面包含以下几类：

- 商城主线业务接口：主要来自 `ruoyi-flower`
- 后台底座接口：主要来自 `ruoyi-system`
- 平台辅助接口：`ruoyi-generator`
- 示例接口：`ruoyi-demo`

`ruoyi-admin/src/main/resources/application.yml` 当前仍为下列包配置了 SpringDoc 分组：

- `org.dromara.demo`
- `org.dromara.generator`
- `org.dromara.system`
- `org.dromara.flowable`
- `org.dromara.flower`
- `org.dromara.flowerapplet`

结论：

- `demo` 与 `generator` 当前不仅参与构建，也参与接口文档暴露
- 当前主应用的接口文档认知仍然偏“平台全家桶”，而不是“单商户商城主线”

## 2. 本轮不直接改动的原因

当前不建议直接删除 `demo` / `generator` 的原因：

- 还没有跑通完整编译基线
- 还没有跑通主应用启动验证
- 还没有确认前端、权限、菜单、路由、按钮权限是否依赖这些模块
- 还没有确认生产环境是否实际使用代码生成器或示例能力

因此，这一阶段的正确动作是“先标记、先隔离认知、先建立验证顺序”，而不是直接删模块或删文档分组。

## 3. 低风险治理顺序

建议按以下顺序收敛：

### 3.1 先完成构建与启动基线

前置条件：

- Maven CLI 或 Maven Wrapper 可用
- 能完成 `mvn -q -DskipTests compile`
- 能完成主应用启动和 `/swagger-ui.html` 访问

在这一步完成之前，不建议直接动 SpringDoc 分组。

### 3.2 先在文档与测试层隔离主线范围

这一步已经开始落地：

- `demo` 与 `generator` 不再被视为商城主线
- 商城回归和冒烟默认不覆盖 `demo` / `generator`
- 相关基线见：
  - `docs/architecture/api-surface.md`
  - `docs/testing/regression-checklist.md`
  - `docs/testing/smoke-scenarios.md`

### 3.3 再评估是否收敛 SpringDoc 分组

建议优先动作：

- 保留 `system`
- 保留 `flower`
- 保留 `flowerapplet`
- 对 `demo`、`generator`、`flowable` 逐项确认是否仍需默认暴露

建议判断标准：

- 是否属于单商户商城主线
- 是否被当前生产流程使用
- 是否只是运维/开发辅助能力
- 是否可通过环境区分隐藏而不是直接删除

### 3.4 最后再评估模块级收缩

如果后续验证表明 `demo` 或 `generator` 不再需要参与生产主应用，再进入：

- 移除 `ruoyi-admin` 直接依赖
- 收敛 SpringDoc 配置
- 收敛菜单、路由、权限、前端入口
- 更新回归清单

这一步属于中风险治理，不应提前执行。

## 4. 后续执行条件

### 可以开始收敛 SpringDoc 的条件

- 环境检查通过到可编译级别
- 编译通过
- 主应用启动通过
- Swagger 可访问
- 已确认主线接口回归基线

### 可以开始评估移除 demo/generator 依赖的条件

- 已确认前后端无直接运行依赖
- 已确认生产环境不依赖对应功能
- 已有一次完整回归记录
- 已准备好回退方案

## 5. 当前建议

当前最合理的动作是：

1. 继续先补可执行验证入口
2. 先打通 Maven 构建前提
3. 再做 SpringDoc 收敛
4. 最后才考虑 `demo` / `generator` 的模块级收缩

也就是说，`demo` / `generator` 当前的正确状态不是“立即删除”，而是“明确不属于商城主线，但暂时保留，等待验证后再收缩”。
