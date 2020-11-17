package project.bookcrossing.dto.report;

import io.swagger.annotations.ApiModelProperty;
import project.bookcrossing.entity.User;

import java.util.Date;

public class ReportResponseDTO {

	@ApiModelProperty(position = 0)
	private long idReport;
	@ApiModelProperty(position = 1)
	private String user;
	@ApiModelProperty(position = 2)
	private String reporter;
	@ApiModelProperty(position = 3)
	private String description;
	@ApiModelProperty(position = 4)
	private Date date;

	public ReportResponseDTO(long idReport, String user, String reporter, String description, Date date) {
		this.idReport = idReport;
		this.user = user;
		this.reporter = reporter;
		this.description = description;
		this.date = date;
	}

	public long getIdReport() {
		return idReport;
	}

	public void setIdReport(long idReport) {
		this.idReport = idReport;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ReportResponseDTO{" +
				"idReport=" + idReport +
				", user=" + user +
				", reporter=" + reporter +
				", description='" + description + '\'' +
				", date=" + date +
				'}';
	}
}
