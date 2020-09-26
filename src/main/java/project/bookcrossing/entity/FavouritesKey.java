package project.bookcrossing.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavouritesKey implements Serializable {

	@Column(name = "id_book")
	Long id_book;

	@Column(name = "id_user")
	Long id_user;
}
