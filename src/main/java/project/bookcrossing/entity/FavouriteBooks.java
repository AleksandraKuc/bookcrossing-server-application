package project.bookcrossing.entity;

import javax.persistence.*;

@Entity
public class FavouriteBooks {

	@EmbeddedId
	private FavouritesKey id_favBook;

	public FavouriteBooks(){}

	public FavouritesKey getId_favouriteBooks() {
	return id_favBook;
}

	public void setId_favouriteBooks(FavouritesKey id_favBook) {
		this.id_favBook = id_favBook;
	}

	@Override
	public String toString() {
		return "FavouriteBooks{" +
				"id_favBook=" + id_favBook +
				'}';
	}
}
