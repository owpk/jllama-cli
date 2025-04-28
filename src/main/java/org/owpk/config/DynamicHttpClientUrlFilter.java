package org.owpk.config;

import java.net.URI;

import org.owpk.llm.ollama.client.OllamaProps;
import org.reactivestreams.Publisher;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;

public class DynamicHttpClientUrlFilter implements HttpClientFilter {

	private final OllamaProps ollamaProps;

	public DynamicHttpClientUrlFilter(OllamaProps props) {
		this.ollamaProps = props;
	}

	@Override
	public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
		var originalUri = request.getUri();
		var newUri = ollamaProps.getApiUrl() + originalUri.getPath();
		request.uri(URI.create(newUri));
		return chain.proceed(request);
	}

}
