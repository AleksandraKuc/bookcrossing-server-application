package project.bookcrossing.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HistoryUsers {

	@EmbeddedId
	private HistoryUsersKey id_historyUsers;

	private String userType;

	public HistoryUsers(){}

	public HistoryUsers(String userType){
		this.userType = userType;
	}

	public HistoryUsersKey getId_historyUsers() {
		return id_historyUsers;
	}

	public void setId_historyUsers(HistoryUsersKey id_historyUsers) {
		this.id_historyUsers = id_historyUsers;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "HistoryUsers{" +
				"id_historyUsers=" + id_historyUsers +
				", updateDate=" + userType +
				'}';
	}
}
