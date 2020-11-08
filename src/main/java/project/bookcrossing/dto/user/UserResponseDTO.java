package project.bookcrossing.dto.user;

import java.sql.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import project.bookcrossing.entity.Role;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

public class UserResponseDTO {

	@ApiModelProperty(position = 0)
	private Integer id;
	@ApiModelProperty(position = 1)
	private String username;
	@ApiModelProperty(position = 2)
	private String email;
	@ApiModelProperty(position = 3)
	List<Role> roles;
	@ApiModelProperty(position = 4)
	private String firstName;
	@ApiModelProperty(position = 5)
	private String lastName;
	@ApiModelProperty(position = 6)
	private String city;
	@ApiModelProperty(position = 7)
	private String province;
	@ApiModelProperty(position = 8)
	private long phoneNumber;
	@ApiModelProperty(position = 9)
	private Date startDate;
	@ApiModelProperty(position = 10)
	private int addedBooks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getAddedBooks() {
		return addedBooks;
	}

	public void setAddedBooks(int addedBooks) {
		this.addedBooks = addedBooks;
	}
}
