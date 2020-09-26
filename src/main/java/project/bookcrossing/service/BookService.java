package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Book;
import project.bookcrossing.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public ResponseEntity<Book> postBook(Book book) {
		try {
			Book _book = bookRepository.save(new Book( book.getTitle(), book.getAuthor(), book.getDescription(), book.getISBN(), book.getCategory(), book.getHistory()));
			return new ResponseEntity<>(_book, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<List<Book>> getBooks() {
		try {
			List<Book> books = new ArrayList<>(bookRepository.findAll());
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

	public ResponseEntity<Book> updateBook(long id, Book book) {

		Optional<Book> bookData = bookRepository.findById(id);

		if (bookData.isPresent()) {

			Book _book = bookData.get();
			_book.setDescription(book.getDescription());

			return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
