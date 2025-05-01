package org.owpk.config.properties.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LlmProviderProperties {
	private String provider;
	private String url;
	private String model;
	private String apiKey;

	public LlmProviderProperties(String provider, String url) {
		this.provider = provider;
		this.url = url;
	}
}
