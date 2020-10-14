package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.repository.BookRepository;
import project.bookcrossing.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookHistoryService historyService;

	public ResponseEntity<Book> createBook(Book book) {
		try {
			Book _book = bookRepository.save(book);
			return new ResponseEntity<>(_book, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<List<Book>> getBooks() {
		try {
			List<Book> books = (List<Book>) bookRepository.findAll();
			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Book> getBookById(long id) {

		Optional<Book> bookData = bookRepository.findById(id);

		return bookData.map(book -> new ResponseEntity<>(book, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

//	public ResponseEntity<List<Book>> getBooksByUser(long userId){
//	create list od book and forEach dla id history & add to list next books
/**
 * needs:
 * userService.getUserById() - jest
 * historyUser.getHistoryByUser()
 * bookHistory.getBookHistoryByHistory()
 * bookService.getBookByHistory()
 */

	public ResponseEntity<Book> updateBook(long book_id, Book book) {

		Optional<Book> bookData = bookRepository.findById(book_id);

		if (bookData.isPresent()) {

			Book _book = bookData.get();
			_book.setDescription(book.getDescription());

			return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteBook(long book_id) {
		try {
			bookRepository.deleteById(book_id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<HttpStatus> updateLastHired(long book_id) {
		Optional<Book> bookData = bookRepository.findById(book_id);

		if (bookData.isPresent()) {
			BookHistory history = bookData.get().getHistory();
			ResponseEntity<BookHistory> _history = historyService.updateHistory(history.getId_history());
			return new ResponseEntity<>(_history.getStatusCode());
		}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
}
