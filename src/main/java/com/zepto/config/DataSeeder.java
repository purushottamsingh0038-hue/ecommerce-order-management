package com.zepto.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zepto.entity.CustomerEntity;
import com.zepto.entity.OrderEntity;
import com.zepto.repository.CustomerRepository;

@Component
public class DataSeeder implements CommandLineRunner {

	private final CustomerRepository customerRepository;

	public DataSeeder(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) {
		if (customerRepository.count() > 0) {
			return;
		}

		String[][] customerData = { { "Purushottam Singh", "purushottam.singh@gmail.com" },
				{ "Priya Verma", "priya.verma@gmail.com" }, { "Amit Kumar", "amit.kumar@yahoo.com" },
				{ "Sneha Dubey", "sneha.dubey@outlook.com" }, { "Vikram Singh", "vikram.singh@gmail.com" } };

		String[] products = { "Wireless Mouse", "Bluetooth Headphones", "Running Shoes", "Cotton T-Shirt",
				"Smart Watch", "Backpack", "Mixer Grinder" };
		String[] prices = { "799.00", "2499.00", "3299.00", "599.00", "7999.00", "1499.00", "3499.00" };
		String[] statuses = { "PLACED", "SHIPPED", "DELIVERED", "CANCELLED" };

		List<CustomerEntity> customers = new ArrayList<>();
		int orderSeq = 1001;

		for (int i = 0; i < customerData.length; i++) {
			CustomerEntity c = new CustomerEntity();
			c.setName(customerData[i][0]);
			c.setEmail(customerData[i][1]);

			int orderCount = 2 + (i % 3); // 2, 3, 4, 2, 3 orders
			for (int j = 0; j < orderCount; j++) {
				int p = (i + j) % products.length;

				OrderEntity o = new OrderEntity();
				o.setOrderNumber("ORD-" + (orderSeq++));
				o.setOrderDate(LocalDate.now().minusDays(i * 3L + j));
				o.setProductName(products[p]);
				o.setTotalAmount(new BigDecimal(prices[p]));
				o.setStatus(statuses[(i + j) % statuses.length]);
				c.addOrder(o);
			}
			customers.add(c);
		}
		customerRepository.saveAll(customers);
	}
}