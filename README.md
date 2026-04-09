# flower-sharing

## Overview

This repository is now maintained as a **single-merchant standard mall backbone**.

The active backbone covers:

- product center
- order and fulfillment
- transaction
- user assets:
  - membership
  - points
  - coupons

The codebase still contains several historical flower-business domains, but they are no longer part of the default mall backbone and are treated as legacy edge capabilities.

## Backbone Scope

Default backbone domains:

- product:
  - category
  - product
  - SKU
  - product detail
- order:
  - backend order management
  - applet order center
  - refund preparation
  - logistics and fulfillment view
- transaction:
  - submit payment
  - query payment
  - payment callback
  - refund
  - refund callback
- user assets:
  - member level and privileges
  - points ledger and exchange-facing semantics
  - coupon publish, receive, consume, rollback

Legacy edge domains kept in the repository but excluded from the default backbone:

- courses
- community
- credit-mall order/product/category flows
- promotion reward / rebate / profit-sharing
- flower-only delivery configuration

See:

- [Stage Roadmap](docs/domain/four-stage-delivery-roadmap.md)
- [Stage 4 Closure](docs/domain/legacy-isolation-backbone-closure.md)
- [Backbone Regression Gate](docs/testing/backbone-regression-gate.md)

## Runtime

Primary application:

- `ruoyi-admin`

Primary runtime dependencies:

- MySQL
- Redis

The default delivery story is the mall backbone only. Legacy edge domains remain available for compatibility, but they are not part of the default acceptance gate for this repository.

## Delivery Rules

Current delivery model:

- GitHub is the active remote for backbone development
- feature branches are used for each stage
- every stable batch must:
  - compile
  - pass the active backbone regression gate
  - be committed
  - be pushed

## Notes

- Historical names such as `Folwer` and `flowerapplet` are still present for compatibility.
- Stage 4 freezes further expansion of those historical names but does not perform a big-bang rename.
- Operational secrets, server passwords, and old GitLab bootstrap snippets are intentionally not kept in this repository documentation anymore.
