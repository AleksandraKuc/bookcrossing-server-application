package project.bookcrossing.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findById(Long userId);
	List<User> findAll(Example<User> userExample);
}
