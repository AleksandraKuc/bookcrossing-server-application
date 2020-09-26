package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.service.BookHistoryService;
import project.bookcrossing.service.BookService;
import project.bookcrossing.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private BookHistoryService historyService;
	@Autowired
	private UserService userService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Book>> getAllBooks(){
		return bookService.getBooks();
	}

	@GetMapping(value = "/getBook/{id}")
	public ResponseEntity<Book> getBookDetails(@PathVariable long id){
		return bookService.getBookById(id);
	}

	@PostMapping(value = "/create/{userId}")
	public ResponseEntity<Book> postBook(@PathVariable long userId, @RequestBody Book book){
		ResponseEntity<Book> _book = bookService.postBook(book);

		if (_book.getStatusCode().equals(HttpStatus.CREATED)){

			BookHistory bookHistory = new BookHistory(userService.getUserById(userId).getBody(), userService.getUserById(userId).getBody(), _book.getBody());
			ResponseEntity<BookHistory>_history = historyService.postHistory(bookHistory);

			if (_history.getStatusCode().equals(HttpStatus.CREATED)){
				return _book;
			}
		}
		return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
//		return bookService.postBook(book);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book book){
		return bookService.updateBook(id, book);
	}
}
