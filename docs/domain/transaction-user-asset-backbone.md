# Stage 3: Transaction and User-Asset Backbone

## Current status

Stage 3 has started. The codebase now has an initial shared transaction and user-asset rule layer, while product and order backbones from Stages 1 and 2 remain stable and fully covered by regression.

The current Stage 3 entry batch keeps all public routes, BO/VO contracts, and database schema unchanged.

## Shared Stage 3 domain services

The current Stage 3 shared rule layer is centered on:

- `PaymentTransactionDomainService`
- `CouponAssetDomainService`
- `PointsAssetDomainService`
- `MemberAssetDomainService`

These services currently provide the first canonical entrypoints for:

- paid/refund mutation preparation
- refund response shaping
- coupon publish/receive rules
- points earn and points-source labeling
- member privilege snapshot preparation

## Current Stage 3 rewiring

The first Stage 3 batch has already rewired these areas into the shared transaction and asset layer:

- applet order payment/refund state preparation in `FolwerAppletOrderServiceImpl`
- points record source-label shaping in `FolwerCreditGetrecordsServiceImpl`
- member privilege snapshot creation in `MemberPurchaseRecordServiceImpl`
- coupon publish rule preparation in `MarketingCouponServiceImpl`
- coupon receive snapshot preparation in `MarketingCouponReceiveServiceImpl`

The intent is now explicit:

- payment is treated as a transaction capability
- points, membership, and coupons are treated as user assets
- order services remain adapter-facing and consume the shared transaction rules rather than owning them

## Current Stage 3 regression coverage

The current Stage 3 opening gate is:

- `compile`
- full Stage 1 + Stage 2 regression
- Stage 3 transaction/user-asset domain tests
- `Tests run: 170, Failures: 0, Errors: 0`

Current Stage 3-specific tests include:

- `PaymentTransactionDomainServiceTest`
- `PointsAssetDomainServiceTest`
- `MemberAssetDomainServiceTest`
- `CouponAssetDomainServiceTest`

Existing high-risk protection remains active:

- full product-center regression
- full order/fulfillment regression
- `WxPayCallbackControllerTest`
- `FolwerAppletOrderServiceImplTest`

## Stage 3 boundary

Included in Stage 3 mainline:

- payment request, paid-state preparation, query-payment mutation, refund preparation, refund callback preparation
- member level, member privilege, member purchase-record asset shaping
- points earn/use/refund-facing base semantics
- coupon publish, receive, consume, rollback-facing asset semantics

Explicitly kept out of the Stage 3 mainline:

- course payment backbone
- credit-mall order/product/category/payment backbone
- promotion reward, rebate, profit-sharing, and commission logic
- naming cleanup and legacy isolation

These capabilities are preserved as edge or legacy domains and are not treated as the standard mall transaction/user-asset backbone.

## Stage 3 next priorities

Stage 3 is not complete yet. The next implementation priorities are:

- finish rewiring payment flow so payment rules no longer live inside order services
- expand member/points/coupon controller and service regression
- make coupon and points rules the default shared asset entrypoints
- explicitly document and edge-isolate non-mainline transaction capabilities

## Stage 3 exit result

Stage 3 is complete only when:

- payment is expressed as a stable shared transaction rule layer
- membership, points, and coupons are expressed as shared user-asset rules
- order services no longer own payment rules directly
- promotion/rebate/profit-sharing and credit-mall logic are not part of the mainline mall backbone
- the repository is stable enough to move into Stage 4: Legacy Isolation and Backbone Closure
