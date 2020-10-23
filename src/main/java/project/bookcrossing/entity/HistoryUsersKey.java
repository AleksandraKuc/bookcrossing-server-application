package project.bookcrossing.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class HistoryUsersKey implements Serializable {

	@Column(name = "id_history")
	Long id_history;

	@Column(name = "id_user")
	Long id_user;

	public HistoryUsersKey(){}

	public HistoryUsersKey(Long id_history) {
		this.id_history = id_history;
	}

	public HistoryUsersKey(Long id_history, Long id_user) {
		this.id_history = id_history;
		this.id_user = id_user;
	}

	public Long getId_history() {
		return id_history;
	}

	public void setId_history(Long id_history) {
		this.id_history = id_history;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	@Override
	public String toString() {
		return "HistoryUsersKey{" +
				"id_history=" + id_history +
				", id_user=" + id_user +
				'}';
	}
}
