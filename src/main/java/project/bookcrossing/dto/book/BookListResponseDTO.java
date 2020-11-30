package project.bookcrossing.dto.book;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class BookListResponseDTO {

	@ApiModelProperty(position = 0)
	private long amountAll;
	@ApiModelProperty(position = 1)
	private List<BookResponseDTO> books;

	public long getAmountAll() {
		return amountAll;
	}

	public void setAmountAll(long amountAll) {
		this.amountAll = amountAll;
	}

	public List<BookResponseDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookResponseDTO> books) {
		this.books = books;
	}
}
