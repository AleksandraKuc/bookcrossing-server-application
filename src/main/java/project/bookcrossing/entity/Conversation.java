package project.bookcrossing.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "conversation_seq", allocationSize = 100)
public class Conversation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_conversation;

	@ManyToMany
	@JoinTable(
			name = "user_conversation",
			joinColumns = @JoinColumn(name = "id_conversation"),
			inverseJoinColumns = @JoinColumn(name = "id_user"))
	private List<User> conversation_users;

	@OneToMany(mappedBy = "conversation")
	private List<Message> messagesList;

	public Conversation() {
	}

	public Conversation(User firstUser, User secondUser, List<Message> messageList) {
		this.conversation_users.add(0, firstUser);
		this.conversation_users.add(1, secondUser);
		this.messagesList = messageList;
	}

	public User getFirstUser() {
		return conversation_users.get(0);
	}

	public void setFirstUser(User firstUser) {
		this.conversation_users.set(0, firstUser);
	}

	public User getSecondUser() {
		return conversation_users.get(1);
	}

	public void setSecondUser(User secondUser) {
		this.conversation_users.set(1, secondUser);
	}

	public List<Message> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<Message> messagesList) {
		this.messagesList = messagesList;
	}
}
