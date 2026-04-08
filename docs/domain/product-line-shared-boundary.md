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

## Target Ownership

The long-term ownership of these behaviors should be:

- Product-domain layer:
  - Category hierarchy semantics
  - Product/category relationship
  - SKU-to-product relationship
  - Product detail lookup semantics
- API adapter layer:
  - Price masking
  - Category display-name formatting
  - OSS id to URL expansion
  - Client-specific response decoration

## Immediate Rule

Until a dedicated shared product module exists:

- Do not merge backend and mini-program services.
- Do not introduce new duplicated helper logic for the 5 shared behaviors above.
- New logic should either:
  - reuse an existing helper inside the current stack, or
  - be documented here first if it changes one of the shared behaviors.
