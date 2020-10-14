package project.bookcrossing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.BookHistory;

@Repository
public interface BookHistoryRepository extends CrudRepository<BookHistory, Long> {
}
