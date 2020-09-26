package project.bookcrossing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "conversation_seq", allocationSize = 100)
public class Conversation {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private long id_conversation;

	@ManyToOne(
			fetch = FetchType.LAZY,
			optional = false,
			cascade = CascadeType.MERGE
	)
	@JoinColumn(
			name = "first_user", referencedColumnName = "id_user",
			insertable = false,
			updatable = false,
			nullable = false
	)
	@JsonIgnore
	private User firstUser;

	@ManyToOne(
			fetch = FetchType.LAZY,
			optional = false,
			cascade = CascadeType.MERGE
	)
	@JoinColumn(
			name = "second_user", referencedColumnName = "id_user",
			insertable = false,
			updatable = false,
			nullable = false
	)
	@JsonIgnore
	private User secondUser;

	public Conversation(){}

	public Conversation(User firstUser, User secondUser) {
		this.firstUser = firstUser;
		this.secondUser = secondUser;
	}

	public User getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(User firstUser) { this.firstUser = firstUser; }

	public User getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(User secondUser) { this.secondUser = secondUser; }
}
