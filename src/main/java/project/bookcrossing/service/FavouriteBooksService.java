package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.*;
import project.bookcrossing.repository.FavouriteBooksRepository;

import java.util.List;

@Service
public class FavouriteBooksService {

	@Autowired
	private FavouriteBooksRepository favouriteRepository;

	public ResponseEntity<FavouriteBooks> createFavouriteBook(long book_id, long user_id) {
		try {
			FavouriteBooks favouriteBook = new FavouriteBooks();
			FavouritesKey key = new FavouritesKey(book_id, user_id);
			favouriteBook.setId_favouriteBooks(key);
			return new ResponseEntity<>(favouriteRepository.save(favouriteBook), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<List<FavouriteBooks>> getByUserKey(long user_id) {
		FavouriteBooks _favBook = new FavouriteBooks();
		FavouritesKey key = new FavouritesKey();

		_favBook.setId_favouriteBooks(key);
		key.setId_user(user_id);
		return getByKey(_favBook);
	}

	public ResponseEntity<HttpStatus> deleteFromList(FavouritesKey fav_key){
		FavouriteBooks _favBook = new FavouriteBooks();
		_favBook.setId_favouriteBooks(fav_key);
		List<FavouriteBooks> books = getByKey(_favBook).getBody();
		if (books != null) {
			if (!books.isEmpty()) {
				for (FavouriteBooks item : books) {
					deleteByKey(item.getId_favouriteBooks());
				}
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}

	public ResponseEntity<HttpStatus> deleteByKey(FavouritesKey fav_key) {
		try {
			favouriteRepository.deleteById(fav_key);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	private ResponseEntity<List<FavouriteBooks>> getByKey(FavouriteBooks _favBook) {
		Example<FavouriteBooks> favouriteExample = Example.of(_favBook);

		try {
			List<FavouriteBooks> _results = favouriteRepository.findAll(favouriteExample);
			if (_results.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_results, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
