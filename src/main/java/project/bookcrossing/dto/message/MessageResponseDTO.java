package project.bookcrossing.dto.message;

import io.swagger.annotations.ApiModelProperty;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.User;

import java.util.Date;

public class MessageResponseDTO {

	@ApiModelProperty(position = 0)
	private long idMessage;
	@ApiModelProperty(position = 1)
	private String content;
	@ApiModelProperty(position = 2)
	private Date date;
	@ApiModelProperty(position = 3)
	private String sender;
	@ApiModelProperty(position = 4)
	private long conversationId;

	public MessageResponseDTO(long idMessage, String content, Date date, String sender, long conversationId) {
		this.idMessage = idMessage;
		this.content = content;
		this.date = date;
		this.sender = sender;
		this.conversationId = conversationId;
	}

	public long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(long idMessage) {
		this.idMessage = idMessage;
	}

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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public long getConversation() {
		return conversationId;
	}

	public void setConversation(long conversation) {
		this.conversationId = conversation;
	}
}
