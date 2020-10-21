package project.bookcrossing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.BookCategory;
import project.bookcrossing.entity.BookHistory;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	List<Book> findByTitleStartsWith(String title);
	List<Book> findByCategory(BookCategory category);
	List<Book> findByTitleStartsWithAndCategory(String title, BookCategory category);
	Book findByHistory(BookHistory bookHistory);
	Optional<Book> findById(long bookId);
}
