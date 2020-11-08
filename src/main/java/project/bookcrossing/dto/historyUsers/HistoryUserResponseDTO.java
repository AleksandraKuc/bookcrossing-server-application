package project.bookcrossing.dto.historyUsers;

import io.swagger.annotations.ApiModelProperty;
import project.bookcrossing.entity.HistoryUsersKey;

public class HistoryUserResponseDTO {

	@ApiModelProperty(position = 0)
	private HistoryUsersKey id_historyUsers;
	@ApiModelProperty(position = 1)
	private String userType;

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
}
