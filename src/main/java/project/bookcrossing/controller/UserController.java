package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/getById/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long id){
		ResponseEntity<User> _user = userService.getUserById(id);
		System.out.println(_user.getBody());
		return _user;
//		return userService.getUserById(id);
	}

	@GetMapping(value = "/getByUsername/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username){
		return userService.getUserByUsername(username);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<User>> getUsers(){
		return userService.getUsers();
	}

	@PostMapping(value = "/create")
	public ResponseEntity<User> postUser(@RequestBody User user){
		return userService.postUser(user);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {
		return userService.deleteUser(id);
	}

	@PutMapping("/users/{idUser}")
	public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
		return userService.updateUser(id, user);
	}
}
