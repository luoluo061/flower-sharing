# Stage 2: Order and Fulfillment Backbone

## Current status

Stage 2 is in progress. The first batch focuses on read-chain stabilization and shared order-domain entrypoints, without changing public controller routes, BO/VO contracts, or database schema.

## First batch implemented

- Added shared internal order domain services under `org.dromara.flower.service.domain`:
  - `OrderLifecycleDomainService`
  - `OrderDetailDomainService`
  - `OrderFulfillmentDomainService`
  - `OrderRefundDomainService`
- Rewired applet order read-chain aggregation in `FolwerAppletOrderServiceImpl`:
  - order-detail loading
  - `totalNum` aggregation
  - payment-success mutation preparation
- Rewired backend order management entrypoints in `FolwerOrderServiceImpl`:
  - address formatting through fulfillment domain service
  - backend refund creation through lifecycle/refund domain services
- Added Stage 2 first-batch tests:
  - `FolwerOrderReadControllerTest`
  - `FolwerAppletOrderReadControllerTest`
  - `OrderLifecycleDomainServiceTest`
  - `OrderDetailDomainServiceTest`
  - `OrderFulfillmentDomainServiceTest`
  - `OrderRefundDomainServiceTest`

## Stage 2 boundary

Included in Stage 2:

- backend order list/detail/info
- applet order list/detail/query-order
- order lifecycle state preparation
- order-detail aggregation
- backend refund creation preparation
- base fulfillment address/logistics-view shaping

Explicitly not in the first batch:

- checkout creation refactor
- full submit-order cleanup
- logistics module consolidation
- order-delivery record rewrite
- after-sale system expansion
- user asset and coupon settlement rules

## Next focus

Stage 2 next batches should continue in this order:

1. extract more order-detail and lifecycle rules out of backend/applet services
2. stabilize backend and applet order write-chain tests
3. isolate basic fulfillment/logistics capabilities from flower-specific delivery expressions
4. close Stage 2 only after backend and applet order rules no longer exist as separate implementations
