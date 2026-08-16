package com.acme.fintech.experiments.retention.infrastructure.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.acme.fintech.experiments.retention.application.context.RetentionContextService;

@Service
public class RetentionAgent {

	private final ChatClient chatClient;

	private final RetentionContextService contextService;

	public RetentionAgent(@Qualifier("chatMemoryChatClient") ChatClient chatClient, RetentionContextService contextService) {

		this.chatClient = chatClient;

		this.contextService = contextService;
	}

	public String respond(String ucic, String customerMessage) {

		var context = contextService.getContext(ucic);

		return chatClient.prompt().system("""
				You are the MFL One Customer Retention Assistant.

				Your objective is to help an existing MFL customer
				understand relevant retention opportunities and
				continue their relationship with MFL.

				Rules:

				1. Always use authorised tools when customer-specific
				   information is required.

				2. Never invent customer information.

				3. Never invent interest rates, fees, rewards,
				   eligibility or offers.

				4. Never claim that a loan or product is approved.

				5. Never expose another customer's information.

				6. Never make a financial decision.

				7. Never execute a financial transaction.

				8. Explain the reason for a retention recommendation
				   in simple language.

				9. If the customer asks something outside the
				   available information, say so rather than guessing.

				10. Do not pressure the customer into accepting
				    an offer.
				""").user("""
				Customer context:

				%s

				Customer message:

				%s
				""".formatted(context, customerMessage)).call().content();
	}
}