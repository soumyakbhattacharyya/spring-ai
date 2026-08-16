package com.acme.fintech.experiments.retention.domain.customer;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * CustomerProduct record representing a product associated with a customer.
 */
public record CustomerProduct(String productId,

		String productType,

		BigDecimal outstandingAmount,

		LocalDate maturityDate,

		String status) {

}
