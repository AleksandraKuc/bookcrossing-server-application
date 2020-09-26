package project.bookcrossing.entity;

import javax.persistence.*;

@Entity
public class FavouriteBooks {

	@EmbeddedId
	private FavouritesKey id_favBook;

	@ManyToOne
	@MapsId("id_book")
	@JoinColumn(name = "id_book")
	private Book book_fav;

	@ManyToOne
	@MapsId("id_user")
	@JoinColumn(name = "id_user")
	private User user_fav;

	public FavouriteBooks(){}

	public FavouriteBooks(Book book_fav, User user_fav) {
		this.book_fav = book_fav;
		this.user_fav = user_fav;
	}

	public Book getBook_fav() {
		return book_fav;
	}

	public void setBook_fav(Book book_fav) {
		this.book_fav = book_fav;
	}

	public User getUser_fav() {
		return user_fav;
	}

	public void setUser_fav(User user_fav) {
		this.user_fav = user_fav;
	}
}
