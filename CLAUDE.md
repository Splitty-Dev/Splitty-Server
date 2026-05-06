# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Splitty is a bulk-purchase splitting platform (소분 거래 플랫폼) — users near each other split large-quantity products. Backend is Java 21 / Spring Boot 3.5 with a multi-module Gradle setup.

## Build & Run Commands

```bash
# Build all modules
./gradlew build

# Build without tests
./gradlew build -x test

# Run the API server (main application)
./gradlew :api:bootRun

# Run the Scheduler application
./gradlew :scheduler:bootRun

# Run all tests
./gradlew test

# Run a single test class
./gradlew :api:test --tests "com.chaegangjo.chat.application.GetChatMessagesUseCaseTest"

# Run a single test method
./gradlew :api:test --tests "com.chaegangjo.chat.application.GetChatMessagesUseCaseTest.getByCursor"

# Clean QueryDSL generated sources
./gradlew :core:clean

# Build Docker image for linux/amd64 and push
./ci.sh
```

## Local Infrastructure

Start local MySQL and Redis via Docker Compose before running the app:

```bash
docker-compose up -d db redis
```

- MySQL: `localhost:3309` (mapped from 3306), DB `splitty-db`, user `admin`/`adminpw`
- Redis: `localhost:6380` (mapped from 6379)

The `application.yml` defaults to `local` profile, which connects to these ports.

## Module Architecture

The project is a **multi-module Gradle project** with the following dependency flow:

```
common  ←── core  ←── api       (bootJar: splitty-api.jar)
                  ←── websocket (library, included by api)
                  ←── scheduler (bootJar: splitty-scheduler.jar)
```

| Module      | Purpose |
|-------------|---------|
| `common`    | Shared DTOs, exceptions/error codes, Redis config/utils, S3 utils, JWT properties, pagination helpers, logging interfaces |
| `core`      | JPA entities, repositories (Spring Data JPA + QueryDSL), domain services, FCM service, OpenFeign clients |
| `api`       | HTTP layer — Spring MVC controllers, UseCase classes (application layer), Spring Security + OAuth2/JWT config |
| `websocket` | STOMP WebSocket config and chat message handler (included as a library dependency in `api`) |
| `scheduler` | Standalone Spring Boot app — flushes buffered user action logs to S3 every 5 minutes |

## Application Layer Pattern (UseCase)

The `api` module uses explicit **UseCase classes** (in `application` packages) instead of calling services directly from controllers. Each UseCase is a `@Component` with a single `execute(...)` method. Controllers inject UseCases; UseCases inject core services.

Example flow: `GoodsController` → `GetAllGoodsUseCase` → `GoodsService` + `RecommendationOpenFeign`

## Key Architectural Decisions

### Location-based filtering via Redis GEO
Member and Goods locations are stored in Redis using GEO commands (`MEMBER_KEY`, `GOODS_KEY`). When listing goods, `RedisUtil.getNearByIds()` performs a geo-radius search to return only goods within `RESTRICT_DISTANCE` (300,000 meters) of the member. Goods geo entries store `"goodsId:categoryId"` as the value to enable category filtering.

### Cursor-based pagination
Two cursor strategies exist in `common`:
- `CursorPage` — ID-only cursor (used for goods feed)
- `IdCreatedAtCursorPage` — ID + `createdAt` cursor (used for chat messages, search history, etc.)

Repositories expose `findAllByCursor(...)` methods returning Spring Data `Slice<T>`.

### Pessimistic locking on trade participation
`GoodsRepository.findByIdForUpdate()` uses `@Lock(PESSIMISTIC_WRITE)` to prevent race conditions when multiple users join the same trade simultaneously.

### User action logging
`UserActionLogger` (interface in `common`, implemented in `core` as `S3ActionLoggerImpl`) buffers user actions in memory. The `scheduler` module's `UserActionFlushScheduler` calls `flush()` every 3 seconds (configured via `@Scheduled(fixedRate = 3000)`), writing batched logs to S3 for the AI recommendation service.

### AI recommendations via OpenFeign
`RecommendationOpenFeign` calls an external Python/FastAPI recommendation service. `GetAllGoodsUseCase.executeWithRecommendation()` fetches ranked goods IDs from the AI service, then loads entities from the DB.

## Exception Handling

All domain exceptions extend `BaseException` with a `BaseErrorCode`. Each domain has its own exception class (e.g., `GoodsException`, `TradeException`) and error code enum (e.g., `GoodsErrorCode`). `GlobalExceptionHandler` in `api` catches these and returns structured error responses.

## Security

- Stateless JWT authentication via `JwtAuthenticationFilter`
- Kakao OAuth2 login; on success `OAuth2AuthenticationSuccessHandler` issues a JWT
- `@AuthenticationPrincipal CustomOAuth2User` provides the current user in controllers
- WebSocket connections authenticated via STOMP channel interceptor (`StompChannelInterceptor`)

## QueryDSL

QueryDSL Q-classes are generated into `core/src/main/generated/`. After changing entities, run `./gradlew :core:compileJava` to regenerate. Custom repository pattern: interface (e.g., `GoodsCustomRepository`) + impl (e.g., `GoodsCustomRepositoryImpl`) injected alongside the Spring Data JPA repository.
