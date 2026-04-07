# 第一批 API 冒烟基线

本文档定义当前仓库第一批已经落地、可重复执行的 API 冒烟基线。

目标：
- 将“主应用可启动”升级为“主应用 + 公开只读接口可回归”
- 为后续 `ruoyi-flower` 清洗提供最小可复用护栏
- 在不触发真实业务副作用的前提下，把验证从“列表”扩展到“列表 + 详情”

范围说明：
- 当前仅覆盖 `ruoyi-admin` 主应用
- 当前仅覆盖公开、只读、无副作用接口
- 当前不覆盖登录写操作、注册、下单、退款、支付成功回调

## 1. 当前已纳入脚本化冒烟的接口

### 1.1 静态探测接口

这些接口不依赖前置数据解析，可直接探测：

1. `GET /v3/api-docs`
2. `GET /auth/isLogin`
3. `GET /flowerapplet/category/allList`
4. `GET /flowerapplet/category/list?pageNum=1&pageSize=1`
5. `GET /flowerapplet/product/list?pageNum=1&pageSize=1`
6. `GET /flowerapplet/announcement/list?pageNum=1&pageSize=1`
7. `GET /flowerapplet/sku/list?pageNum=1&pageSize=1`
8. `GET /flowerapplet/productDetail/list?pageNum=1&pageSize=1`
9. `GET /flowerapplet/product/queryColor`
10. `GET /flowerapplet/product/queryLevel`
11. `GET /flowerapplet/sku/queryColor`
12. `GET /flowerapplet/sku/queryLevel`

### 1.2 基于真实数据解析的详情接口

这些接口由脚本先访问列表接口，再从响应里解析主键后自动探测：

1. `GET /flowerapplet/category/{id}`
2. `GET /flowerapplet/product/queryCategory/{categoryId}/1/1`
3. `GET /flowerapplet/product/{id}`
4. `GET /flowerapplet/announcement/{announcementId}`
5. `GET /flowerapplet/sku/{skuId}`
6. `GET /flowerapplet/productDetail/getInfoBySkuId/{skuId}`
7. `GET /flowerapplet/productDetail/{detailId}`

说明：
- 如果源列表返回为空，脚本会将对应详情检查标记为 `SKIP`
- `SKIP` 表示当前环境数据不足，不代表接口一定异常
- `FAIL` 才表示请求失败或服务不可用

## 2. 当前默认最小回归集

每次涉及以下改动时，建议至少执行这一批冒烟：

- 启动链、依赖、配置、Spring 容器
- `ruoyi-admin` 启动入口
- `ruoyi-flower` 中公开只读接口
- 权限放行或匿名访问控制
- 商品、分类、SKU、公告、商品详情的只读查询逻辑

执行命令：

```powershell
powershell -ExecutionPolicy Bypass -File .\script\bin\smoke-api.ps1
```

## 3. 暂不纳入自动冒烟的接口

以下接口目前明确不纳入自动冒烟：

1. `POST /auth/login`
   - 原因：方法上存在 `@ApiEncrypt`
   - 当前尚未把调用规则整理成可复用脚本

2. `POST /auth/register`
   - 原因：存在 `@ApiEncrypt`
   - 会触发真实注册副作用

3. `POST /flowerapplet/order`
4. `POST /flowerapplet/order/submitOrder`
5. `POST /flowerapplet/order/refundOrder`
   - 原因：会触发真实订单、支付或退款副作用

6. `POST /wxpayback/pay/payCallback`
7. `POST /wxpayback/pay/refundCallback`
   - 原因：虽然路径公开，但依赖真实回调报文格式

## 4. 后续扩展顺序

下一步按以下顺序扩展：

1. 先打通后台登录链路的调用规则
2. 再补受保护的只读接口，例如后台订单列表、订单详情
3. 然后引入测试账号，补购物车与订单查询
4. 最后再评估下单、退款、支付回调模拟

## 5. 相关文档

- [verification-commands.md](/E:/flower-sharing/docs/testing/verification-commands.md)
- [build-verification-log.md](/E:/flower-sharing/docs/testing/build-verification-log.md)
- [smoke-scenarios.md](/E:/flower-sharing/docs/testing/smoke-scenarios.md)
