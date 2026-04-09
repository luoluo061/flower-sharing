# Backbone Regression Gate

## Purpose

This file defines the default delivery gate for the repository after Stage 4 closure.

The backbone gate is the quality bar for the **single-merchant standard mall backbone**. Legacy edge domains may keep their own tests, but they are not part of the default acceptance gate.

## Compile Gate

The repository must pass:

- `compile`

Use the existing single-process Maven execution model to avoid generated-source conflicts.

## Backbone Test Gate

The default backbone gate includes the stabilized tests from Stages 1 through 3.

### Product backbone

- `FolwerCatalogReadControllerTest`
- `FolwerCatalogWriteControllerTest`
- `FolwerProductWriteControllerTest`
- `FolwerProductDetailWriteControllerTest`
- `FolwerSkuWriteControllerTest`
- `FolwerCatalogReadServiceTest`
- `FolwerCatalogWriteServiceTest`
- `FolwerProductWriteServiceTest`
- `FolwerProductDetailWriteServiceTest`
- `FolwerSkuWriteServiceTest`
- `ProductCategoryHierarchySupportTest`
- `ProductWriteDefaultsSupportTest`
- `ProductSkuAggregateSupportTest`
- `ProductCategoryDomainServiceTest`
- `ProductCoreDomainServiceTest`
- `ProductDetailDomainServiceTest`
- `ProductSkuAggregateDomainServiceTest`
- `FolwerAppletCatalogReadControllerTest`
- `FolwerAppletCatalogReadServiceTest`
- `FolwerAppletProductDetailServiceTest`

### Order and fulfillment backbone

- `FolwerOrderReadControllerTest`
- `FolwerOrderWriteControllerTest`
- `FolwerOrderDetailControllerTest`
- `FolwerOrderRefundControllerTest`
- `FolwerOrderDetailServiceTest`
- `FolwerOrderRefundServiceTest`
- `OrderLifecycleDomainServiceTest`
- `OrderDetailDomainServiceTest`
- `OrderFulfillmentDomainServiceTest`
- `OrderRefundDomainServiceTest`
- `FolwerAppletOrderReadControllerTest`
- `FolwerAppletOrderWriteControllerTest`
- `FolwerAppletOrderDetailControllerTest`
- `FolwerAppletOrderRefundControllerTest`
- `FolwerAppletOrderDvyControllerTest`
- `FolwerAppletOrderDetailServiceTest`
- `FolwerAppletOrderRefundServiceTest`
- `FolwerAppletOrderServiceImplTest`

### Transaction and user-asset backbone

- `WxPayCallbackControllerTest`
- `PaymentTransactionDomainServiceTest`
- `CouponAssetDomainServiceTest`
- `PointsAssetDomainServiceTest`
- `MemberAssetDomainServiceTest`
- `CouponAssetControllerTest`
- `FolwerLegacyCouponControllerTest`
- `PointsAssetControllerTest`
- `MemberAssetControllerTest`
- `MemberAppletAssetControllerTest`
- `MarketingCouponServiceImplTest`
- `MarketingCouponReceiveServiceImplTest`
- `MemberPurchaseRecordServiceImplTest`
- `MemberAppletPurchaseRecordServiceImplTest`
- `MemberPointsExchangeGoldServiceImplTest`

### Backbone isolation guard

- `BackboneIsolationGuardTest`

## Excluded from the Default Gate

The following areas are preserved but not part of the default backbone acceptance gate:

- courses
- community
- credit-mall order/product/category flows
- promotion reward / rebate / profit-sharing
- flower-only delivery configuration

These domains are edge or legacy capabilities. Their existence must not block the default backbone delivery unless they directly break compile or leak back into backbone domain rules.

## Delivery Rule

A stable batch is backbone-acceptable only when:

- compile passes
- the backbone test gate passes
- the branch is committed and pushed

This gate is the default repository contract after Stage 4 closure.
