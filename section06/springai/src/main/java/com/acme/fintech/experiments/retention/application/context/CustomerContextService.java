package com.acme.fintech.experiments.retention.application.context;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.acme.fintech.experiments.retention.domain.customer.Customer;
import com.acme.fintech.experiments.retention.domain.customer.CustomerProduct;

@Service
public class CustomerContextService {

	/**
	 * Retrieves customer details based on the provided UCIC.
	 *
	 * @param ucic the unique customer identifier
	 * @return a Customer object containing customer details
	 */
	public Customer getCustomer(String ucic) {

		return new Customer(ucic, "Soumyak", "98XXXXXX21", BigDecimal.valueOf(12000));
	}

	/**
	 * Retrieves a list of products associated with the customer identified by the provided UCIC.
	 *
	 * @param ucic the unique customer identifier
	 * @return a list of CustomerProduct objects representing the customer's products
	 */
	public List<CustomerProduct> getProducts(String ucic) {

		return List.of(new CustomerProduct("GL12345", "GOLD_LOAN", BigDecimal.valueOf(320000),
				LocalDate.of(2026, 9, 15), "ACTIVE"));
	}
}