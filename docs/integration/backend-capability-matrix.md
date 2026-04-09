# Backend Capability Matrix

## Summary

This repository now implements a **single-merchant standard mall backbone** on the backend side.

The backend is ready to support integration for:

- admin management frontend
- applet / mini-program frontend
- MySQL-backed business data
- Redis-backed session, cache, and async state
- payment callback integration

It does **not** include the frontend codebases themselves, and it does **not** package external provider environments such as production payment, logistics, or public callback exposure.

## Backbone Capabilities Already Present

### Product Center

Available backend capabilities:

- category list / detail / write management
- product list / detail / write management
- SKU list / detail / write management
- product detail list / detail / write management
- applet-facing product browsing APIs
- shared product domain services and regression coverage

Typical interface families:

- `/flower/category/*`
- `/flower/product/*`
- `/flower/sku/*`
- `/flower/productDetail/*`
- `/flowerapplet/category/*`
- `/flowerapplet/product/*`
- `/flowerapplet/sku/*`
- `/flowerapplet/productDetail/*`

### Order And Fulfillment Backbone

Available backend capabilities:

- backend order list / detail / info
- applet order list / detail / query-order
- applet order create / update / submit order
- refund preparation and backend refund creation
- order detail aggregation
- logistics and fulfillment view
- order delivery record management

Typical interface families:

- `/flower/order/*`
- `/flower/orderDetail/*`
- `/flower/orderRefund/*`
- `/flower/orderDvy/*`
- `/flowerapplet/order/*`
- `/flowerapplet/orderDetail/*`
- `/flowerapplet/orderRefund/*`

### Transaction Backbone

Available backend capabilities:

- applet submit payment
- query payment result
- payment callback handling
- refund request preparation
- refund callback handling

Typical interface families:

- `/flowerapplet/order/submitOrder`
- `/flowerapplet/order/queryOrder/{orderId}`
- `/flowerapplet/order/refundOrder`
- `/wxpayback/pay/payCallback`
- `/wxpayback/pay/refundCallback`

### User Asset Backbone

Available backend capabilities:

- membership level / privilege / purchase record
- standard points configuration and points ledger
- coupon publish / receive / consume / rollback-facing asset rules

Typical interface families:

- membership:
  - backend `MemberLevel*`, `MemberLevelPrivilege*`, `MemberPurchaseRecord*`
  - applet `MemberAppletLevel*`, `MemberAppletLevelPrivilege*`, `MemberAppletPurchaseRecord*`
- points:
  - `/flower/creditSet/*`
  - `/flower/creditGetrecords/*`
  - applet credit get-record endpoints
- coupon:
  - `/flower/marketingCoupon/*`
  - `/flower/marketingCouponReceive/*`
  - `/flower/coupon/*`
  - `/flower/couponReceive/*`

## Integration-Ready But External

These are supported by the backend, but require external integration pieces that are not part of this repository:

- admin frontend application
- mini-program / applet frontend application
- public frontend domain routing
- real payment environment and merchant certificates
- public callback reachability
- optional logistics provider integration

## Explicitly Not Part Of The Default Backbone

These capabilities remain in the repository but are **not** part of the default mall integration scope:

- courses
- community
- credit-mall order / product / category flows
- promotion reward / rebate / profit-sharing
- flower-only delivery configuration

These domains should not be used as blockers when determining whether the mall backbone is integration-ready.
