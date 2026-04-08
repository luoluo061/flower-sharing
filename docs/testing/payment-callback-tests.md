# Payment Callback Tests

## Purpose

This test set establishes a repeatable regression guard for the WeChat payment callback path before larger order and payment refactors.

Current coverage is split into two layers:

- Controller callback entry tests
- Service-layer payment status flow tests

## Test Classes

- `ruoyi-admin/src/test/java/org/dromara/flowerapplet/controller/WxPayCallbackControllerTest.java`
- `ruoyi-admin/src/test/java/org/dromara/flowerapplet/service/impl/FolwerAppletOrderServiceImplTest.java`

## Covered Behavior

### Controller layer

- `POST /wxpayback/pay/payCallback`
  - success response
  - exception response
- `POST /wxpayback/pay/refundCallback`
  - success response
  - exception response

### Service layer

- `refundOrder`
  - `SUCCESS / PROCESSING / ABNORMAL / CLOSED / null` status mapping
- `queryOrder`
  - blank order id returns `null`
  - missing transaction returns `null`
  - non-success payment state returns `null`
  - successful payment updates order state to `5`
- `payCallbackOrder`
  - missing transaction returns `null`
  - successful callback updates order state to `1`
  - user points update
  - product sold count / stock follow existing behavior
  - repeated callback stays idempotent for already-paid orders

## Run Command

Use the repo-local Maven installation and run from the repository root:

```powershell
& 'E:\flower-sharing\.tools\apache-maven-3.9.14\bin\mvn.cmd' `
  '-Duser.home=E:\flower-sharing' `
  '-Dmaven.repo.local=E:\flower-sharing\.m2\repository' `
  '-Dsurefire.failIfNoSpecifiedTests=false' `
  '-pl' 'ruoyi-admin' '-am' `
  '-Dtest=WxPayCallbackControllerTest,FolwerAppletOrderServiceImplTest' `
  'test'
```

## Important Note

The root `pom.xml` configures Surefire with `groups=${profiles.active}`. Under the default `dev` profile, JUnit 5 tests must be tagged with:

```java
@Tag("dev")
```

If that tag is missing, the build can succeed with `Tests run: 0`.

## Current Limitations

- These tests use mocks and spies; they do not call the real WeChat callback service.
- They do not yet cover:
  - real payment callback payload parsing
  - refund callback domain side effects
  - end-to-end order write flow
- `FolwerAppletOrderServiceImpl` now routes payment-success updates through shared helper methods, while the original duplicated blocks remain as compatibility fallback inside the service and can be removed in the next batch.
