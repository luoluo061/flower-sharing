# Stage 2: Order and Fulfillment Backbone

## Current status

Stage 2 is in progress. The second batch has now extended the backbone from read-chain stabilization into write-chain and refund/detail service rewiring, while still keeping public controller routes, BO/VO contracts, and database schema unchanged.

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

## Second batch implemented

- Added backend order write/controller coverage:
  - `FolwerOrderWriteControllerTest`
  - `FolwerOrderDetailControllerTest`
  - `FolwerOrderRefundControllerTest`
- Added applet order write/delivery-record controller coverage:
  - `FolwerAppletOrderWriteControllerTest`
  - `FolwerAppletOrderDvyControllerTest`
- Rewired backend order-detail aggregation through `OrderDetailDomainService`:
  - backend SKU attachment now routes through shared detail-domain logic
- Rewired backend refund flow through `OrderRefundDomainService`:
  - refund status mutation is prepared centrally
  - backend refund service no longer duplicates mutation shaping
- Cleaned backend refund creation in `FolwerOrderServiceImpl`:
  - refund BO creation now relies on lifecycle/refund domain services without duplicated manual field assignment
- Added backend service regression:
  - `FolwerOrderDetailServiceTest`
  - `FolwerOrderRefundServiceTest`
- Current Stage 2 combined regression gate:
  - `compile`
  - full product + payment + order regression
  - `Tests run: 147, Failures: 0, Errors: 0`

## Stage 2 boundary

Included in Stage 2:

- backend order list/detail/info
- applet order list/detail/query-order
- order lifecycle state preparation
- order-detail aggregation
- backend refund creation preparation
- base fulfillment address/logistics-view shaping

Explicitly not yet complete in Stage 2:

- full checkout creation refactor
- full applet submit-order cleanup
- logistics module consolidation
- full delivery-record rewrite
- after-sale system expansion
- user asset and coupon settlement rules

## Next focus

Stage 2 next batches should continue in this order:

1. finish applet order write-chain cleanup around create/update/submit/refund/query flows
2. move backend order management, refund, and detail services fully into adapter-facing entrypoints
3. isolate basic fulfillment/logistics capabilities from flower-specific delivery expressions
4. close Stage 2 only after backend and applet order rules no longer exist as separate implementations
