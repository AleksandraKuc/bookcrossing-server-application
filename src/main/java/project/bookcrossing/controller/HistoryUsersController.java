package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.HistoryUsers;
import project.bookcrossing.service.HistoryUsersService;

import java.util.List;

@RestController
@RequestMapping(value = "/historyUsers")
public class HistoryUsersController {

	@Autowired
	private HistoryUsersService historyUsersService;

	@PostMapping(value = "/create/{user_id}/{history_id}")
	public ResponseEntity<HistoryUsers> postHistoryUsers(@PathVariable long user_id, @PathVariable long history_id) {
		return historyUsersService.createHistoryUsers(history_id, user_id);
	}

	@GetMapping(value = "/getByHistory/{history_id}")
	public ResponseEntity<List<HistoryUsers>> getByHistory(@PathVariable long history_id) {
		return historyUsersService.getByHistoryKey(history_id);
	}

	@GetMapping(value = "/getByCurrentUser/{user_id}")
	public ResponseEntity<List<HistoryUsers>> getByCurrentUser(@PathVariable long user_id) {
		return historyUsersService.getByCurrentUserKey(user_id);
	}

	@GetMapping(value = "/getByFirstUser/{user_id}")
	public ResponseEntity<List<HistoryUsers>> getByFirstUser(@PathVariable long user_id) {
		return historyUsersService.getByFirstUserKey(user_id);
	}

	@PutMapping(value = "/update/{user_id}/{history_id}")
	public ResponseEntity<HistoryUsers> updateHistoryUsers(@PathVariable long user_id, @PathVariable long history_id) {
		return historyUsersService.updateHistoryUsers(user_id, history_id);
	}

	@DeleteMapping(value = "/delete/{history_id}")
	public ResponseEntity<HttpStatus> deleteHistoryUsers(@PathVariable long history_id) {
		return historyUsersService.deleteHistory(history_id);
	}
}
