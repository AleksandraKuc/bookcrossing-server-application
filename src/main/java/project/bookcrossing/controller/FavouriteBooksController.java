package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.FavouriteBooks;
import project.bookcrossing.entity.FavouritesKey;
import project.bookcrossing.service.FavouriteBooksService;

import java.util.List;

@RestController
@RequestMapping(value = "/favouriteBooks")
public class FavouriteBooksController {

	@Autowired
	private FavouriteBooksService favouriteBooksService;

	@PostMapping(value = "/create/{user_id}/{book_id}")
	public ResponseEntity<FavouriteBooks> postFavouriteBook(@PathVariable long user_id, @PathVariable long book_id) {
		return favouriteBooksService.createFavouriteBook(book_id, user_id);
	}

	@GetMapping(value = "/getByUser/{user_id}")
	public ResponseEntity<List<FavouriteBooks>> getByUser(@PathVariable long user_id) {
		return favouriteBooksService.getByUserKey(user_id);
	}

	@DeleteMapping(value = "/deleteByUser/{user_id}")
	public ResponseEntity<HttpStatus> deleteByUser(@PathVariable long user_id) {
		FavouritesKey key = new FavouritesKey();
		key.setId_user(user_id);
		return favouriteBooksService.deleteFromList(key);
	}

	@DeleteMapping(value = "/deleteByBook/{book_id}")
	public ResponseEntity<HttpStatus> deleteByBook(@PathVariable long book_id) {
		FavouritesKey key = new FavouritesKey();
		key.setId_book(book_id);
		return favouriteBooksService.deleteFromList(key);
	}

	@DeleteMapping(value = "/delete/{user_id}/{book_id}")
	public ResponseEntity<HttpStatus> deleteHistoryUsers(@PathVariable long user_id, @PathVariable long book_id) {
		FavouritesKey key = new FavouritesKey(book_id, user_id);
		return favouriteBooksService.deleteByKey(key);
	}

}
