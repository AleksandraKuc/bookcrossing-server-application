package project.bookcrossing.dto.message;

import io.swagger.annotations.ApiModelProperty;

public class MessageDataDTO {

	@ApiModelProperty(position = 0)
	private String content;
	@ApiModelProperty(position = 1)
	private String sender;
	@ApiModelProperty(position = 2)
	private long conversationId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String senderId) {
		this.sender = senderId;
	}

	public long getConversationId() {
		return conversationId;
	}

	public void setConversationId(long conversationId) {
		this.conversationId = conversationId;
	}

	@Override
	public String toString() {
		return "MessageDataDTO{" +
				"content='" + content + '\'' +
				", senderName='" + sender + '\'' +
				", conversationId=" + conversationId +
				'}';
	}
}
