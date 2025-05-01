package org.owpk.llm.provider.mcp;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class McpMessage {
	private String type;
	private String target;
	private Map<String, Object> payload;
	private Map<String, Object> metadata;
}
