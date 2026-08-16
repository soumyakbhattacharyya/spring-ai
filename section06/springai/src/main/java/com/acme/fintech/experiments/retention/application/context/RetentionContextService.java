package com.acme.fintech.experiments.retention.application.context;

import org.springframework.stereotype.Service;

import com.acme.fintech.experiments.retention.application.RetentionOpportunityService;

/**
 * Service class responsible for establishing the retention context for a
 * customer.
 */
@Service
public class RetentionContextService {

	private final CustomerContextService customerService;

	private final RetentionOpportunityService opportunityService;

	public RetentionContextService(CustomerContextService customerService,
			RetentionOpportunityService opportunityService) {

		this.customerService = customerService;
		this.opportunityService = opportunityService;
	}

	/**
	 * Builds the retention context for a given customer identified by their
	 * UCIC.
	 *
	 * @param ucic the unique customer identifier
	 * @return a RetentionContext object containing customer details, products, and
	 *         retention opportunity
	 */
	public RetentionContext getContext(String ucic) {

		return new RetentionContext(

				customerService.getCustomer(ucic),

				customerService.getProducts(ucic),

				opportunityService.getOpportunity(ucic));
	}
}
