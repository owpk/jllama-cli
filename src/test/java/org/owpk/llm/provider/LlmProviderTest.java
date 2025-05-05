package org.owpk.llm.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.provider.mcp.McpMessage;
import org.owpk.llm.provider.model.ChatRequest;
import org.owpk.llm.provider.model.MessageType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class LlmProviderTest {
	private static final double[] TEST_EMBEDDING = { 1.0, 2.0 };

	@Mock
	private TestApiClient mockApiClient;

	@Mock
	private LlmProviderProperties mockProperties;

	private TestLlmProvider provider;

	// Тестовый API клиент
	private static class TestApiClient {
	}

	// Тестовая реализация LlmProvider
	private static class TestLlmProvider extends LlmProvider<TestApiClient> {
		private boolean onClientUpdateCalled = false;

		public TestLlmProvider(TestApiClient client, LlmProviderProperties props) {
			super(client, props);
		}

		@Override
		protected void onClientUpdate(TestApiClient newClient, LlmProviderProperties newProperties) {
			this.onClientUpdateCalled = true;
		}

		@Override
		public Flux<String> generate(String prompt, String rolePrompt) {
			return Flux.just("test response");
		}

		@Override
		public Flux<String> chat(List<ChatRequest> messages) {
			return Flux.just("test chat response");
		}

		@Override
		public Mono<double[]> embed(String text) {
			return Mono.just(TEST_EMBEDDING);
		}

		public boolean wasOnClientUpdateCalled() {
			return onClientUpdateCalled;
		}
	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		provider = new TestLlmProvider(mockApiClient, mockProperties);
	}

	@Test
	void testConstructorAndGetters() {
		assertNotNull(provider.getApiClient());
		assertNotNull(provider.getProperties());
		assertEquals(mockApiClient, provider.getApiClient());
		assertEquals(mockProperties, provider.getProperties());
	}

	@Test
	void testUpdateClient() {
		TestApiClient newClient = mock(TestApiClient.class);
		LlmProviderProperties newProps = mock(LlmProviderProperties.class);

		provider.updateClient(newClient, newProps);

		assertTrue(provider.wasOnClientUpdateCalled());
	}

	@Test
	void testGenerate() {
		StepVerifier.create(provider.generate("test prompt", "test role"))
				.expectNext("test response")
				.verifyComplete();
	}

	@Test
	void testChat() {
		List<ChatRequest> messages = Arrays.asList(
				ChatRequest.builder()
						.message("Hello")
						.role(MessageType.USER)
						.build());

		StepVerifier.create(provider.chat(messages))
				.expectNext("test chat response")
				.verifyComplete();
	}

	@Test
	void testEmbed() {
		StepVerifier.create(provider.embed("test text"))
				.expectNext(TEST_EMBEDDING)
				.verifyComplete();
	}

	@Test
	void testHandleMcpMessage_throwsException() {
		McpMessage mockMessage = mock(McpMessage.class);

		assertThrows(UnsupportedOperationException.class, () -> {
			provider.handleMcpMessage(mockMessage).block();
		});
	}

	@Test
	void testGetMcpMetadata_throwsException() {
		assertThrows(UnsupportedOperationException.class, () -> {
			provider.getMcpMetadata();
		});
	}
}