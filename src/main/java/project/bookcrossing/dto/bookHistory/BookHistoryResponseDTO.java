package project.bookcrossing.dto.bookHistory;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class BookHistoryResponseDTO {

	@ApiModelProperty(position = 0)
	private long id_history;
	@ApiModelProperty(position = 1)
	private Date start_date;
	@ApiModelProperty(position = 2)
	private Date last_hire;

	public long getId_history() {
		return id_history;
	}

	public void setId_history(long id_history) {
		this.id_history = id_history;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getLast_hire() {
		return last_hire;
	}

	public void setLast_hire(Date last_hire) {
		this.last_hire = last_hire;
	}
}
