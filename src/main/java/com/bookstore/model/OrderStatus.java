package com.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "statuses")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(unique = true, nullable = false)
    private StatusName name;

    public enum StatusName {
        NEW,
        PROCESSING,
        PAID,
        SHIPPED,
        DELIVERED,
        CANCELLED,
        REFUNDED,
        COMPLETED
    }
}
