package project.bookcrossing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	Book findByTitle(String beginning);

	Optional<Book> findById(long bookId);
}
