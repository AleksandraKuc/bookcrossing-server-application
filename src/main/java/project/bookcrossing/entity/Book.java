package project.bookcrossing.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "book_seq", allocationSize = 100)
public class Book implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private long id_book;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String author;

	private String description;
	private String ISBN;

	@Column(nullable = false)
	private BookCategory category;

	public Book(){}

	public Book(String title, String author, String description, String ISBN, BookCategory category){
		this.title = title;
		this.author = author;
		this.description = description;
		this.ISBN = ISBN;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
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

	public BookCategory getCategory(){
		return category;
	}

	public long getId_book(){
		return id_book;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id_book=" + id_book +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", description='" + description + '\'' +
				", ISBN='" + ISBN + '\'' +
				", category=" + category +
				'}';
	}
}
