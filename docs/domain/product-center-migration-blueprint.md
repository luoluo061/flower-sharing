# Product Center Migration Blueprint

## Summary

The current product line has enough read and write regression coverage to stop treating backend and mini-program behavior as isolated cleanup targets. The next migration phase should move toward a product-center shape without changing current HTTP paths or forcing an immediate package merge.

This blueprint fixes the target ownership model and the migration order so later implementation batches can keep working without re-deciding domain boundaries.

## Target Subdomains

### 1. Category subdomain

Owns:

- category hierarchy semantics
- parent/child traversal rules
- category-to-product relationship metadata

Should not own:

- display-name formatting for specific clients
- controller-specific response decoration

### 2. Product core subdomain

Owns:

- product base fields
- product write defaults such as current `deliveryPrice` normalization
- product lifecycle status mutations

Should not own:

- OSS display URL expansion
- mini-program price masking

### 3. SKU subdomain

Owns:

- SKU persistence
- SKU-to-product aggregate refresh semantics
- product price/stock snapshot recalculation

Should not own:

- mini-program SKU label decoration
- client-facing URL translation

### 4. Product detail subdomain

Owns:

- detail record lookup semantics
- detail CRUD semantics
- detail-to-SKU relationship

Should not own:

- product core write defaults
- category presentation rules

### 5. API adapter layer

Owns:

- backend-specific response formatting
- mini-program-specific response formatting
- category display-name formatting
- OSS id to URL expansion
- mini-program price masking

Should not own:

- aggregate recalculation
- domain write defaults
- detail persistence rules

## Current-to-Target Mapping

The existing code should be treated as follows:

- `FolwerCategoryServiceImpl` and `FolwerAppletCategoryServiceImpl`
  - keep as entrypoint-specific services for now
  - gradually push hierarchy rules toward a shared category-domain helper layer
- `FolwerProductServiceImpl`
  - keep as backend write/read entrypoint
  - treat product defaults and status behavior as future product-core logic
- `FolwerSkuServiceImpl`
  - keep as backend SKU write entrypoint
  - treat aggregate refresh logic as future SKU-domain logic
- `FolwerProductDetailServiceImpl`
  - keep as product-detail subdomain entrypoint
  - do not merge into product core write logic
- applet product and SKU services
  - keep client-specific price masking and response behavior in adapter-facing code

## Migration Sequence

### Phase 1: stabilize helpers in place

- continue extracting explicit helpers inside current services
- do not move classes across packages
- do not rename URLs or types

### Phase 2: introduce shared domain helpers

- extract non-HTTP, non-decoration helpers for:
  - category hierarchy
  - product defaults
  - SKU aggregate refresh
  - product-detail lookup
- keep backend and mini-program services calling those helpers

### Phase 3: introduce shared domain services

- wrap the helper layer in shared product-domain services
- keep existing controller/service interfaces as compatibility entrypoints
- avoid changing BO/VO contracts in the same batch

### Phase 4: compatibility cleanup

- only after the shared domain layer is stable:
  - consider naming cleanup
  - consider backend/applet service consolidation
  - consider path and API surface cleanup

## Locked Compatibility Rules

Before the blueprint is actively implemented, the following stay fixed:

- no database schema changes
- no `/flower/*` or `/flowerapplet/*` path changes
- no forced rename of `Folwer` symbols
- no silent semantic change to current write-path defaults or SKU aggregate behavior
- no weakening of the existing 75-test regression baseline

## Required Validation During Blueprint Execution

Every future migration batch derived from this blueprint must still pass:

- single-process `compile`
- backend product read/write regression
- mini-program product read regression
- payment callback regression

If a later batch changes product-core or SKU semantics, that batch must first add tests that lock the current behavior before changing it.
