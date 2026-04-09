# Stage 3: Transaction and User-Asset Backbone

## Current status

Stage 3 is **completed**. The codebase now has a shared transaction and user-asset backbone on top of the stabilized product and order backbones from Stages 1 and 2.

Stage 3 preserved all public routes, BO/VO contracts, and database schema while finishing the transaction/user-asset rule split.

## Shared Stage 3 domain services

The shared Stage 3 rule layer is centered on:

- `PaymentTransactionDomainService`
- `CouponAssetDomainService`
- `PointsAssetDomainService`
- `MemberAssetDomainService`

These services are now the default canonical entrypoints for:

- paid/query/refund mutation preparation
- refund response shaping
- coupon publish/receive/consume/rollback rules
- points earn/consume/refund-facing semantics and source labeling
- member privilege snapshot and purchase-record preparation

## Stage 3 rewiring result

Stage 3 has rewired these mainline areas into the shared transaction and asset layer:

- applet order payment/refund state preparation in `FolwerAppletOrderServiceImpl`
- payment request shaping and refund response shaping in applet membership purchase flow
- points record source-label shaping in `FolwerCreditGetrecordsServiceImpl`
- points exchange preparation in `MemberPointsExchangeGoldServiceImpl`
- member privilege snapshot creation and purchase-record preparation in backend/applet member purchase services
- coupon publish and state-toggle rules in `MarketingCouponServiceImpl`
- coupon receive preparation in `MarketingCouponReceiveServiceImpl`

The resulting backbone is now explicit:

- payment is treated as a transaction capability
- points, membership, and coupons are treated as user assets
- order services remain adapter-facing and consume the shared transaction rules rather than owning them

## Stage 3 regression coverage

The Stage 3 completion gate is:

- `compile`
- full Stage 1 + Stage 2 regression
- Stage 3 transaction/user-asset controller, service, and domain tests
- `Tests run: 201, Failures: 0, Errors: 0`

Stage 3-specific tests now include:

- `PaymentTransactionDomainServiceTest`
- `PointsAssetDomainServiceTest`
- `MemberAssetDomainServiceTest`
- `CouponAssetDomainServiceTest`
- `MemberAssetControllerTest`
- `MemberAppletAssetControllerTest`
- `PointsAssetControllerTest`
- `CouponAssetControllerTest`
- `FolwerLegacyCouponControllerTest`
- `MemberPurchaseRecordServiceImplTest`
- `MemberAppletPurchaseRecordServiceImplTest`
- `MemberPointsExchangeGoldServiceImplTest`
- `MarketingCouponServiceImplTest`
- `MarketingCouponReceiveServiceImplTest`

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

Explicitly kept out of the Stage 3 mainline and treated as edge or legacy capability:

- course payment backbone
- credit-mall order/product/category/payment backbone
- promotion reward, rebate, profit-sharing, and commission logic
- naming cleanup and legacy isolation

These capabilities are preserved, but they are not inputs to the standard mall transaction/user-asset backbone.

## Stage 3 completion result

Stage 3 is complete because:

- payment is expressed as a stable shared transaction rule layer
- membership, points, and coupons are expressed as shared user-asset rules
- order services no longer own payment rules directly
- promotion/rebate/profit-sharing and credit-mall logic are not part of the mainline mall backbone
- the repository is stable enough to move into Stage 4: Legacy Isolation and Backbone Closure

The default next priority is now Stage 4.
