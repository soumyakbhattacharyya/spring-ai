package com.eazybytes.springai.controller;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rag")
public class RAGController {

	private final ChatClient chatClient;
	private final ChatClient webSearchchatClient;
	private final VectorStore vectorStore;

	@Value("classpath:/promptTemplates/systemPromptRandomDataTemplate.st")
	Resource promptTemplate;

	@Value("classpath:/promptTemplates/systemPromptTemplate.st")
	Resource hrSystemTemplate;

	public RAGController(@Qualifier("chatMemoryChatClient") ChatClient chatClient,
			@Qualifier("webSearchRAGChatClient") ChatClient webSearchchatClient, VectorStore vectorStore) {
		this.chatClient = chatClient;
		this.webSearchchatClient = webSearchchatClient;
		this.vectorStore = vectorStore;
	}

	@GetMapping("/random/chat")
	public ResponseEntity<String> randomChat(@RequestHeader("username") String username,
			@RequestParam("message") String message) {
		SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build();
		List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
		String similarContext = similarDocs.stream().map(Document::getText)
				.collect(Collectors.joining(System.lineSeparator()));
		String answer = chatClient.prompt()
				.system(promptSystemSpec -> promptSystemSpec.text(hrSystemTemplate).param("documents", similarContext))
				.advisors(a -> a.param(CONVERSATION_ID, username)).user(message).call().content();
		return ResponseEntity.ok(answer);
	}

	/**
	 * 
	 * This endpoint demonstrates a simple RAG implementation where the user's
	 * message is used to retrieve similar documents from a vector store. The
	 * retrieved documents are then provided as context to the chat client, which
	 * generates a response based on both the user's message and the relevant
	 * information from the documents. This allows for more informed and accurate
	 * responses, as the chat client can leverage the additional context provided by
	 * the retrieved documents.
	 * 
	 * Note: This method uses the @RetrievalAugemntationAdvisor configured in the
	 * chat client, which means that the retrieval and augmentation process is
	 * handled automatically by the advisor. The search request and document
	 * retrieval logic are included here for demonstration purposes, but in a real
	 * implementation, you would typically rely on the advisor to manage this
	 * process seamlessly.
	 * 
	 * @param username
	 * @param message
	 * @return
	 */
	@GetMapping("/document/chat")
	public ResponseEntity<String> documentChat(@RequestHeader("username") String username,
			@RequestParam("message") String message) {
		/*
		 * SearchRequest searchRequest =
		 * SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build
		 * (); List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
		 * String similarContext = similarDocs.stream() .map(Document::getText)
		 * .collect(Collectors.joining(System.lineSeparator()));
		 */
		String answer = chatClient.prompt()
				/*
				 * .system(promptSystemSpec -> promptSystemSpec.text(hrSystemTemplate)
				 * .param("documents", similarContext))
				 */
				.advisors(a -> a.param(CONVERSATION_ID, username)).user(message).call().content();
		return ResponseEntity.ok(answer);
	}

	@GetMapping("/web-search/chat")
	public ResponseEntity<String> webSearchChat(@RequestHeader("username") String username,
			@RequestParam("message") String message) {
		String answer = webSearchchatClient.prompt().advisors(a -> a.param(CONVERSATION_ID, username)).user(message)
				.call().content();
		return ResponseEntity.ok(answer);
	}
}
