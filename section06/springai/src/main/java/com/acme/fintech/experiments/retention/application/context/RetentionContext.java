package com.acme.fintech.experiments.retention.application.context;

import java.util.List;

import com.acme.fintech.experiments.retention.domain.customer.Customer;
import com.acme.fintech.experiments.retention.domain.customer.CustomerProduct;
import com.acme.fintech.experiments.retention.domain.retention.RetentionOpportunity;

/**
 * RetentionContext record representing the context of a retention opportunity for a customer.
 */
public record RetentionContext(

		Customer customer,

		List<CustomerProduct> products,

		RetentionOpportunity opportunity

) {
}