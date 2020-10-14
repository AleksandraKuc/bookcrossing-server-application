package project.bookcrossing.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "history_seq", allocationSize = 100)
public class BookHistory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_history;

	private Date start_date;
	private Date last_hire;

	public BookHistory() {
	}

	public BookHistory(Date start_date, Date last_hire) {
		this.start_date = start_date;
		this.last_hire = last_hire;
	}

	public long getId_history() {
		return id_history;
	}

	public Date getStart_date() {
		return start_date;
	}

	public Date getLast_hire() {
		return last_hire;
	}

	public void setLast_hire() {
		this.last_hire = new Date();
	}

	@Override
	public String toString() {
		return "BookHistory{" +
				"id_history=" + id_history +
				", start_date=" + start_date +
				", last_hire=" + last_hire +
				'}';
	}
}
