package project.bookcrossing.dto.conversation;

import io.swagger.annotations.ApiModelProperty;

import project.bookcrossing.entity.User;

public class ConversationResponseDTO {

	@ApiModelProperty(position = 0)
	private long id_conversation;
	@ApiModelProperty(position = 1)
	private final UserConv recipient = new UserConv();

	public ConversationResponseDTO(long id_conversation, User recipient) {
		this.id_conversation = id_conversation;
		this.setRecipient(recipient);
	}

	public long getId_conversation() {
		return id_conversation;
	}

	public void setId_conversation(long id_conversation) {
		this.id_conversation = id_conversation;
	}

	public UserConv getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient.setUsername(recipient.getUsername());
		this.recipient.setFirstname(recipient.getFirstName());
	}

	private static class UserConv {
		private String username;
		private String firstname;

		public void setUsername(String username) {
			this.username = username;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getUsername() {
			return username;
		}

		public String getFirstname() {
			return firstname;
		}
	}
}
