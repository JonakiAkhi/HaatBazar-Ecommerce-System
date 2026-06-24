package com.Haat_Bazar.notification_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) — this is what the client sends in the
 * POST request body as JSON.
 *
 * Example JSON:
 * {
 *   "email":   "customer@example.com",
 *   "subject": "Order Confirmed",
 *   "message": "Your order #123 has been placed successfully!"
 * }
 *
 * The @NotBlank and @Email annotations automatically validate the input.
 * If validation fails, Spring returns a 400 Bad Request with error details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    private String email;

    @NotBlank(message = "subject is required")
    private String subject;

    @NotBlank(message = "message is required")
    private String message;
}
