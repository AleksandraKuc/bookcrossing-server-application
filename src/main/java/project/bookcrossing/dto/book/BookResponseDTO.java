package project.bookcrossing.dto.book;

import io.swagger.annotations.ApiModelProperty;
import project.bookcrossing.entity.BookCategory;
import project.bookcrossing.entity.BookHistory;

public class BookResponseDTO {

	@ApiModelProperty(position = 0)
	private long id_book;
	@ApiModelProperty(position = 1)
	private String title;
	@ApiModelProperty(position = 2)
	private String author;
	@ApiModelProperty(position = 3)
	private String description;
	@ApiModelProperty(position = 4)
	private String ISBN;
	@ApiModelProperty(position = 5)
	private BookCategory category;
	@ApiModelProperty(position = 6)
	private BookHistory history;

	public long getId_book() { return id_book; }

	public void setId_book(long id_book) { this.id_book = id_book; }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public BookCategory getCategory() {
		return category;
	}

	public void setCategory(BookCategory category) {
		this.category = category;
	}

	public BookHistory getHistory() {
		return history;
	}

	public void setHistory(BookHistory history) {
		this.history = history;
	}
}
