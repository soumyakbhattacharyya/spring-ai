package com.acme.fintech.experiments.retention.domain.retention;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * RetentionOpportunity record representing a retention opportunity for a customer.
 */
public record RetentionOpportunity(String opportunityId,

		String ucic,

		String type,

		String reason,

		BigDecimal retentionProbability,

		String approvedAction,

		Instant expiresAt) {

}
