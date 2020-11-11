package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.dto.favouriteBooks.FavouriteBooksResponseDTO;
import project.bookcrossing.dto.user.UserDataDTO;
import project.bookcrossing.dto.user.UserResponseDTO;
import project.bookcrossing.entity.FavouriteBooks;
import project.bookcrossing.entity.FavouritesKey;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.FavouriteBooksService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/favouriteBooks")
public class FavouriteBooksController {

	@Autowired
	private FavouriteBooksService favouriteBooksService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create/{userId}/{bookId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.create}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied")})
	public FavouriteBooksResponseDTO create(@ApiParam("UserId") @PathVariable long userId,
											@ApiParam("BookId") @PathVariable long bookId) {
		return modelMapper.map(favouriteBooksService.create(userId, bookId), FavouriteBooksResponseDTO.class);
	}

	@GetMapping(value = "/search/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.search}", response = UserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public List<FavouriteBooksResponseDTO> search(@ApiParam("UserId") @PathVariable long userId) {
		List<FavouriteBooks> books = favouriteBooksService.search(userId);
		List<FavouriteBooksResponseDTO> response = new ArrayList<>();
		for (FavouriteBooks book : books) {
			response.add(modelMapper.map(book, FavouriteBooksResponseDTO.class));
		}
		return response;
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
		key.setId_user(bookId);
		favouriteBooksService.deleteFromList(key);
	}

	@DeleteMapping(value = "/delete/{userId}/{bookId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${FavouriteBooksController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "Fav_book doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void deleteByBook(@ApiParam("UserId") @PathVariable long userId,
							 @ApiParam("BookId") @PathVariable long bookId) {
		FavouritesKey key = new FavouritesKey(bookId, userId);
		favouriteBooksService.deleteByKey(key);
	}

}
