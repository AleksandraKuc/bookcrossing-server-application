package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.ConversationService;
import project.bookcrossing.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/conversation")
public class ConversationController {

	@Autowired
	private ConversationService conversationService;
	@Autowired
	private UserService userService;

	@PostMapping(value = "/create/{user_id}/{recipient_id}")
	public ResponseEntity<Conversation> postConversation(@PathVariable long user_id, @PathVariable long recipient_id) {
		User firstUser = userService.searchById(user_id);
		User secondUser = userService.searchById(recipient_id);
		return conversationService.createConversation(firstUser, secondUser);
	}

	@GetMapping(value = "/getByUser/{user_id}")
	public ResponseEntity<List<Conversation>> getByUser(@PathVariable long user_id){
		User user = userService.searchById(user_id);
		return conversationService.getConversationsByUser(user);
	}

	@DeleteMapping("/delete/{conversation_id}")
	public ResponseEntity<HttpStatus> deleteConversation(@PathVariable long conversation_id) {
		return conversationService.deleteConversation(conversation_id);
	}

	@DeleteMapping("/deleteByUser/{user_id}")
	public ResponseEntity<HttpStatus> deleteConversationsByUser(@PathVariable long user_id) {
		User user = userService.searchById(user_id);
		return conversationService.deleteConversationByUser(user);
	}
}
