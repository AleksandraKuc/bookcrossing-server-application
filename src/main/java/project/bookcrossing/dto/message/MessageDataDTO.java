package project.bookcrossing.dto.message;

import io.swagger.annotations.ApiModelProperty;

public class MessageDataDTO {

	@ApiModelProperty(position = 0)
	private String content;
	@ApiModelProperty(position = 1)
	private long senderId;
	@ApiModelProperty(position = 2)
	private long conversationId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getConversationId() {
		return conversationId;
	}

	public void setConversationId(long conversationId) {
		this.conversationId = conversationId;
	}
}
