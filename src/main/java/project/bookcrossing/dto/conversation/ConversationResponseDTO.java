package project.bookcrossing.dto.conversation;

import io.swagger.annotations.ApiModelProperty;
import project.bookcrossing.entity.User;

import java.util.List;

public class ConversationResponseDTO {

	@ApiModelProperty(position = 0)
	private long id_conversation;
	@ApiModelProperty(position = 1)
	private List<User> conversationUsers;

	public long getId_conversation() {
		return id_conversation;
	}

	public void setId_conversation(long id_conversation) {
		this.id_conversation = id_conversation;
	}

	public List<User> getConversationUsers() {
		return conversationUsers;
	}

	public void setConversationUsers(List<User> conversationUsers) {
		this.conversationUsers = conversationUsers;
	}
}
