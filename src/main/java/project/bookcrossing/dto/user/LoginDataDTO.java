package project.bookcrossing.dto.user;

import io.swagger.annotations.ApiModelProperty;

public class LoginDataDTO {

	@ApiModelProperty(position = 0)
	String username;
	@ApiModelProperty(position = 1)
	String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDataDTO{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
