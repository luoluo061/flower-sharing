# Database Backbone Audit

## Conclusion

The current system **can continue using the existing `flower_sharing` database** for integration and frontend development.

At the same time, the current database should be treated as a **mixed runtime database**, not as the final clean database for a standard single-merchant mall.

Recommended strategy:

- short to medium term:
  - continue using the current `flower_sharing` database for backend and frontend integration
- medium to long term:
  - derive a new **backbone-only** database from the current one
  - keep only the standard mall backbone tables and sample data

## Why The Current Database Is Usable

The backend backbone already runs against the current business database shape, and the following backbone table families are clearly present in code and mapper usage:

- product center:
  - `folwer_category`
  - `folwer_product`
  - `folwer_sku`
  - `folwer_product_detail`
- order and fulfillment:
  - `folwer_order`
  - `folwer_order_detail`
  - `folwer_order_refund`
  - `folwer_order_dvy`
- user and membership:
  - `applet_user_information`
  - `member_level`
  - `member_level_privilege`
  - `member_purchase_record`
- coupons and standard points backbone:
  - `marketing_coupon`
  - `marketing_coupon_receive`
  - `folwer_coupon`
  - `folwer_coupon_receive`
  - `folwer_credit_set`
  - `folwer_credit_getrecords`
  - `member_points_exchange_gold`

This means the current database already contains the operational schema needed for:

- admin mall management
- applet mall browsing and ordering
- payment and refund backbone flows
- membership, points, and coupon backbone flows

## Why The Current Database Is Not The Final Target Database

The same runtime database also still carries legacy business domains that are not part of the standard mall backbone:

- courses:
  - `courses_*`
- community:
  - `flower_friends_community*`
- credit-mall:
  - `folwer_credit_order*`
  - `folwer_credit_product*`
  - `folwer_credit_category*`
- promotion / rebate / profit-sharing:
  - `marketing_member_promotion_*`
  - related promotion fields in order/member flows
- flower-only delivery configuration:
  - `folwer_delivery_box`
  - `folwer_delivery_temperature`
  - `folwer_delivery_rule`
  - `folwer_delivery_set`

Because of that, the current database is best described as:

- a valid **integration runtime database**
- a valid **sample-data source**
- **not** the final backbone-only mall database

## Recommended Database Strategy

### Phase A: Keep Using The Existing Database

Use the current `flower_sharing` database as the active integration database for:

- backend verification
- new admin frontend development
- new applet frontend development
- payment and refund verification

Rules for Phase A:

- backbone sample data should be prepared and documented
- legacy-domain data should not be used as default integration samples
- payment and refund test orders should be easy to identify and isolate

### Phase B: Build A Backbone-Only Database

After the new frontend is stable against the current backend, derive a clean database from the current one.

The future backbone-only database should keep:

- platform tables:
  - required `sys_*` runtime tables
- product backbone tables
- order and fulfillment backbone tables
- transaction-related order state storage
- membership backbone tables
- standard points backbone tables
- coupon backbone tables

The future backbone-only database should exclude:

- courses
- community
- credit-mall order/product/category
- promotion reward / rebate / profit-sharing
- flower-only delivery configuration

## Practical Decision

If the immediate goal is:

- verify backend usability
- start frontend integration
- start admin and applet development

then the correct choice is:

- **use the current `flower_sharing` database now**

If the goal becomes:

- create a clean production-ready mall-only data model
- reduce long-term legacy drag
- simplify future maintenance and onboarding

then the correct next choice is:

- **clone and trim the current database into a backbone-only database**

## Current Default

Until frontend integration is stable, the repository should treat the current database as:

- the default integration database
- the source of truth for realistic sample data
- the source schema from which a future backbone-only database will be derived
