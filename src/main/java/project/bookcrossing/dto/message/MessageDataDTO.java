package project.bookcrossing.dto.message;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class MessageDataDTO {

	@ApiModelProperty(position = 0)
	private String content;
	@ApiModelProperty(position = 1)
	private Date date;
	@ApiModelProperty(position = 2)
	private long senderId;
	@ApiModelProperty(position = 3)
	private long conversationId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
