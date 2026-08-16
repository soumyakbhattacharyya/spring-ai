package com.acme.fintech.experiments.retention.domain.customer;

import java.math.BigDecimal;

/**
 * Customer record representing customer details.
 */
public record Customer(String ucic,

		String name,

		String mobile,

		BigDecimal relationshipValue) {

}
