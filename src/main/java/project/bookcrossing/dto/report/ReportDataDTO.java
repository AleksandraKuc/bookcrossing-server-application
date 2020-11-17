package project.bookcrossing.dto.report;

import io.swagger.annotations.ApiModelProperty;

public class ReportDataDTO {

	@ApiModelProperty(position = 0)
	private String reporter;
	@ApiModelProperty(position = 1)
	private String user;
	@ApiModelProperty(position = 2)
	private String description;

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ReportDataDTO{" +
				"reporter='" + reporter + '\'' +
				", user='" + user + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
