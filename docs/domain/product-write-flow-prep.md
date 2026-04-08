# Product Write Flow Preparation

## Current Backend Write Entry Points

### Category

- `POST /flower/category`
- `PUT /flower/category`
- `DELETE /flower/category/{ids}`

Current write-side behavior:

- add and edit are direct pass-through controller calls into `IFolwerCategoryService`
- delete performs product-existence validation inside `FolwerCategoryServiceImpl.deleteWithValidByIds`
- current cleanup status: delete validation is now isolated behind explicit helper methods, but the validation message and delete semantics remain unchanged

### Product

- `POST /flower/product`
- `PUT /flower/product`
- `DELETE /flower/product/{ids}`
- `PUT /flower/product/batchUpdateStatus/{ids}`

Current write-side behavior:

- add and edit are direct pass-through controller calls into `IFolwerProductService`
- delete is currently a direct pass-through service call
- batch status update mutates each selected product through `updateStatusByIds`
- current implementation sets selected product status to `0` regardless of the previous value; this behavior is now treated as locked current behavior until a later explicit write-path fix
- current cleanup status:
  - the batch status update loop is now expressed through a dedicated helper to make the locked behavior explicit without changing it
  - create / update / delete success paths are now covered in controller and service tests
  - create keeps the current `deliveryPrice -> 0` default when the incoming field is blank
  - update conversion is isolated behind an overridable helper so tests can avoid the static Spring-backed mapper dependency

### SKU

- `POST /flower/sku`
- `POST /flower/sku/batchAdd`
- `PUT /flower/sku`
- `DELETE /flower/sku/{skuIds}`

Current write-side behavior:

- SKU writes include product price and stock recalculation side effects
- current insert semantics:
  - disabled SKU rows are skipped during aggregate refresh
  - max price and min price are rounded to 2 decimals
  - total stock is not accumulated; it is overwritten by the last active SKU row
- current update semantics:
  - all SKU rows participate in aggregate refresh, including disabled rows
  - `derlinePrice` starts from `0`, so positive `minPrice` values do not lower it
  - total stock is not accumulated; it is overwritten by the last SKU row
- current batch-add semantics:
  - batch insert does not refresh product aggregate data
- this path is now in active stabilization and should be locked by tests before any semantic fix

### Product Detail

- `POST /flower/productDetail`
- `PUT /flower/productDetail`
- `DELETE /flower/productDetail/{detailIds}`

Current write-side behavior:

- product detail writes are comparatively isolated
- current stabilization status:
  - controller success paths are now covered for add / edit / remove
  - service behavior is locked as direct insert / update / delete with no extra side effects
  - conversion is isolated behind an overridable helper so tests can avoid the static Spring-backed mapper dependency

## Immediate Testing Scope

The current write-path test scope should stay limited to:

- category add
- category edit
- category delete validation
- product batch status update
- SKU add
- SKU edit
- SKU remove
- SKU batch add
- SKU aggregate refresh current behavior
- product detail add
- product detail edit
- product detail remove
- product add
- product edit
- product remove

The current write-path test scope should not yet include:

- product write-path side effects beyond current direct persistence
- SKU create/update/batchAdd side effects

## Next Safe Implementation Boundary

Before touching SKU or full product write flows, the following must already stay green:

- backend product read controller tests
- backend product read service tests
- mini-program product read controller tests
- mini-program product read service tests
- payment callback tests

## Current SKU Stabilization Rule

The next implementation batches may refactor SKU aggregate refresh into helpers, but they must not change:

- insert-path disabled-SKU filtering
- insert-path 2-decimal rounding
- update-path zero-based `derlinePrice` behavior
- non-accumulating `totalStocks` behavior
- batch-add controller success path
