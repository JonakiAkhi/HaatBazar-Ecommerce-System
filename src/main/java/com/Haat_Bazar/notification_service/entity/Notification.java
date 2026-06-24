package com.Haat_Bazar.notification_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity — each instance of this class becomes one row in the
 * "notifications" table in MySQL.
 *
 * Lombok annotations:
 *   @Data           → auto-generates getters, setters, toString, equals, hashCode
 *   @NoArgsConstructor → empty constructor (JPA needs this)
 *   @AllArgsConstructor → constructor with all fields
 *
 * JPA annotations:
 *   @Entity  → marks this as a database table
 *   @Table   → lets us choose the table name
 *   @Id + @GeneratedValue → auto-incrementing primary key
 *   @Column  → customises the column (nullable, length, etc.)
 */
@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The email address the notification was sent to */
    @Column(nullable = false)
    private String recipient;

    /** Email subject line */
    @Column(nullable = false)
    private String subject;

    /** Email body text */
    @Column(nullable = false, length = 2000)
    private String message;

    /** "SENT" or "FAILED" */
    @Column(nullable = false)
    private String status;

    /** When the notification was created/sent */
    @Column(nullable = false)
    private LocalDateTime sentAt;
}
