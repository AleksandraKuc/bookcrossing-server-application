package project.bookcrossing.dto.user;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserListResponseDTO {

	@ApiModelProperty(position = 0)
	private long amountAll;
	@ApiModelProperty(position = 1)
	private List<UserResponseDTO> users;

	public long getAmountAll() {
		return amountAll;
	}

	public void setAmountAll(long amountAll) {
		this.amountAll = amountAll;
	}

	public List<UserResponseDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserResponseDTO> users) {
		this.users = users;
	}
}
