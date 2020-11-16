package project.bookcrossing.dto.user;

import io.swagger.annotations.ApiModelProperty;

public class ResetPasswordDataDTO {

	@ApiModelProperty(position = 0)
	private String username;
	@ApiModelProperty(position = 1)
	private String currentPassword;
	@ApiModelProperty(position = 2)
	private String newPassword;

	public ResetPasswordDataDTO(String username, String currentPassword, String newPassword) {
		this.username = username;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
