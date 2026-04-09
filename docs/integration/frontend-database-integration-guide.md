# Frontend And Database Integration Guide

## Goal

Use the current backend as the integration backend for:

- admin frontend
- applet frontend
- existing `flower_sharing` test database

This guide defines the expected backend-side integration setup.

## Backend Runtime Baseline

Primary application:

- `ruoyi-admin`

Primary runtime dependencies:

- MySQL
- Redis

Core API discovery endpoints:

- `/v3/api-docs`
- `/swagger-ui.html`

Backbone controller families expected to participate in integration:

- product:
  - `/flower/*`
  - `/flowerapplet/product*`
  - `/flowerapplet/category*`
  - `/flowerapplet/sku*`
  - `/flowerapplet/productDetail*`
- order and fulfillment:
  - `/flower/order*`
  - `/flower/orderDetail*`
  - `/flower/orderRefund*`
  - `/flower/orderDvy*`
  - `/flowerapplet/order*`
  - `/flowerapplet/orderDetail*`
  - `/flowerapplet/orderRefund*`
- transaction:
  - `/wxpayback/pay/*`
- assets:
  - membership controllers
  - points controllers
  - coupon controllers

## Database Integration Baseline

Default integration database:

- `flower_sharing`

Recommended usage rules:

- use this database for backbone integration only
- prefer known-good sample records for products, SKUs, member levels, coupons, and orders
- avoid using legacy-domain records as backbone validation samples
- isolate payment and refund validation records so they are easy to inspect and clean up

Recommended sample data buckets:

- admin login account
- applet login account
- valid product categories
- valid sellable products and SKUs
- at least one coupon definition and one coupon receive record
- at least one member level and privilege record
- at least one points configuration row
- dedicated test orders for pay / refund verification

## Admin Frontend Integration

The admin frontend should treat this backend as the default mall-management backend.

Admin integration priorities:

- login and token acquisition
- product management
- order management
- member / points / coupon management

Admin-side integration checks:

- the frontend base API URL points to the current backend
- RSA encryption / decryption settings match backend configuration when enabled
- login, menu, and permission behavior match the current backend auth model
- product CRUD pages use backbone endpoints, not legacy domains
- order pages use backbone list / detail / refund / delivery endpoints

Admin integration should **exclude by default**:

- courses
- community
- credit-mall product or credit-mall order pages
- promotion reward / rebate flows
- flower-only delivery configuration pages

## Applet Frontend Integration

The applet frontend should treat this backend as the default mall mini-program backend.

Applet integration priorities:

- applet login
- category / product / SKU browsing
- order creation
- submit payment
- query order
- refund request
- order detail and delivery record view
- membership / points / coupon view

Applet-side integration checks:

- applet login path and login payload format match current backend expectations
- product list / detail / SKU requests use backbone routes
- order submit and order query results are consumable by the applet UI
- payment request response can be handed to the mini-program payment client
- post-payment order refresh reflects paid state correctly
- refund request and refund-result refresh are visible in the applet order center

Applet integration should **exclude by default**:

- course purchase flows
- community flows
- credit-mall order / product / category flows

## Payment And Callback Integration

The backend already supports payment and refund callbacks, but real integration still requires:

- valid WeChat payment configuration
- public callback reachability
- applet-side payment invocation
- refund callback verification

Required integration checkpoints:

- create order from applet
- submit payment
- receive payment callback
- verify backend order state mutation
- query order state from applet
- submit refund request
- receive refund callback
- verify backend and applet refund state consistency

## What This Repository Does Not Provide

This repository does not by itself provide:

- the admin frontend codebase
- the applet frontend codebase
- a public callback tunnel or reverse proxy setup
- production payment certificates or merchant account provisioning
- production deployment automation for the frontend applications
