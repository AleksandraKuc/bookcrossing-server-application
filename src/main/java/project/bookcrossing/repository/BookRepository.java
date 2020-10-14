package project.bookcrossing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.BookHistory;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	Book findByTitle(String beginning);
	Book findByHistory(BookHistory bookHistory);
	Optional<Book> findById(long bookId);
}
