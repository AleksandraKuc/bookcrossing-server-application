package project.bookcrossing.dto.book;

import io.swagger.annotations.ApiModelProperty;

import project.bookcrossing.entity.BookCategory;

public class BookDataDTO {

	@ApiModelProperty(position = 0)
	private long idBook;
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

	public long getId_book() {
		return idBook;
	}

	public void setId_book(long id) {
		this.idBook = id;
	}

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

	@Override
	public String toString() {
		return "BookDataDTO{" +
				"id=" + idBook +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", description='" + description + '\'' +
				", ISBN='" + ISBN + '\'' +
				", category=" + category +
				'}';
	}
}
