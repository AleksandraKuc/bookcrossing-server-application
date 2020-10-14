package project.bookcrossing.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FavouritesKey implements Serializable {

	@Column(name = "id_book")
	Long id_book;

	@Column(name = "id_user")
	Long id_user;

	public FavouritesKey(){}

	public FavouritesKey(Long id_book, Long id_user) {
		this.id_book = id_book;
		this.id_user = id_user;
	}

	public Long getId_book() {
		return id_book;
	}

	public void setId_book(Long id_book) {
		this.id_book = id_book;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

}
