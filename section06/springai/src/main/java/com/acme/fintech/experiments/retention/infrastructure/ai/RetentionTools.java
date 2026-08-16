package com.acme.fintech.experiments.retention.infrastructure.ai;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.acme.fintech.experiments.retention.application.RetentionOpportunityService;
import com.acme.fintech.experiments.retention.application.context.CustomerContextService;
import com.acme.fintech.experiments.retention.domain.customer.Customer;
import com.acme.fintech.experiments.retention.domain.customer.CustomerProduct;
import com.acme.fintech.experiments.retention.domain.retention.RetentionOpportunity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RetentionTools {

    private final CustomerContextService customerContextService;

    private final RetentionOpportunityService
        retentionOpportunityService;

    @Tool(
        description = """
        Retrieves the customer's MFL profile.
        """
    )
    public Customer getCustomerProfile() {

        return customerContextService
            .getCustomer(getAuthenticatedUcic());
    }

    @Tool(
        description = """
        Retrieves the customer's active products.
        """
    )
    public List<CustomerProduct> getActiveProducts() {

        return customerContextService
            .getProducts(getAuthenticatedUcic());
    }

    @Tool(
        description = """
        Retrieves the currently approved retention
        opportunity for the customer.
        """
    )
    public RetentionOpportunity
    getRetentionOpportunity() {

        return retentionOpportunityService
            .getOpportunity(getAuthenticatedUcic());
    }
    
    private String getAuthenticatedUcic() {

		// In a real application, this would retrieve the UCIC of the
		// authenticated user from the security context or session.
		// For this example, we will return a hardcoded UCIC.
		return "UCIC-1234567890";
	}
}