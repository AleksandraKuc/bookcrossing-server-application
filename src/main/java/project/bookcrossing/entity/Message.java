package project.bookcrossing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "message_seq", allocationSize = 100)
public class Message {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private long id_message;

	@Column(nullable = false)
	private String content;

	private String date;

	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL
	)
	@JoinColumn(
			name = "convesation", referencedColumnName = "id_conversation",
			nullable = false
	)
	@JsonIgnore
	private Conversation conversation;

	public Message(){}

	public Message(String content, Conversation conversation) {
		this.content = content;
		this.conversation = conversation;
	}

	public Message(String content, String date, Conversation conversation) {
		this.content = content;
		this.date = date;
		this.conversation = conversation;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
}
