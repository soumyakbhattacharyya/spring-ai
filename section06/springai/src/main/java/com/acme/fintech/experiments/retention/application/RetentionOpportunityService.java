package com.acme.fintech.experiments.retention.application;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.acme.fintech.experiments.retention.domain.retention.RetentionOpportunity;

@Service
public class RetentionOpportunityService {
	/**
	 * Returns a retention opportunity for the given UCIC.
	 *
	 * @param ucic the UCIC of the customer
	 * @return a RetentionOpportunity object
	 */
	public RetentionOpportunity getOpportunity(String ucic) {

		return new RetentionOpportunity(

				"RET-10001",

				ucic,

				"LOAN_RENEWAL",

				"Gold loan maturity approaching",

				BigDecimal.valueOf(0.82),

				"Offer renewal with 2,000 Blue Rewards points",

				Instant.now().plusSeconds(86400 * 15));
	}
}
