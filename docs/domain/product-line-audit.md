# Product Line Audit

## Current Scope

The current product domain is split into two parallel implementations inside `ruoyi-flower`:

- Backend management stack under `org.dromara.flower.*`
- Mini-program read and lightweight management stack under `org.dromara.flowerapplet.*`

Both stacks currently own the same core product concepts:

- Category
- Product
- SKU
- Product detail

The backend stack additionally exposes `ProductComm`, which is not part of the minimum mall product backbone.

## Current HTTP Surface

### Backend management

- `/flower/category`
- `/flower/product`
- `/flower/sku`
- `/flower/productDetail`

### Mini-program

- `/flowerapplet/category`
- `/flowerapplet/product`
- `/flowerapplet/sku`
- `/flowerapplet/productDetail`

## Current Structure

### Backend management stack

- Controllers:
  - `FolwerCategoryController`
  - `FolwerProductController`
  - `FolwerSkuController`
  - `FolwerProductDetailController`
- Services:
  - `IFolwerCategoryService` / `FolwerCategoryServiceImpl`
  - `IFolwerProductService` / `FolwerProductServiceImpl`
  - `IFolwerSkuService` / `FolwerSkuServiceImpl`
  - `IFolwerProductDetailService` / `FolwerProductDetailServiceImpl`
- Domain models:
  - `FolwerCategory*`
  - `FolwerProduct*`
  - `FolwerSku*`
  - `FolwerProductDetail*`

### Mini-program stack

- Controllers:
  - `FolwerAppletCategoryController`
  - `FolwerAppletProductController`
  - `FolwerAppletSkuController`
  - `FolwerAppletProductDetailController`
- Services:
  - `IFolwerAppletCategoryService` / `FolwerAppletCategoryServiceImpl`
  - `IFolwerAppletProductService` / `FolwerAppletProductServiceImpl`
  - `IFolwerAppletSkuService` / `FolwerAppletSkuServiceImpl`
  - `IFolwerAppletProductDetailService` / `FolwerAppletProductDetailServiceImpl`
- Domain models:
  - `FolwerAppletCategory*`
  - `FolwerAppletProduct*`
  - `FolwerAppletSku*`
  - `FolwerAppletProductDetail*`

## Main Observations

1. Product logic is duplicated by client surface instead of being expressed as a shared product domain with separate API adapters.
2. Historical `Folwer` naming remains the default naming convention across both stacks.
3. Public mini-program read APIs are the safest first stabilization target because they are mostly read-only and already exposed without strict permission checks.
4. Backend product management currently mixes read and write operations in the same controllers, so it is a worse first target for code cleanup than the mini-program read chain.
5. `ProductComm` should be treated as an adjacent extension, not part of the first-pass standard mall product backbone.

## First Execution Batch

The first product-line batch should stay limited to:

- Mini-program category read APIs
- Mini-program product read APIs
- Mini-program SKU read APIs
- Mini-program product detail read APIs
- Controller-level regression tests for those endpoints

This batch explicitly does not change:

- Database schema
- Existing URL paths
- Response payload shape
- Backend management write flows
- Product naming compatibility

## Current Regression Coverage

Current automated regression now covers both public mini-program reads and backend management reads:

- Mini-program controller tests:
  - category list / allList / detail
  - product list / detail / category query
  - SKU list / detail
  - product detail list / by-sku query
- Backend controller tests:
  - category list / allList / detail
  - product list / allList / detail
  - SKU list / detail
  - product detail list / detail
- Service-level tests:
  - mini-program category child population and sort behavior
  - mini-program product price masking for guest / pending-auth / authenticated reads
  - mini-program SKU display name and price masking behavior
  - backend category child population
  - backend product category-name composition
  - backend SKU picture URL fill behavior
  - backend category delete validation
  - backend product status update current behavior lock

- Write-path controller tests:
  - backend category add / edit / remove
  - backend product batch status update
  - backend SKU add / batchAdd / edit / remove
  - backend product detail add / edit / remove
- First write-path cleanup:
  - backend category delete validation is isolated behind dedicated helpers
  - backend product batch status update now expresses the locked `status -> 0` behavior explicitly
  - backend SKU aggregate refresh is isolated behind explicit insert/update helpers while preserving current semantics
- Write-path service tests:
  - SKU insert aggregate refresh current behavior
  - SKU update aggregate refresh current behavior
  - product detail direct insert current behavior
  - product detail direct update current behavior
  - product detail direct delete current behavior

These tests are intentionally scoped to read flows and are meant to be run together with the existing payment callback regression tests as a shared safety net.

## Current Verification Baseline

The current product-line branch should be verified with a single-process Maven run. Running multiple Maven compile/test processes in parallel can cause `ruoyi-flower/target/generated-sources` to regenerate conflicting `AutoMapperConfig__*` references during the same build window.

Current stable verification commands:

- `mvn ... -pl ruoyi-admin -am -DskipTests compile`
- `mvn ... -pl ruoyi-admin -am -Dtest=FolwerCatalogReadControllerTest,FolwerCatalogReadServiceTest,FolwerAppletCatalogReadControllerTest,FolwerAppletCatalogReadServiceTest,WxPayCallbackControllerTest,FolwerAppletOrderServiceImplTest test`

## Follow-up Batches

### Batch 2

- Backend management read-path regression tests
- Read-path cleanup in backend services

### Batch 3

- Backend management write-path regression strategy
- Product write-path cleanup

### Batch 4

- Shared product-domain extraction design
- Naming and API consolidation design
