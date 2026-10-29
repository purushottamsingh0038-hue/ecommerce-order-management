package com.zepto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zepto.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    // Fix 1: JOIN FETCH
    @Query("select c from CustomerEntity c left join fetch c.orders")
    List<CustomerEntity> findAllWithOrders();

    // Fix 2: @EntityGraph
    @EntityGraph(attributePaths = "orders")
    @Query("select c from CustomerEntity c")
    List<CustomerEntity> findAllWithOrdersGraph();
}