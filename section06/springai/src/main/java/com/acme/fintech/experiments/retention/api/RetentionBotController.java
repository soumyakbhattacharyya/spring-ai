package com.acme.fintech.experiments.retention.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.fintech.experiments.retention.infrastructure.ai.RetentionAgent;

@RestController
@RequestMapping("/api/v1/retention")
public class RetentionBotController {

	private final RetentionAgent retentionAgent;

	public RetentionBotController(RetentionAgent retentionAgent) {

		this.retentionAgent = retentionAgent;
	}

	@PostMapping("/customers/{ucic}/chat")
	public ChatResponse chat(@PathVariable String ucic, @RequestBody ChatRequest request) {

		String response = retentionAgent.respond(ucic, request.message());

		return new ChatResponse(response);
	}

	public record ChatRequest(String message) {
	}

	public record ChatResponse(String message) {
	}
}