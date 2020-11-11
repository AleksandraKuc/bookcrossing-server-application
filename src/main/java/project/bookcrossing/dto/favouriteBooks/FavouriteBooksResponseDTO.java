package project.bookcrossing.dto.favouriteBooks;

import io.swagger.annotations.ApiModelProperty;

import project.bookcrossing.entity.FavouritesKey;

public class FavouriteBooksResponseDTO {

	@ApiModelProperty(position = 0)
	private FavouritesKey id_favourites;

	public FavouritesKey getId_favourites() {
		return id_favourites;
	}

	public void setId_favourites(FavouritesKey id_favourites) {
		this.id_favourites = id_favourites;
	}
}
