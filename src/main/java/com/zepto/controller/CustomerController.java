package com.zepto.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zepto.dto.*;
import com.zepto.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest req) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createCustomer(req));
	}

	@PostMapping("/{id}/orders")
	public ResponseEntity<OrderResponse> createOrder(@PathVariable Long id, @RequestBody OrderRequest req) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(id, req));
	}

	@GetMapping
	public List<CustomerResponse> getAllCustomers() {
		return service.getAllCustomers();
	}

	@GetMapping("/{id}")
	public CustomerResponse getCustomer(@PathVariable Long id) {
		return service.getCustomerById(id);
	}

	@GetMapping("/{id}/orders")
	public List<OrderResponse> getOrders(@PathVariable Long id) {
		return service.getOrdersOfCustomer(id);
	}
	
	@GetMapping("/demo/n-plus-one")
	public List<String> nPlusOneDemo() {
	    return service.nPlusOneDemo();
	}
	
	@GetMapping("/demo/join-fetch")
	public List<String> joinFetchDemo() {
	    return service.joinFetchDemo();
	}

	@GetMapping("/demo/entity-graph")
	public List<String> entityGraphDemo() {
	    return service.entityGraphDemo();
	}
}