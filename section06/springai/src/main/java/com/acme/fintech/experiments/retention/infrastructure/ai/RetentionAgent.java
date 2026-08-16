package com.acme.fintech.experiments.retention.infrastructure.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.acme.fintech.experiments.retention.application.context.RetentionContextService;

@Service
public class RetentionAgent {

	private final ChatClient chatClient;

	private final RetentionContextService contextService;

	public RetentionAgent(ChatClient.Builder chatClientBuilder, RetentionContextService contextService) {

		this.chatClient = chatClientBuilder.build();

		this.contextService = contextService;
	}

	public String respond(String ucic, String customerMessage) {

		var context = contextService.getContext(ucic);

		return chatClient.prompt().system("""
				You are the MFL One Customer
				Retention Assistant.

				Your objective is to help retain
				the customer's relationship with MFL.

				You must:

				- Be transparent.
				- Explain approved offers clearly.
				- Never invent rates or benefits.
				- Never guarantee approval.
				- Never fabricate customer information.
				- Never make financial decisions.
				- Never execute transactions.

				Use the customer context provided
				by the application as the source
				of customer-specific information.
				""").user("""
				Customer context:

				%s

				Customer message:

				%s
				""".formatted(context, customerMessage)).call().content();
	}
}