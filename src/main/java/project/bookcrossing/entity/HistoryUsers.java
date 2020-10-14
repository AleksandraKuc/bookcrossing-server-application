package project.bookcrossing.entity;

import javax.persistence.*;

@Entity
public class HistoryUsers {

	@EmbeddedId
	private HistoryUsersKey id_historyUsers;

	public HistoryUsers(){}

	public HistoryUsersKey getId_historyUsers() {
		return id_historyUsers;
	}

	public void setId_historyUsers(HistoryUsersKey id_historyUsers) {
		this.id_historyUsers = id_historyUsers;
	}

}
