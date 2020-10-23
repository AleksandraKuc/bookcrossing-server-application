package project.bookcrossing.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "message_seq", allocationSize = 100)
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long idMessage;

	@Column(nullable = false)
	private String content;

	private Date date;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender", referencedColumnName = "id_user")
	private User sender;

	@ManyToOne
	@JoinColumn(
			name = "conversation",
			nullable = false
	)
	private Conversation conversation;

	public Message() {
	}

	public Message(String content, User user, Conversation conversation) {
		this.content = content;
		this.sender = user;
		this.conversation = conversation;
	}

	public Message(String content, Date date, User user, Conversation conversation) {
		this.content = content;
		this.date = date;
		this.sender = user;
		this.conversation = conversation;
	}

	public long getId_message() { return idMessage; }

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

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}
}
