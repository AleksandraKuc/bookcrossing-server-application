package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import project.bookcrossing.entity.*;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookHistoryService historyService;

	public List<Book> getBooks() {
		return (List<Book>) bookRepository.findAll();
	}

	public Book create(Book book) {
		BookHistory bookHistory = new BookHistory(new Date(), new Date());
		book.setHistory(bookHistory);
		return bookRepository.save(book);
	}

	public List<Book> getAllBooks(String title, String category) {
		if (!title.equals("") && !category.equals("")) {
			return searchByTitleCategory(title, category);
		} else if (!title.equals("")) {
			return searchByTitle(title);
		} else if (!category.equals("")) {
			return searchByCategory(category);
		}
		return (List<Book>) bookRepository.findAll();
	}

	public Book searchById(long id) {
		Optional<Book> book = bookRepository.findByIdBook(id);
		return book.orElse(null);
	}

	public List<Book> searchByTitleCategory(String title, String category) {
		return bookRepository.findByTitleStartsWithAndCategory(title, BookCategory.getEnumCategory(category));
	}

	public List<Book> searchByTitle(String title) {
		return bookRepository.findByTitleStartsWith(title);
	}

	public List<Book> searchByCategory(String category) {
		return bookRepository.findByCategory(BookCategory.getEnumCategory(category));
	}

	public Book getBookByHistory(BookHistory bookHistory) {
		Book book = bookRepository.findByHistory(bookHistory);
		if (book == null) {
			throw new CustomException("The book doesn't exist", HttpStatus.NOT_FOUND);
		}
		return book;
	}

	public Book update(Book book) {
		Optional<Book> _bookData = bookRepository.findByIdBook(book.getId_book());
		if(_bookData.isPresent()){
			Book bookData = _bookData.get();
			book.setTitle(book.getTitle() != null ? book.getTitle() : bookData.getTitle());
			book.setAuthor(book.getAuthor() != null ? book.getAuthor() : bookData.getAuthor());
			book.setDescription(book.getDescription() != null ? book.getDescription() : bookData.getDescription());
			book.setCategory(book.getCategory() != null ? book.getCategory() : bookData.getCategory());
			book.setISBN(book.getISBN() != null ? book.getISBN() : bookData.getISBN());
			book.setHistory(bookData.getHistory());
			return bookRepository.save(book);
		} else {
			throw new CustomException("The book doesn't exist", HttpStatus.NOT_FOUND);
		}
	}

	public void delete(long bookId) {
		bookRepository.deleteById(bookId);
	}

	public Book updateLastHired(long bookId) {
		Optional<Book> bookData = bookRepository.findByIdBook(bookId);
		if (bookData.isEmpty()) {
			throw new CustomException("The book doesn't exist", HttpStatus.NOT_FOUND);
		}
		BookHistory history = bookData.get().getHistory();
		historyService.updateHistory(history.getId_history());
		bookData = bookRepository.findByIdBook(bookId);
		if (bookData.isEmpty()) {
			throw new CustomException("The book doesn't exist", HttpStatus.NOT_FOUND);
		}
		return bookData.get();

	}

	public boolean checkSearchParams(Book book, String title, String category) {
		if (!title.equals("") && !category.equals("")) {
			return book.getTitle().startsWith(title) && book.getCategory().toString().equals(category);
		} else if (!title.equals("")) {
			return book.getTitle().startsWith(title);
		} else if (!category.equals("")) {
			return book.getCategory().toString().equals(category);
		}
		return true;
	}

	// for update from HistoryUser class
	public void updateBookHireDate(BookHistory history){
		Book book = getBookByHistory(history);
		updateLastHired(book.getId_book());
	}

}
