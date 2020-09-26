package project.bookcrossing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	Book findByTitle(String beginning);

	Optional<Book> findById(long bookId);
}
