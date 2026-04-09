# Product Line Shared Boundary

## Current Shared Behaviors

The backend `org.dromara.flower.*` stack and the mini-program `org.dromara.flowerapplet.*` stack currently implement the same product concepts separately. The following behaviors are the effective shared boundary of the product line:

1. Category tree assembly
- Backend and mini-program both attach child categories when the current node is not a root node.
- This is currently API-facing behavior rather than a shared domain primitive.

2. Product category-name presentation
- Backend product reads assemble a display name from parent and child category names.
- Mini-program product reads do not currently reuse that backend presentation logic.

3. SKU picture URL fill
- Backend and mini-program both translate stored OSS ids into display URLs for SKU pictures.
- This is adapter-layer behavior and should stay outside the core product model.

4. Price visibility
- Mini-program reads apply guest and pending-auth masking.
- Backend reads expose raw prices.
- This is a client-surface rule, not a shared product-domain rule.

5. Product detail expansion
- Backend and mini-program both expose product-detail records, but through different service stacks.
- The current implementation duplicates query entrypoints instead of centralizing detail lookup.

6. Product write defaults
- Backend product writes currently normalize `deliveryPrice` inside `FolwerProductServiceImpl`.
- This is not a controller concern and should stay in the product service boundary until a shared product write service exists.

7. SKU aggregate refresh
- Backend SKU writes recalculate product price and stock snapshots after insert and update.
- This is product-domain behavior, even though the current implementation lives inside the SKU service.

8. Product detail writes
- Backend product-detail writes are still direct CRUD pass-throughs with no shared write abstraction.
- This is a separate detail subdomain and should not be folded into product core write rules until the migration blueprint is executed.

## Shared Helper and Domain-Service Layer

The current branch now has a first shared-helper layer under `org.dromara.flower.service.support`, and the first shared product-domain service layer under `org.dromara.flower.service.domain`.

- `ProductCategoryHierarchySupport`
  - owns the current shared "should children be populated" rule and generic children attachment helper
  - is now used by both backend and mini-program category services
- `ProductWriteDefaultsSupport`
  - owns the current backend product write default normalization for `deliveryPrice`
  - is now the single place for the current `null -> 0` default
- `ProductSkuAggregateSupport`
  - owns the current SKU aggregate snapshot rules already locked by tests
  - keeps current insert/update semantics unchanged while moving calculation out of `FolwerSkuServiceImpl`

The support layer remains intentionally low-level. Product-domain ownership is now partially implemented above it through:

- `ProductCategoryDomainService`
  - owns category hierarchy semantics for backend and mini-program entry services
- `ProductCoreDomainService`
  - owns currently locked product write defaults and current status-mutation preparation
- `ProductSkuAggregateDomainService`
  - owns currently locked SKU aggregate snapshot semantics and applies them back to product BOs
- `ProductDetailDomainService`
  - owns current product-detail create/update/delete preparation semantics

Backend and mini-program services still coexist, but product-domain rules should now flow through these domain services rather than being re-expressed inside each service implementation.

## Target Ownership

The long-term ownership of these behaviors should be:

- Product-domain layer:
  - Category hierarchy semantics
  - Product/category relationship
  - SKU-to-product relationship
  - Product detail lookup semantics
  - Product write defaults
  - SKU aggregate refresh semantics
  - Product detail write semantics
- API adapter layer:
  - Price masking
  - Category display-name formatting
  - OSS id to URL expansion
  - Client-specific response decoration

## Immediate Rule

Until a dedicated shared product module exists:

- Do not merge backend and mini-program services.
- Do not introduce new duplicated helper logic for the shared behaviors above.
- New logic should either:
  - reuse an existing domain service or helper inside the current stack, or
  - be documented here first if it changes one of the shared behaviors.

## Immediate Migration Targets

The next product-center extraction steps should treat the current shared boundary as three buckets:

1. Product-domain candidates
- category hierarchy helpers and domain services
- product write default normalization
- SKU aggregate recalculation
- product-detail lookup and write-preparation semantics

2. API-adapter candidates
- category display names
- OSS id to display URL expansion
- mini-program price masking

3. Deferred compatibility layer
- duplicated backend/applet service entrypoints
- historical `Folwer` naming
- current URL layout under `/flower/*` and `/flowerapplet/*`
