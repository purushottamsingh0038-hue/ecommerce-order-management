package com.zepto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.zepto.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	List<OrderEntity> findByCustomerId(Long customerId);
}