package project.bookcrossing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookcrossing.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findById(long userId);
//	List<User> findByUsertype(int usertype);
}
