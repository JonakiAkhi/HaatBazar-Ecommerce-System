# Notification Service – Haat-Bazar Backend

Matched against the team repo (`HaatBazar-Ecommerce-System` /
`haatbazar-backend`): same Spring Boot 3.5.15 / Spring Cloud 2025.0.3,
same `com.Haat-Bazar` groupId, same `com.Haat_Bazar.notification_service`
package style, same `eureka-server` URL, same MySQL `root/root` local
convention (`auth_db`, `product_db` → this one uses `notification_db`).

## Known ports in the team project

| Service | Port |
|---|---|
| eureka-server | 8761 |
| api-gateway | 8080 |
| auth-service | 8081 |
| Product-service | 8082 |
| order-service | 8083 (reserved by gateway route order, not yet configured) |
| payment-service | 8084 (reserved by gateway route order, not yet configured) |
| **notification-service** | **8085** |

If a teammate later sets order/payment to a different port, this won't clash.
Just double check before everyone runs together.

## Run it standalone (to demo just your part)

You don't need MySQL or Eureka running for this — open
`application.properties` and swap to the commented-out H2 block at the
bottom (comment out the MySQL + eureka lines, uncomment the H2 ones).

1. Open this folder in IntelliJ (`File > Open`, pick the folder with `pom.xml`)
2. Run `NotificationServiceApplication`
3. Test:
   - `POST http://localhost:8085/api/notifications`
     ```json
     {
       "userId": 1,
       "type": "ORDER_PLACED",
       "message": "Your order #102 has been placed!",
       "channel": "EMAIL"
     }
     ```
   - `GET http://localhost:8085/api/notifications/user/1`
   - DB viewer (H2 mode only): `http://localhost:8085/h2-console` → JDBC URL `jdbc:h2:mem:notificationdb`, user `sa`, no password

## Run it integrated with the real team project

1. **Get MySQL running locally** with a `root` user, password `root` (same as
   `auth-service`/`Product-service` expect). The `notification_db` database
   doesn't need to be created manually — Hibernate creates tables on startup.
2. **Copy this folder** into `HaatBazar-Ecommerce-System/haatbazar-backend/`,
   as a sibling of `auth-service`, `Product-service`, `order-service`,
   `payment-service`.
3. **Start in this order:**
   1. `eureka-server` (port 8761) — wait until it's up
   2. `auth-service`, `Product-service`, `notification-service` (any order, after Eureka is up)
   3. `api-gateway` last
4. Check `http://localhost:8761` in a browser — you should see
   `NOTIFICATION-SERVICE` listed as registered alongside the others.

## Gateway route — give this to whoever owns `api-gateway`

Their `application.properties` already has routes `[0]` through `[3]` for
auth/product/order/payment. Add this as the next one:

```properties
# ---- Notification Service Route ----
spring.cloud.gateway.mvc.routes[4].id=notification-service
spring.cloud.gateway.mvc.routes[4].uri=lb://NOTIFICATION-SERVICE
spring.cloud.gateway.mvc.routes[4].predicates[0]=Path=/api/notifications/**
```

After that, the rest of the team can call it through the gateway at
`http://localhost:8080/api/notifications` instead of port 8085 directly.

## Calling this from other services (Order, Payment)

Once everything is registered with Eureka, another service can call this
one by name instead of `localhost:8085` — e.g. a `RestTemplate` or
`WebClient` configured with `@LoadBalanced`, calling
`http://notification-service/api/notifications`.

## Files in this module

```
notification-service/
├── pom.xml
└── src/main/
    ├── java/com/Haat_Bazar/notification_service/
    │   ├── NotificationServiceApplication.java
    │   ├── entity/        (Notification, NotificationType, NotificationChannel, NotificationStatus)
    │   ├── dto/            (NotificationRequest, NotificationResponse)
    │   ├── repository/    (NotificationRepository)
    │   ├── service/        (NotificationService)
    │   ├── controller/    (NotificationController)
    │   └── exception/    (ResourceNotFoundException, GlobalExceptionHandler)
    └── resources/
        └── application.properties
```
