package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.FavouritesKey;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.ConversationService;
import project.bookcrossing.service.FavouriteBooksService;
import project.bookcrossing.service.HistoryUsersService;
import project.bookcrossing.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private FavouriteBooksService favouriteBooksService;
	@Autowired
	private ConversationService conversationService;
	@Autowired
	private HistoryUsersService historyUsersService;

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
		return userService.getUserByNames(firstName, lastName);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<User>> getUsers(){
		return userService.getAllUsers();
	}

	@PostMapping(value = "/create")
	public ResponseEntity<User> postUser(@RequestBody User user){
		return userService.postUser(user);
	}

	@DeleteMapping("/delete/{id_user}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id_user) {
		FavouritesKey key = new FavouritesKey();
		key.setId_user(id_user);
		ResponseEntity<HttpStatus> _fav = favouriteBooksService.deleteFromList(key);
		User user = getUserById(id_user).getBody();
		ResponseEntity<HttpStatus> _conv = conversationService.deleteConversationByUser(user);
		ResponseEntity<HttpStatus> _his = historyUsersService.deleteByUser(id_user);
		return userService.deleteUser(id_user);
	}

	@PutMapping("/update/{idUser}")
	public ResponseEntity<User> updateUser(@PathVariable long idUser, @RequestBody User user) {
		return userService.updateUser(idUser, user);
	}
}
