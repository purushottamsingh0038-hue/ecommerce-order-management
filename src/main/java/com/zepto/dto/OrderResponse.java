package com.zepto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderResponse(Long id, String orderNumber, LocalDate orderDate, BigDecimal totalAmount, String status) {
}