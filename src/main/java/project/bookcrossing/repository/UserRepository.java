package project.bookcrossing.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	List<User> findByFirstNameAndLastName(String firstName, String lastName);

	@Transactional
	void deleteByUsername(String username);
	boolean existsByUsername(String username);
}
