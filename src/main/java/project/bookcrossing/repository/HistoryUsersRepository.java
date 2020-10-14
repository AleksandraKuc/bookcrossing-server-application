package project.bookcrossing.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.HistoryUsers;
import project.bookcrossing.entity.HistoryUsersKey;

import java.util.List;

@Repository
public interface HistoryUsersRepository extends CrudRepository<HistoryUsers, HistoryUsersKey> {
	List<HistoryUsers> findAll(Example<HistoryUsers> historyUsersExample);
}
