package com.Haat_Bazar.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The main entry point of the Notification Service.
 *
 * @SpringBootApplication  — tells Spring Boot to auto-configure everything.
 * @EnableAsync            — allows methods annotated with @Async to run in a
 *                           separate thread, so sending an email doesn't block
 *                           the HTTP response.
 *
 * NOTE: @EnableEurekaClient is no longer needed in Spring Cloud 2022+.
 * Simply having the eureka-client dependency + the eureka URL in
 * application.properties is enough to register with Eureka automatically.
 */
@SpringBootApplication
@EnableAsync
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
