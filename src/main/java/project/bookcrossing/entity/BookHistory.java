package project.bookcrossing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = "history_seq", allocationSize = 100)
public class BookHistory implements Serializable {

//	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private long id_history;

	@OneToOne(
			fetch = FetchType.LAZY,
			optional = false
	)
	@JoinColumn(
			name = "current_user", referencedColumnName = "id_user",
			nullable = false
	)
	@JsonIgnore
	private User current_user;

	@OneToOne(
			fetch = FetchType.LAZY,
			optional = false
	)
	@JoinColumn(
			name = "first_user", referencedColumnName = "id_user",
			nullable = false
	)
	@JsonIgnore
	private User first_user;

	@OneToOne(
			fetch = FetchType.LAZY,
			optional = false,
			cascade = CascadeType.ALL
	)
	@JoinColumn(
			name = "book", referencedColumnName = "id_book",
			nullable = false
	)
	@JsonIgnore
	private Book book;

	private String start_date;
	private String date;

	public BookHistory(){}

	public BookHistory(User current_user, User first_user, Book book, String start_date, String date) {
		this.current_user = current_user;
		this.first_user = first_user;
		this.book = book;
		this.start_date = start_date;
		this.date = date;
	}

	public BookHistory(User current_user, User first_user, Book book){
		this.current_user = current_user;
		this.first_user = first_user;
		this.book = book;
	}

	public BookHistory(User current_user) {
		this.current_user = current_user;
	}

	public User getCurrent_user() {
		return current_user;
	}

	public void setCurrent_user(User current_user){
		this.current_user = current_user;
	}

	public User getFirst_user() {
		return first_user;
	}

	public Book getBook() {
		return book;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "BookHistory{" +
				"id_history=" + id_history +
				", current_user=" + current_user +
				", first_user=" + first_user +
				", book=" + book +
				", start_date='" + start_date + '\'' +
				", date='" + date + '\'' +
				'}';
	}
}
