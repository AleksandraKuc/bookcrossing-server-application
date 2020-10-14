package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.*;
import project.bookcrossing.service.BookHistoryService;
import project.bookcrossing.service.BookService;
import project.bookcrossing.service.HistoryUsersService;
import project.bookcrossing.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private HistoryUsersService historyUsersService;
	@Autowired
	private BookHistoryService bookHistoryService;
	@Autowired
	private UserService userService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Book>> getAllBooks() {
		return bookService.getBooks();
	}

	@GetMapping(value = "/getBook/{book_id}")
	public ResponseEntity<Book> getBookDetails(@PathVariable long book_id) {
		return bookService.getBookById(book_id);
	}

	@GetMapping(value = "getBooksByUser/{user_id}")
	public ResponseEntity<List<Book>> getBooksByUser(@PathVariable long user_id) {
		List<Book> books = new ArrayList<>();
		List<HistoryUsers> historyUsers = historyUsersService.getByCurrentUserKey(user_id).getBody();
		if (historyUsers != null && !historyUsers.isEmpty()) {
			for (HistoryUsers item : historyUsers){
				BookHistory bookHistory = bookHistoryService.getHistoryById(item.getId_historyUsers().getId_history()).getBody();
				if (bookHistory != null) {
					Book book = bookService.getBookByHistory(bookHistory).getBody();
					if (book != null) {
						books.add(book);
					}
				}
			}
		}
		if (!books.isEmpty()) {
			return new ResponseEntity<>(books, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/create/{user_id}")
	public ResponseEntity<Book> postBook(@PathVariable long user_id, @RequestBody Book book) {

		BookHistory bookHistory = new BookHistory(new Date(), new Date());
		book.setHistory(bookHistory);

		ResponseEntity<Book> _book = bookService.createBook(book);

		if (_book.getStatusCode().equals(HttpStatus.CREATED)) {
			ResponseEntity<Book> sentBook = bookService.getBookById(Objects.requireNonNull(_book.getBody()).getId_book());
			if (sentBook.getStatusCode().equals(HttpStatus.OK)) {
				long historyId = Objects.requireNonNull(sentBook.getBody()).getHistory().getId_history();
				ResponseEntity<HistoryUsers> _historyUser = historyUsersService.createHistoryUsers(historyId, user_id);
				if (_historyUser.getStatusCode().equals(HttpStatus.CREATED)) {
					return _book;
				}
			}
		}
		return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	}

	@PutMapping(value = "/update/{book_id}")
	public ResponseEntity<Book> updateBook(@PathVariable long book_id, @RequestBody Book book) {
		return bookService.updateBook(book_id, book);
	}

	@PutMapping(value = "/update/hired/{book_id}")
	public ResponseEntity<HttpStatus> updateLastHired(@PathVariable long book_id) {
		return bookService.updateLastHired(book_id);
	}

	@DeleteMapping(value = "/delete/{book_id}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable long book_id) {
		long history_id = Objects.requireNonNull(bookService.getBookById(book_id).getBody()).getHistory().getId_history();
		if (bookHistoryService.deleteHistory(history_id).getStatusCode().equals(HttpStatus.NO_CONTENT)) {
			return bookService.deleteBook(book_id);
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
}
