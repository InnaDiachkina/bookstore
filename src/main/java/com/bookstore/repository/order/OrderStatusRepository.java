package com.bookstore.repository.order;

import com.bookstore.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByName(OrderStatus.StatusName name);
}
