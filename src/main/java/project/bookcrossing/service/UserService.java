package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.bookcrossing.entity.Role;
import project.bookcrossing.entity.User;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.UserRepository;
import project.bookcrossing.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public String signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public String signup(User user) {
		if (!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public String update(User user) {
		User userData = userRepository.findByUsername(user.getUsername());
		if(userData != null){
			user.setUsername(user.getUsername() != null ? user.getUsername() : userData.getUsername());
			user.setFirstName(user.getFirstName() != null ? user.getFirstName() : userData.getFirstName());
			user.setLastName(user.getLastName() != null ? user.getLastName() : userData.getLastName());
			user.setEmail(user.getEmail() != null ? user.getEmail() : userData.getEmail());
			user.setCity(user.getCity() != null ? user.getCity() : userData.getCity());
			user.setProvince(user.getProvince() != null ? user.getProvince() : userData.getProvince());
			user.setPhoneNumber(user.getPhoneNumber() != 0 ? user.getPhoneNumber() : userData.getPhoneNumber());
			user.setPassword(userData.getPassword());
			user.setId(userData.getId());
			user.setStartDate(userData.getStartDate());
			user.setAddedBooks(userData.getAddedBooks());
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		} else {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
	}

	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}

	public User search(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user;
	}

	public User searchById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user.get();
	}

	public List<User> searchByNames(String firstname, String lastname) {
		List<User> users = userRepository.findByFirstNameAndLastName(firstname, lastname);
		if (users.isEmpty()) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return users;
	}

	public User whoami(HttpServletRequest req) {
		return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	}

	public List<User> getAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		for (User user : users) {
			List<Role> roles = user.getRoles();
			for (Role role : roles) {
				if (role.equals(Role.ROLE_ADMIN)){
					users.remove(user);
				}
				break;
			}
		}
		if (users.isEmpty()) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return users;
	}

}
