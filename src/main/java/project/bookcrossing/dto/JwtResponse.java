package project.bookcrossing.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.List;

public class JwtResponse {

	@ApiModelProperty(position = 0)
	private String accessToken;
	@ApiModelProperty(position = 1)
	private String username;
	@ApiModelProperty(position = 2)
	private List<String> authorities;

	public JwtResponse() {
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "JwtResponse{" +
				"accessToken='" + accessToken + '\'' +
				", username='" + username + '\'' +
				", authorities=" + authorities +
				'}';
	}
}
