package project.bookcrossing.dto.favouriteBooks;

import io.swagger.annotations.ApiModelProperty;

import project.bookcrossing.entity.FavouritesKey;

public class FavouriteBooksResponseDTO {

	@ApiModelProperty(position = 0)
	private FavouritesKey id_favBook;

	public FavouritesKey getId_favBook() {
		return id_favBook;
	}

	public void setId_favBook(FavouritesKey id_favourites) {
		this.id_favBook = id_favourites;
	}
}
