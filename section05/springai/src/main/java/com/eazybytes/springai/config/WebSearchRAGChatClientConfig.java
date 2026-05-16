package com.eazybytes.springai.config;

import com.eazybytes.springai.advisors.TokenUsageAuditAdvisor;
import com.eazybytes.springai.rag.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * This configuration class sets up a ChatClient that incorporates
 * Retrieval-Augmented Generation (RAG) using web search. It defines a bean
 * named "webSearchRAGChatClient" that configures the ChatClient with several
 * advisors, including logging, token usage auditing, chat memory, and retrieval
 * augmentation using web search.
 * 
 * see the usage of @WebSearchDocumentRetriever in the
 * RetrievalAugmentationAdvisor configuration, which allows the chat client to
 * retrieve relevant information from the web based on user queries. This setup
 * enables the chat client to provide more informed and contextually relevant
 * responses by leveraging external web search results.
 */
@Configuration
public class WebSearchRAGChatClientConfig {

	@Bean("webSearchRAGChatClient")
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
			RestClient.Builder restClientBuilder) {
		Advisor loggerAdvisor = new SimpleLoggerAdvisor();
		Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
		Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
		var webSearchRAGAdvisor = RetrievalAugmentationAdvisor.builder()
				.documentRetriever(
						WebSearchDocumentRetriever.builder().restClientBuilder(restClientBuilder).maxResults(5).build())
				.build();
		return chatClientBuilder
				.defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenUsageAdvisor, webSearchRAGAdvisor)).build();
	}
}
