package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import project.bookcrossing.dto.book.BookDataDTO;
import project.bookcrossing.dto.book.BookResponseDTO;
import project.bookcrossing.entity.*;
import project.bookcrossing.repository.ImagesRepository;
import project.bookcrossing.service.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
	@Autowired
	private ImagesRepository imagesRepository;

	@GetMapping(value = "/all")
	@ApiOperation(value = "${BookController.getAll}", response = BookResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The book doesn't exist")})
	public List<BookResponseDTO> getAll(@ApiParam("title") @RequestParam String title,
										@ApiParam("category") @RequestParam String category,
										@ApiParam("username") @RequestParam String username) {
		List<Book> books = bookService.getAllBooks(title, category);
		if (username.equals("null")) {
			return changeBookClass(books);
		}
		return checkCurrentUser(books, username);
	}

	@GetMapping(value = "/id/{id}")
	@ApiOperation(value = "${BookController.searchById}", response = BookResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public BookResponseDTO searchById(@ApiParam("Id") @PathVariable long id) {
		return modelMapper.map(bookService.searchById(id), BookResponseDTO.class);
	}

//	@GetMapping(value = "/title&category/{title}/{category}/{username}")
//	@ApiOperation(value = "${BookController.searchByTitleCategory}", response = BookResponseDTO.class)
//	@ApiResponses(value = {//
//			@ApiResponse(code = 400, message = "Something went wrong"), //
//			@ApiResponse(code = 404, message = "The user doesn't exist")})
//	public List<BookResponseDTO> searchByTitleCategory(@ApiParam("Title") @PathVariable String title,
//											   @ApiParam("Category") @PathVariable String category,
//											   @ApiParam("Username") @PathVariable String username) {
//		List<Book> books = bookService.searchByTitleCategory(title, category);
//		if (username.equals("null")) {
//			return changeBookClass(books);
//		}
//		return checkCurrentUser(books, username);
//	}

//	@GetMapping(value = "/title/{title}/{username}")
//	@ApiOperation(value = "${BookController.searchByTitle}", response = BookResponseDTO.class)
//	@ApiResponses(value = {//
//			@ApiResponse(code = 400, message = "Something went wrong"), //
//			@ApiResponse(code = 404, message = "The user doesn't exist")})
//	public List<BookResponseDTO> searchByTitle(@ApiParam("Title") @PathVariable String title,
//											   @ApiParam("Username") @PathVariable String username) {
//		List<Book> books = bookService.searchByTitle(title);
//		if (username.equals("null")) {
//			return changeBookClass(books);
//		}
//		return checkCurrentUser(books, username);
//	}

//	@GetMapping(value = "/category/{category}/{username}")
//	@ApiOperation(value = "${BookController.searchByCategory}", response = BookResponseDTO.class)
//	@ApiResponses(value = {//
//			@ApiResponse(code = 400, message = "Something went wrong"), //
//			@ApiResponse(code = 404, message = "The user doesn't exist")})
//	public List<BookResponseDTO> searchByCategory(@ApiParam("Category") @PathVariable String category,
//												  @ApiParam("Username") @PathVariable String username) {
//		List<Book> books = bookService.searchByCategory(category);
//		if (username.equals("null")) {
//			return changeBookClass(books);
//		}
//		return checkCurrentUser(books, username);
//	}


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
	public List<BookResponseDTO> getFavByUser(@ApiParam("User") @PathVariable String username,
											  @ApiParam("title") @RequestParam String title,
											  @ApiParam("category") @RequestParam String category) {
		List<BookResponseDTO> books = new ArrayList<>();
		User user = userService.search(username);
		List<FavouriteBooks> favourites = favouriteBooksService.search(user.getId());
		for (FavouriteBooks fav : favourites){
			Book book = bookService.searchById(fav.getId_favouriteBooks().getId_book());
				if (bookService.checkSearchParams(book, title, category)) {
					books.add(modelMapper.map(book, BookResponseDTO.class));
				}
		}
		return books;
	}

	@PostMapping("/upload")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${BookController.uploadImage}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public Images uploadImage(@ApiParam("Image") @RequestBody MultipartFile image) {
		try {
			Images img = new Images(image.getOriginalFilename(), image.getContentType(),
					compressBytes(image.getBytes()));
			return imagesRepository.save(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			System.out.print("Problem!");
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException | DataFormatException ioe) {
			System.out.println("problem!");
		}
		return outputStream.toByteArray();
	}
}
