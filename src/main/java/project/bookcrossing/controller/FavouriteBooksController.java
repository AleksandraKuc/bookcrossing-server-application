package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.dto.book.BookResponseDTO;
import project.bookcrossing.dto.favouriteBooks.FavouriteBooksResponseDTO;
import project.bookcrossing.dto.user.UserResponseDTO;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.FavouriteBooks;
import project.bookcrossing.entity.FavouritesKey;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.BookService;
import project.bookcrossing.service.FavouriteBooksService;
import project.bookcrossing.service.UserService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100"})
@RestController
@RequestMapping(value = "/api/favouriteBooks")
public class FavouriteBooksController {

	@Autowired
	private FavouriteBooksService favouriteBooksService;
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create/{username}/{bookId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.create}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied")})
	public FavouriteBooksResponseDTO create(@ApiParam("Username") @PathVariable String username,
											@ApiParam("BookId") @PathVariable long bookId) {
		User user = userService.search(username);
		return modelMapper.map(favouriteBooksService.create(user.getId(), bookId), FavouriteBooksResponseDTO.class);
	}

	@GetMapping(value = "/search/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.search}", response = UserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public List<BookResponseDTO> search(@ApiParam("UserId") @PathVariable long userId) {
		List<FavouriteBooks> favourites = favouriteBooksService.search(userId);
		List<BookResponseDTO> response = new ArrayList<>();
		for (FavouriteBooks favourite : favourites) {
			Book book = bookService.searchById(favourite.getId_favouriteBooks().getId_book());
			response.add(modelMapper.map(book, BookResponseDTO.class));
		}
		return response;
	}

	@GetMapping(value = "/check/{username}/{bookId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.searchByBook}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public boolean searchByBook(@ApiParam("Usernamed") @PathVariable String username,
										@ApiParam("BookId") @PathVariable long bookId) {
		User user = userService.search(username);
		return favouriteBooksService.checkIfFavourite(user.getId(), bookId);
	}

	@DeleteMapping(value = "/deleteByUser/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.deleteByUser}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "Fav_books don't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void deleteByUser(@ApiParam("UserId") @PathVariable long userId) {
		FavouritesKey key = new FavouritesKey();
		key.setId_user(userId);
		favouriteBooksService.deleteFromList(key);
	}

	@DeleteMapping(value = "/deleteByBook/{bookId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.deleteByBook}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "Fav_books don't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void deleteByBook(@ApiParam("BookId") @PathVariable long bookId) {
		FavouritesKey key = new FavouritesKey();
		key.setId_book(bookId);
		favouriteBooksService.deleteFromList(key);
	}

	@DeleteMapping(value = "/delete/{username}/{bookId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "Fav_book doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void deleteByBook(@ApiParam("Username") @PathVariable String username,
							 @ApiParam("BookId") @PathVariable long bookId) {
		User user = userService.search(username);
		FavouritesKey key = new FavouritesKey(bookId, user.getId());
		favouriteBooksService.deleteByKey(key);
	}

}
