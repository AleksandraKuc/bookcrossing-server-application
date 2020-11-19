package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.bookcrossing.dto.JwtResponse;
import project.bookcrossing.entity.Role;
import project.bookcrossing.entity.User;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.UserRepository;
import project.bookcrossing.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

	public JwtResponse signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = search(username);
			if (user.getAccountStatus() == 0) {
				return getToken(user);
			} else {
				throw new CustomException("Account blocked. Contact with administrator to get details", HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public JwtResponse signup(User user) {
		if (!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return getToken(user);
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public JwtResponse resetPassword(User user, String currentPassword, String newPassword){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), currentPassword));
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			return getToken(user);
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public JwtResponse update(User user) {
		User userData = userRepository.findByUsername(user.getUsername());
		if(userData != null){
			userData.setUsername(user.getUsername() != null ? user.getUsername() : userData.getUsername());
			userData.setFirstName(user.getFirstName() != null ? user.getFirstName() : userData.getFirstName());
			userData.setLastName(user.getLastName() != null ? user.getLastName() : userData.getLastName());
			userData.setEmail(user.getEmail() != null ? user.getEmail() : userData.getEmail());
			userData.setCity(user.getCity() != null ? user.getCity() : userData.getCity());
			userData.setProvince(user.getProvince() != null ? user.getProvince() : userData.getProvince());
			userData.setPhoneNumber(user.getPhoneNumber() != 0 ? user.getPhoneNumber() : userData.getPhoneNumber());
			userRepository.save(userData);
			return getToken(userData);
		} else {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
	}

	private JwtResponse getToken(User user) {
		String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		JwtResponse response = new JwtResponse();
		response.setAccessToken(token);
		response.setUsername(user.getUsername());
		List<String> roles = new ArrayList<>();
		user.getRoles().forEach(role -> roles.add(role.name()));
		response.setAuthorities(roles);
		return response;
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
		List<User> usersToRemove = new ArrayList<>();
		for (User user : users) {
			List<Role> roles = user.getRoles();
			for (Role role : roles) {
				if (role.equals(Role.ROLE_ADMIN)){
					usersToRemove.add(user);
				}
				break;
			}
		}
		for (User user: usersToRemove) {
			users.remove(user);
		}
		if (users.isEmpty()) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return users;
	}

	public User updateAccountStatus(String username) {
		User user = search(username);
		user.setAccountStatus( user.getAccountStatus() == 0 ? 1 : 0 );
		return userRepository.save(user);
	}

	public void updateAddedBooks(User user, int value) {
		user.setAddedBooks(user.getAddedBooks() + value);
		userRepository.save(user);
	}

}
