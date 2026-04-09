# Stage 2: Order and Fulfillment Backbone

## Current status

Stage 2 is completed. The order backbone now runs through shared order-domain services, while backend and mini-program order services act as adapter-facing entrypoints. Public controller routes, BO/VO contracts, and database schema remain unchanged.

## Shared order-domain services

The shared Stage 2 rule layer is now centered on:

- `OrderLifecycleDomainService`
- `OrderDetailDomainService`
- `OrderFulfillmentDomainService`
- `OrderRefundDomainService`

These services now serve as the default rule entrypoints for:

- order lifecycle preparation
- order-detail aggregation and total-count attachment
- basic fulfillment and logistics shaping
- refund and backend refund-creation mutation preparation

## Implemented backbone coverage

Backend order-management coverage now includes:

- order list/detail/info controller regression
- order write controller regression
- order-detail controller regression
- order-refund controller regression
- backend order-detail service regression
- backend order-refund service regression

Mini-program order-center coverage now includes:

- order list/detail/query-order controller regression
- order write controller regression
- delivery-record controller regression
- order-detail controller regression
- order-refund controller regression
- applet order-detail service regression
- applet order-refund service regression

Shared domain-service regression now includes:

- `OrderLifecycleDomainServiceTest`
- `OrderDetailDomainServiceTest`
- `OrderFulfillmentDomainServiceTest`
- `OrderRefundDomainServiceTest`

The current Stage 2 closing gate is:

- `compile`
- full product + payment + order regression
- `Tests run: 140, Failures: 0, Errors: 0`

## Adapter-facing service structure

Backend services are now treated as management adapters over shared order rules:

- `FolwerOrderServiceImpl`
- `FolwerOrderDetailServiceImpl`
- `FolwerOrderRefundServiceImpl`

Mini-program services are now treated as client adapters over shared order rules:

- `FolwerAppletOrderServiceImpl`
- `FolwerAppletOrderDetailServiceImpl`
- `FolwerAppletOrderRefundServiceImpl`
- `FolwerAppletOrderDvyServiceImpl`

Adapter-layer responsibilities now include:

- current backend or mini-program response shaping
- user-context access
- payment/request parameter wiring
- adapter-specific presentation concerns

Shared order rules no longer need to be reimplemented separately in backend and applet service paths.

## Stage 2 boundary

Included in Stage 2:

- backend order list/detail/info
- backend refund creation preparation
- applet order list/detail/query-order
- applet order detail and refund adapter entrypoints
- order lifecycle state preparation
- order-detail aggregation
- delivery-record adapter coverage
- base fulfillment address/logistics-view shaping

Explicitly kept out of Stage 2:

- transaction and user-asset deep cleanup
- coupon or points settlement rules
- full after-sale system expansion
- naming unification
- legacy domain isolation

## Fulfillment boundary

Stage 2 keeps only standard mall fulfillment essentials in the main order backbone:

- freight amount
- shipment/update preparation
- logistics record/view shaping
- address and logistics display preparation

Flower-specific delivery expressions are now treated as edge capabilities rather than order-backbone rules:

- `DeliveryBox`
- `DeliveryTemperature`
- `DeliveryRule`
- `DeliverySet`
- other city, cold-chain, temperature, or insulation-specific delivery expressions

These modules are not deleted in Stage 2, but they no longer define the main order lifecycle.

## Stage 2 exit result

Stage 2 is considered complete because:

- backend and mini-program order services now sit on top of shared order-domain rules rather than maintaining two separate rule sets
- order lifecycle, order detail, fulfillment, and refund preparation each have a shared domain-service entry
- backend and applet read/write paths have controller and service regression coverage
- fulfillment and logistics backbone is limited to standard mall essentials, while flower-specific delivery expressions are no longer treated as core order-state drivers
- the codebase is stable enough to move into Stage 3: Transaction and User-Asset Backbone
