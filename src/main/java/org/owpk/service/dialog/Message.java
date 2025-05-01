package org.owpk.service.dialog;

import java.time.LocalDateTime;
import java.util.Map;

import org.owpk.llm.provider.model.MessageType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
	private String id;
	private String content;
	private MessageType type;
	private LocalDateTime timestamp;
	private Map<String, Object> metadata;
}
