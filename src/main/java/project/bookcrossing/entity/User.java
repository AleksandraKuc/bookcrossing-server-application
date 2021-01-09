package project.bookcrossing.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "user_seq", allocationSize = 100)
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private long id_user;

	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private String province;
	@Column(nullable = false)
	private int accountStatus;
	@Column(nullable = false)
	private int reported;
	private long phoneNumber;
	private Date startDate;
	private int addedBooks;

	@ElementCollection(fetch = FetchType.EAGER)
	List<Role> roles;

	public User(){
	}

	public User(String username, String password, String firstName, String lastName, String email, String city, String province, long phoneNumber, Date startDate, int addedBooks, int accountStatus) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.city = city;
		this.province = province;
		this.phoneNumber = phoneNumber;
		this.startDate = startDate;
		this.addedBooks = addedBooks;
		this.accountStatus = accountStatus;
	}

	public User(String username, String password, String firstName, String lastName, String email, String city, String province, long phoneNumber, int accountStatus) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.city = city;
		this.province = province;
		this.phoneNumber = phoneNumber;
		this.accountStatus = accountStatus;
	}

	public long getId() { return id_user; }

	public void setId(long id) {
		this.id_user = id;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getAddedBooks() {
		return addedBooks;
	}

	public void setAddedBooks(int addedBooks) {
		this.addedBooks = addedBooks;
	}

	public Date getStartDate() { return startDate; }

	public void setStartDate(Date startDate) { this.startDate = startDate; }

	public List<Role> getRoles() { return roles; }

	public void setRoles(List<Role> roles) { this.roles = roles; }

	public int getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Override
	public String toString() {
		return "User{" +
				"id_user=" + id_user +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", city='" + city + '\'' +
				", province='" + province + '\'' +
				", phoneNumber=" + phoneNumber +
				", startDate=" + startDate +
				", addedBooks=" + addedBooks +
				", accountStatus=" + accountStatus +
				'}';
	}
}
