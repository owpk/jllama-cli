package org.owpk.service.dialog;

import java.util.Map;

import org.owpk.llm.provider.model.MessageType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
@ReflectiveAccess
public class Message {
	@JsonProperty("id")
	private String id;
	private MessageType type;
	private String content;
	private String timestamp;
	@JsonProperty("metadata")
	private Map<String, Object> metadata;
}
