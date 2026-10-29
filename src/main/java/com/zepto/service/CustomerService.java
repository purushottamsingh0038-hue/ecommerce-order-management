package com.zepto.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zepto.dto.*;
import com.zepto.entity.*;
import com.zepto.exception.ResourceNotFoundException;
import com.zepto.repository.*;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final OrderRepository orderRepository;

	public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
		this.customerRepository = customerRepository;
		this.orderRepository = orderRepository;
	}

	@Transactional
	public CustomerResponse createCustomer(CustomerRequest req) {
		CustomerEntity c = new CustomerEntity();
		c.setName(req.name());
		c.setEmail(req.email());
		return toResponse(customerRepository.save(c));
	}

	@Transactional
	public OrderResponse createOrder(Long customerId, OrderRequest req) {
		CustomerEntity customer = findCustomer(customerId);
		OrderEntity o = new OrderEntity();
		o.setOrderNumber(req.orderNumber());
		o.setOrderDate(req.orderDate());
		o.setTotalAmount(req.totalAmount());
		o.setStatus(req.status());
		o.setCustomer(customer);
		return toResponse(orderRepository.save(o));
	}

	@Transactional(readOnly = true)
	public List<CustomerResponse> getAllCustomers() {
		return customerRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public CustomerResponse getCustomerById(Long id) {
		return toResponse(findCustomer(id));
	}

	@Transactional(readOnly = true)
	public List<OrderResponse> getOrdersOfCustomer(Long customerId) {
		findCustomer(customerId);
		return orderRepository.findByCustomerId(customerId).stream().map(this::toResponse).toList();
	}

	private CustomerEntity findCustomer(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
	}

	private CustomerResponse toResponse(CustomerEntity c) {
		return new CustomerResponse(c.getId(), c.getName(), c.getEmail());
	}

	private OrderResponse toResponse(OrderEntity o) {
		return new OrderResponse(o.getId(), o.getOrderNumber(), o.getOrderDate(), o.getTotalAmount(), o.getStatus());
	}

	@Transactional(readOnly = true)
	public List<String> nPlusOneDemo() {
		List<CustomerEntity> customers = customerRepository.findAll(); // 1 query

		return customers.stream().map(c -> c.getName() + " -> " + c.getOrders().size()) // N queries
				.toList();
	}
	
	@Transactional(readOnly = true)
	public List<String> joinFetchDemo() {
	    return customerRepository.findAllWithOrders().stream()
	            .map(c -> c.getName() + " -> " + c.getOrders().size())
	            .toList();
	}

	@Transactional(readOnly = true)
	public List<String> entityGraphDemo() {
	    return customerRepository.findAllWithOrdersGraph().stream()
	            .map(c -> c.getName() + " -> " + c.getOrders().size())
	            .toList();
	}
}