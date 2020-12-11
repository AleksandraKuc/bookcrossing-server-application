package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.dto.book.BookDataDTO;
import project.bookcrossing.dto.book.BookListResponseDTO;
import project.bookcrossing.dto.book.BookResponseDTO;
import project.bookcrossing.entity.*;
import project.bookcrossing.service.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100"})
@RestController
@RequestMapping(value = "/api/book")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private HistoryUsersService historyUsersService;
	@Autowired
	private BookHistoryService bookHistoryService;
	@Autowired
	private FavouriteBooksService favouriteBooksService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/all")
	@ApiOperation(value = "${BookController.getAll}", response = BookResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The book doesn't exist")})
	public BookListResponseDTO getAll(@ApiParam("title") @RequestParam String title,
									  @ApiParam("category") @RequestParam String category,
									  @ApiParam("maxResults") @RequestParam String maxResults,
									  @ApiParam("page") @RequestParam String page,
									  @ApiParam("username") @RequestParam String username) {
		List<Book> books = bookService.getAllBooks(title, category);
		BookListResponseDTO response = new BookListResponseDTO();
		response.setAmountAll(books.size());
		if (!maxResults.equals("") && !page.equals("")) {
			int startIndex = Integer.parseInt(page) * Integer.parseInt(maxResults);
			int endIndex = startIndex + Integer.parseInt(maxResults);
			endIndex = Math.min(endIndex, books.size());
			books = books.subList(startIndex, endIndex);
		}
		if (username.equals("null")) {
			response.setBooks(changeBookClass(books));
		} else {
			response.setBooks(checkCurrentUser(books, username));
		}
		return response;
	}

	@GetMapping(value = "/id/{id}")
	@ApiOperation(value = "${BookController.searchById}", response = BookResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public BookResponseDTO searchById(@ApiParam("Id") @PathVariable long id) {
		return modelMapper.map(bookService.searchById(id), BookResponseDTO.class);
	}

	@GetMapping(value = "/addedByUser/{username}")
	@ApiOperation(value = "${BookController.getAddedByUser}", response = BookResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public List<BookResponseDTO> getAddedByUser(@ApiParam("User") @PathVariable String username) {
		List<BookResponseDTO> books = new ArrayList<>();
		User user = userService.search(username);
		List<HistoryUsers> historyUsers = historyUsersService.searchByFirstUserKey(user.getId());
		for (HistoryUsers item : historyUsers){
			BookHistory bookHistory = bookHistoryService.searchById(item.getId_historyUsers().getId_history());
			Book book = bookService.getBookByHistory(bookHistory);
			books.add(modelMapper.map(book, BookResponseDTO.class));
		}
		return books;
	}

	@GetMapping(value = "/user/{username}")
	@ApiOperation(value = "${BookController.getByUser}", response = BookResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public List<BookResponseDTO> getByUser(@ApiParam("User") @PathVariable String username) {
		List<BookResponseDTO> books = new ArrayList<>();
		User user = userService.search(username);
		List<HistoryUsers> historyUsers = historyUsersService.searchByCurrentUserKey(user.getId());
		for (HistoryUsers item : historyUsers){
			BookHistory bookHistory = bookHistoryService.searchById(item.getId_historyUsers().getId_history());
			Book book = bookService.getBookByHistory(bookHistory);
			books.add(modelMapper.map(book, BookResponseDTO.class));
		}
		return books;
	}

	@GetMapping(value = "/fav/{username}")
	@ApiOperation(value = "${BookController.getByUser}", response = BookResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public BookListResponseDTO getFavByUser(@ApiParam("User") @PathVariable String username,
											@ApiParam("title") @RequestParam String title,
											@ApiParam("maxResults") @RequestParam String maxResults,
											@ApiParam("page") @RequestParam String page,
											@ApiParam("category") @RequestParam String category) {
		List<BookResponseDTO> books = new ArrayList<>();
		BookListResponseDTO response = new BookListResponseDTO();
		User user = userService.search(username);
		List<FavouriteBooks> favourites = favouriteBooksService.search(user.getId());
		for (FavouriteBooks fav : favourites){
			Book book = bookService.searchById(fav.getId_favouriteBooks().getId_book());
			if (bookService.checkSearchParams(book, title, category)) {
				books.add(modelMapper.map(book, BookResponseDTO.class));
			}
		}
		response.setAmountAll(books.size());
		if (!maxResults.equals("") && !page.equals("")) {
			int startIndex = Integer.parseInt(page) * Integer.parseInt(maxResults);
			int endIndex = startIndex + Integer.parseInt(maxResults);
			endIndex = Math.min(endIndex, books.size());
			books = books.subList(startIndex, endIndex);
		}
		response.setBooks(books);
		return response;
	}

	@PostMapping("/create/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${BookController.create}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public BookResponseDTO create(@ApiParam("Book") @RequestBody BookDataDTO book,
								  @ApiParam("Username") @PathVariable String username) {
		User user = userService.search(username);
		userService.updateAddedBooks(user, 1);
		Book savedBook = bookService.create(modelMapper.map(book, Book.class));
		long historyId = savedBook.getHistory().getId_history();
		historyUsersService.createHistoryUsers(user.getId(), historyId, "firstUser");
		return modelMapper.map(savedBook, BookResponseDTO.class);
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${BookController.update}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied")})
	public BookResponseDTO update(@ApiParam("Update Book") @RequestBody BookDataDTO book) {
		return modelMapper.map(bookService.update(modelMapper.map(book, Book.class)), BookResponseDTO.class);
	}

	@PutMapping("/updateHired/{bookId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${BookController.updateLastHired}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied")})
	public BookResponseDTO updateLastHired(@ApiParam("Update Book") @PathVariable long bookId) {
		return modelMapper.map(bookService.updateLastHired(bookId), BookResponseDTO.class);
	}

	@DeleteMapping(value = "/{bookId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${BookController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The book doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public long delete(@ApiParam("BookId") @PathVariable long bookId) {
		Book book = bookService.searchById(bookId);
		long historyId = book.getHistory().getId_history();

		HistoryUsers historyUser = historyUsersService.searchByHistory(historyId);
		User firstUser = userService.searchById(historyUser.getId_historyUsers().getId_user());
		userService.updateAddedBooks(firstUser, -1);

		//delete records from history_users
		historyUsersService.deleteByHistory(historyId);

		//delete records from book_history
		bookHistoryService.deleteHistory(historyId);

		FavouritesKey key = new FavouritesKey(bookId);
		//delete records from favourites_books
		favouriteBooksService.deleteFromList(key);

		//delete book
		bookService.delete(bookId);
		return bookId;
	}

	public List<BookResponseDTO> changeBookClass(List<Book> books) {
		List<BookResponseDTO> response = new ArrayList<>();
		for (Book book : books) {
			response.add(modelMapper.map(book, BookResponseDTO.class));
		}
		return response;
	}

	public List<BookResponseDTO> checkCurrentUser(List<Book> books, String username) {
		List<BookResponseDTO> response = new ArrayList<>();
		for (Book book : books) {
			List<HistoryUsers> historyUsers = historyUsersService.searchByHistoryKey(book.getHistory().getId_history());
			User user = null;
			if (historyUsers.size() == 1) {
				user = userService.searchById(historyUsers.get(0).getId_historyUsers().getId_user());
			} else if (historyUsers.get(0).getUserType().equals("currentUser")) {
				user = userService.searchById(historyUsers.get(0).getId_historyUsers().getId_user());
			} else if (historyUsers.get(1).getUserType().equals("currentUser")) {
				user = userService.searchById(historyUsers.get(1).getId_historyUsers().getId_user());
			}
			if (user != null && user.getUsername().equals(username)) {
				response.add(modelMapper.map(book, BookResponseDTO.class));
			}
		}
		return response;
	}
}
