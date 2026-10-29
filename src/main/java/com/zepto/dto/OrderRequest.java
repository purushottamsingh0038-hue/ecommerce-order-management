package com.zepto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderRequest(String orderNumber, LocalDate orderDate, BigDecimal totalAmount, String status) {
}