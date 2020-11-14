package project.bookcrossing.dto.conversation;

import io.swagger.annotations.ApiModelProperty;

import project.bookcrossing.entity.User;

public class ConversationResponseDTO {

	@ApiModelProperty(position = 0)
	private long id_conversation;
	@ApiModelProperty(position = 1)
	private final UserConv recipient = new UserConv();

	public ConversationResponseDTO(long id_conversation, User firstUser) {
		this.id_conversation = id_conversation;
		this.setRecipient(firstUser);
	}

	public long getId_conversation() {
		return id_conversation;
	}

	public void setId_conversation(long id_conversation) {
		this.id_conversation = id_conversation;
	}

	public UserConv getFirstUser() {
		return recipient;
	}

	public void setRecipient(User firstUser) {
		this.recipient.setUsername(firstUser.getUsername());
		this.recipient.setFirstname(firstUser.getFirstName());

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
