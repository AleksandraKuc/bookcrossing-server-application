package project.bookcrossing.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "report_seq", allocationSize = 100)
public class Report {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private long id_report;

	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private Date date;

	@ManyToMany
	@JoinTable(
			name = "report_user",
			joinColumns = @JoinColumn(name = "id_report"),
			inverseJoinColumns = @JoinColumn(name = "id_user"))
	private List<User> reportUsers;

	public Report(){
	}

	public Report(String description, User firstUser, User secondUser) {
		this.description = description;
		this.reportUsers = new ArrayList<>();
		this.reportUsers.add(0, firstUser);
		this.reportUsers.add(1, secondUser);
		this.date = new Date();
	}

	public long getId_report() {
		return id_report;
	}

	public void setId_report(long id_report) {
		this.id_report = id_report;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getFirstUser() {
		return reportUsers.get(0);
	}

	public void setFirstUser(User firstUser) {
		this.reportUsers.set(0, firstUser);
	}

	public User getSecondUser() {
		return reportUsers.get(1);
	}

	public void setSecondUser(User secondUser) {
		this.reportUsers.set(1, secondUser);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Report{" +
				"id_report=" + id_report +
				", description='" + description + '\'' +
				", reportUsers=" + reportUsers +
				", date=" + date +
				'}';
	}
}
