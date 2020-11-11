package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import project.bookcrossing.entity.*;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.FavouriteBooksRepository;

import java.util.List;

@Service
public class FavouriteBooksService {

	@Autowired
	private FavouriteBooksRepository favouriteRepository;

	public FavouriteBooks create(long userId, long bookId) {
		FavouritesKey key = new FavouritesKey(bookId, userId);
		if (!favouriteRepository.existsById(key)) {
			FavouriteBooks favouriteBook = new FavouriteBooks();
			favouriteBook.setId_favouriteBooks(key);
			return favouriteRepository.save(favouriteBook);
		} else {
			throw new CustomException("Book is already in favourites", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public List<FavouriteBooks> search(long userId) {
		FavouriteBooks book = new FavouriteBooks();
		FavouritesKey key = new FavouritesKey();

		book.setId_favouriteBooks(key);
		key.setId_user(userId);
		List<FavouriteBooks> results = getByKey(book);
		if (results.isEmpty()) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return results;
	}

	public void deleteFromList(FavouritesKey fav_key){
		FavouriteBooks book = new FavouriteBooks();
		book.setId_favouriteBooks(fav_key);
		List<FavouriteBooks> books = getByKey(book);

		for (FavouriteBooks item : books) {
			deleteByKey(item.getId_favouriteBooks());
		}
	}

	public void deleteByKey(FavouritesKey fav_key) {
		favouriteRepository.deleteById(fav_key);
	}

	private List<FavouriteBooks> getByKey(FavouriteBooks _favBook) {
		Example<FavouriteBooks> favouriteExample = Example.of(_favBook);

		return favouriteRepository.findAll(favouriteExample);
	}
}
