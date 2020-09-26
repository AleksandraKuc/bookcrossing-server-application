package project.bookcrossing.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "history_seq", allocationSize = 100)
public class BookHistory implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private long id_history;

	@ManyToMany
	@JoinTable(
			name = "user_book",
			joinColumns = @JoinColumn(name = "id_history"),
			inverseJoinColumns = @JoinColumn(name = "id_user"))
	List<User> users;

	@OneToOne(mappedBy = "history")
	private Book book;

	private Date start_date;
	private Date last_hire;

	public BookHistory(){}

	public BookHistory(User current_user, User first_user, Book book, Date start_date, Date last_hire) {
		this.users.add(0, current_user);
		this.users.add(1, first_user);
		this.book = book;
		this.start_date = start_date;
		this.last_hire = last_hire;
	}

	public BookHistory(User current_user, User first_user, Book book){
		this.users.add(0, current_user);
		this.users.add(1, first_user);
		this.book = book;
	}

	public BookHistory(User current_user, Book book){
		this.users.add(0, current_user);
		this.users.add(1, current_user);
		this.book = book;
	}

	public BookHistory(User current_user) {
		this.users.add(0, current_user);
	}

	public User getCurrent_user() {
		return this.users.get(0);
	}

	public void setCurrent_user(User current_user){
		this.users.set(0, current_user);
	}

	public User getFirst_user() {
		return this.users.get(1);
	}

	public Book getBook() {
		return book;
	}

	public Date getStart_date() {
		return start_date;
	}

	public Date getLast_hire() {
		return last_hire;
	}

	@Override
	public String toString() {
		return "BookHistory{" +
				"id_history=" + id_history +
				", current_user=" + users.get(0) +
				", first_user=" + users.get(1) +
				", book=" + book +
				", start_date='" + start_date + '\'' +
				", date='" + last_hire + '\'' +
				'}';
	}
}
