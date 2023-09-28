package com.bookstore.repository.order;

import com.bookstore.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByName(Status.StatusName name);
}
