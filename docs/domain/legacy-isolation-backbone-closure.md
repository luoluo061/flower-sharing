# Stage 4: Legacy Isolation and Backbone Closure

## Current status

Stage 4 is **completed**. The repository can now be described as a **single-merchant standard mall backbone** with explicit legacy edge domains preserved for compatibility.

Stage 4 did not remove historical modules or change public paths. Instead, it finished the repository-level closure work:

- the backbone story in code, docs, and regression gates is aligned
- legacy domains are explicitly edge-isolated
- historical naming is frozen instead of being allowed to keep expanding
- the default repository acceptance gate now targets the mall backbone only

## Backbone default scope

The default backbone now means:

- product center
- order and fulfillment backbone
- transaction backbone
- user-asset backbone

The mainline repository story is therefore:

- backend mall management
- applet mall flows
- shared domain-service rules for product, order, transaction, and assets
- backbone-first regression and delivery

## Edge-isolated legacy domains

These domains are preserved but are no longer part of the default mall backbone:

- courses:
  - `Courses*`
  - `CoursesApplet*`
- community:
  - `FlowerFriendsCommunity*`
  - `FlowerAppletFriendsCommunity*`
- credit-mall:
  - `FolwerCreditOrder*`
  - `FolwerCreditProduct*`
  - `FolwerCreditCategory*`
  - `FolwerAppletCreditOrder*`
  - `FolwerAppletCreditProduct*`
  - `FolwerAppletCreditCategory*`
- promotion, rebate, and profit-sharing:
  - `MarketingMemberPromotionPlan*`
  - `MarketingMemberPromotionPecord*`
  - order/member side promotion and rebate fields
- flower-only delivery configuration:
  - `FolwerDeliveryBox*`
  - `FolwerDeliveryTemperature*`
  - `FolwerDeliveryRule*`
  - `FolwerDeliverySet*`

These capabilities are intentionally **not deleted** in Stage 4. Their current treatment is:

- kept for compatibility
- documented as edge/legacy
- excluded from the default backbone delivery gate
- not allowed to define backbone behavior

## Code-side isolation result

Stage 4 closes the backbone by enforcing these code-side rules:

- backbone domain services must not depend on course, community, credit-mall, promotion, or flower-only delivery configuration types
- backbone delivery documentation must not present those legacy domains as default runtime scope
- legacy edge behaviors that still exist in compatible entrypoints are explicitly treated as compatibility boundaries rather than backbone rules

Important examples:

- applet order profit-sharing remains a compatibility edge path, not a backbone transaction rule
- member promotion side effects remain a compatibility edge path, not a backbone asset rule
- `CreditOrder:` Redis expiry handling remains a credit-mall edge behavior, not part of the default mall transaction flow

## Naming closure rule

Stage 4 does **not** do a big-bang rename. Instead it freezes historical naming spread.

Current compatibility names remain valid:

- `Folwer`
- `flowerapplet`
- `flowerApplet`
- `applet/flower`

But new internal abstractions and documentation should prefer backbone-oriented names:

- `product`
- `order`
- `transaction`
- `asset`
- `fulfillment`
- `legacy`
- `backbone`

## Delivery result

Stage 4 is complete because:

- the repository README describes the mall backbone first
- the roadmap marks Stages 1 through 4 as complete
- legacy domains are written down as excluded edge capabilities
- the final backbone regression gate is explicit and no longer mixes legacy domains into the default acceptance story

The repository should now be treated as a stabilized single-merchant mall backbone with preserved legacy compatibility zones, not as a flower-business system that happens to contain mall logic.
