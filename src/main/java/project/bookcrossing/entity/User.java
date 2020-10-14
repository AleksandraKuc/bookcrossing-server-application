package project.bookcrossing.entity;

import javax.persistence.*;
import java.sql.Date;

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
	private String first_name;
	@Column(nullable = false)
	private String last_name;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private String province;
	private long phone_number;
	private Date start_date;
	private boolean usertype;
	private int added_books;

	public User(){
	}

	public User(String username, String password, String first_name, String last_name, String email, String city, String province, long phone_number, Date start_date, boolean usertype, int added_books) {
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.city = city;
		this.province = province;
		this.phone_number = phone_number;
		this.start_date = start_date;
		this.usertype = usertype;
		this.added_books = added_books;
	}

	public User(String username, String password, String first_name, String last_name, String email, String city, String province, long phone_number) {
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.city = city;
		this.province = province;
		this.phone_number = phone_number;
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
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
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
		return phone_number;
	}

	public void setPhoneNumber(long phone_number) {
		this.phone_number = phone_number;
	}

	public int getAddedBooks() {
		return added_books;
	}

	public void setAddedBooks(int added_books) {
		this.added_books = added_books;
	}

	public long getId() {
		return id_user;
	}

	public Date getStartDate() {
		return start_date;
	}

	public boolean getUsertype() {
		return usertype;
	}

}
