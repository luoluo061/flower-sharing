# Four-Stage Delivery Roadmap

## Purpose

This file is the long-term execution anchor for the repository cleanup.

The target is to reach a **single-merchant standard mall backbone** in **4 major conversation stages**, not through many small ad hoc plans. Future implementation should align to these 4 stages unless a blocking repository fact forces a correction.

Execution principle:

- Keep compatibility first
- Keep GitHub as the only active delivery branch manager
- Keep compile + regression as the minimum gate
- Prefer staged cleanup over big-bang rewrites

Current active branch:

- `feature/product-line-stabilization`

## Stage 1: Product Center Stabilization

### Goal

Turn the current product-related code from a flower-coupled dual-stack implementation into a stable product-center foundation.

### Must be completed in this stage

- Category, product, SKU, and product-detail boundaries are explicit
- Backend and mini-program stacks still coexist, but shared rules are no longer scattered
- Product read and write paths both have stable regression coverage
- Shared product helper layer is in place
- Shared product service candidates are identified and partially landed
- Product-center migration blueprint is concrete enough to guide the next domains

### Current progress

This stage is **in progress and near complete**.

Already completed:

- backend product read regression
- backend product first-wave write regression
- mini-program product read regression
- shared helper layer:
  - `ProductCategoryHierarchySupport`
  - `ProductWriteDefaultsSupport`
  - `ProductSkuAggregateSupport`
- shared product-domain service layer:
  - `ProductCategoryDomainService`
  - `ProductCoreDomainService`
  - `ProductSkuAggregateDomainService`
  - `ProductDetailDomainService`
- product-center migration blueprint
- shared-boundary documentation

Still required before Stage 1 is considered complete:

- confirm the new domain-service layer is the default source of truth for product-domain rules
- finish the final Stage 1 closeout and handoff into Stage 2 order work

### Exit criteria

Stage 1 is complete only when:

- product line is no longer the most structurally chaotic domain
- shared product logic no longer depends on duplicated backend/applet helpers
- product read/write regressions are considered stable enough to support order-flow refactor

## Stage 2: Order and Fulfillment Backbone

### Goal

Turn orders, delivery, logistics, and basic after-sales into a standard mall order backbone.

### Must be completed in this stage

- order lifecycle is stabilized:
  - pending payment
  - paid
  - shipped
  - completed
  - cancelled
  - after-sales in progress
- backend order management and mini-program order center become one coherent backbone
- logistics lookup and shipment flow are decoupled from flower-specific fulfillment semantics
- flower-only delivery logic, starting-price logic, and other special fulfillment behaviors are pushed out of the main order backbone
- order read and write paths have stable regression coverage

### Exit criteria

Stage 2 is complete only when:

- orders are expressed as a standard mall backbone
- fulfillment and logistics are cleanly separated from flower-only extensions
- order state transitions are testable and predictable

## Stage 3: Transaction and User-Asset Backbone

### Goal

Turn payment, membership, points, and coupons into standard mall transaction-support and user-asset domains.

### Must be completed in this stage

- payment chain is stabilized:
  - submit payment
  - query payment
  - payment callback
  - refund
  - refund callback
- membership, points, and coupons are treated as user assets, not flower-marketing side effects
- profit-sharing, promotion-reward, and other non-backbone transaction logic are detached from the main mall path
- payment + user-asset interactions around order creation are clearly bounded

### Exit criteria

Stage 3 is complete only when:

- transaction flow is stable under regression
- user assets are no longer mixed with flower rebate/profit-sharing logic
- the mall transaction loop is coherent without relying on historical special-case coupling

## Stage 4: Legacy Isolation and Backbone Closure

### Goal

Turn the repository from a usable flower-legacy mall base into a clearly defined **single-merchant standard mall backbone**.

### Must be completed in this stage

- non-backbone domains are isolated or downgraded:
  - courses
  - community
  - flower-only delivery/marketing extensions
- historical naming spread is stopped and later compatibility cleanup is finished
- module boundaries, interface boundaries, docs, and configuration explanations are aligned to the final backbone
- the final regression set covers the backbone domains:
  - product
  - order
  - payment
  - logistics
  - membership
  - points
  - basic coupon capability

### Exit criteria

Stage 4 is complete only when:

- repository understanding and code structure match the same mall backbone story
- non-mall backbone capabilities no longer pollute core domains
- the system can reasonably be described as a single-merchant standard mall backbone rather than a flower-legacy business system

## Delivery Rules

These rules stay active across all 4 stages:

- Use GitHub as the active delivery remote
- Keep work on explicit feature branches
- Every stable batch must:
  - compile
  - pass the active regression set
  - be committed
  - be pushed
- Do not change database schema before domain boundaries are stabilized
- Do not change public paths just to improve naming early
- Lock current semantics with tests before changing them

## Current Instruction to Future Work

The current repository should be treated as being in:

- **Stage 1: Product Center Stabilization**

Default next priority:

- finish Stage 1 before shifting the main effort to Stage 2

This roadmap is intended to let the project advance through **4 large conversation checkpoints**, while implementation inside each stage can continue in multiple small batches without redefining the overall direction.
