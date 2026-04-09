# Integration Acceptance Checklist

## Backbone Readiness

- `ruoyi-admin` starts successfully
- MySQL connection is healthy
- Redis connection is healthy
- `/v3/api-docs` is reachable
- backbone regression gate remains green before frontend integration begins

## Admin Frontend Acceptance

- admin login succeeds
- category management page loads and operates normally
- product management page loads and operates normally
- SKU management page loads and operates normally
- product detail management page loads and operates normally
- order list and order detail pages load normally
- refund creation flow works from the backend UI path
- membership pages load normally
- points configuration and points ledger pages load normally
- coupon definition and coupon receive pages load normally

## Applet Frontend Acceptance

- applet login succeeds
- category list loads normally
- product list loads normally
- product detail loads normally
- SKU selection and display work normally
- order creation succeeds
- submit-payment response is accepted by the applet frontend
- query-order reflects current order state
- refund request succeeds
- order detail and delivery record views load normally
- membership, points, and coupon views load normally

## Database Acceptance

- backbone product records are readable and writable
- backbone order records are readable and writable
- membership records are readable and writable
- points backbone records are readable and writable
- coupon backbone records are readable and writable
- dedicated payment / refund test records are identifiable

## Payment Acceptance

- a real payment request can be created from the applet flow
- payment callback reaches the backend
- order state changes after payment callback are correct
- refund request can be submitted
- refund callback reaches the backend
- refund-related order state changes are correct

## Explicitly Out Of Default Acceptance

- courses
- community
- credit-mall order / product / category flows
- promotion reward / rebate / profit-sharing flows
- flower-only delivery configuration flows
