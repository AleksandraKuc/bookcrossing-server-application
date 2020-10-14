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

	@GetMapping(value = "/getById/{user_id}")
	public ResponseEntity<User> getUserById(@PathVariable long user_id){
		return userService.getUserById(user_id);
	}

	@GetMapping(value = "/getByUsername/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username){
		return userService.getUserByUsername(username);
	}

	@GetMapping(value = "/getByNames/{firstName}/{lastName}")
	public ResponseEntity<List<User>> getUserByName(@PathVariable String firstName, @PathVariable String lastName){
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		return userService.getUserByNames(user);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<User>> getUsers(){
		return userService.getAllUsers();
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
