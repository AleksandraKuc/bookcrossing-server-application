package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import project.bookcrossing.entity.User;
import project.bookcrossing.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<User> getUserById(long id) {
		Optional<User> userData = userRepository.findById(id);
		return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public ResponseEntity<User> getUserByUsername(String username) {
		Optional<User> userData = userRepository.findByUsername(username);
		return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public ResponseEntity<List<User>> getUserByNames(String firstName, String lastName){

		try {
			List<User> _results = userRepository.findByFirstNameAndLastName(firstName, lastName);
			if (_results.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_results, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = (List<User>) userRepository.findAll();

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<User> postUser(User user){
		try {
			return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<HttpStatus> deleteUser(long id) {
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<User> updateUser(long id, User user) {
		Optional<User> userData = userRepository.findById(id);
		System.out.println(user);
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setUsername(user.getUsername() != null ? user.getUsername() : _user.getUsername());
			_user.setPassword(user.getPassword() != null ? user.getPassword() : _user.getPassword());
			_user.setFirstName(user.getFirstName() != null ? user.getFirstName() : _user.getFirstName());
			_user.setLastName(user.getLastName() != null ? user.getLastName() : _user.getLastName());
			_user.setEmail(user.getEmail() != null ? user.getEmail() : _user.getEmail());
			_user.setCity(user.getCity() != null ? user.getCity() : _user.getCity());
			_user.setProvince(user.getProvince() != null ? user.getProvince() : _user.getProvince());
			_user.setPhoneNumber(user.getPhoneNumber() != 0 ? user.getPhoneNumber() : _user.getPhoneNumber());
			return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
