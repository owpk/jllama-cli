package org.owpk.llm.provider.dialog;

import java.time.LocalDateTime;
import java.util.Map;

import org.owpk.service.model.MessageType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
	private String id;
	private String content;
	private MessageType type;
	private String dialogId;
	private LocalDateTime timestamp;
	private Map<String, Object> metadata;
}
