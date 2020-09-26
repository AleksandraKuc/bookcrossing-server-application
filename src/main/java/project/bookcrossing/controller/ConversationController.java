/*
package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.service.ConversationService;

import java.util.List;

@RestController
@RequestMapping(value = "/conversation")
public class ConversationController {

	@Autowired
	private ConversationService conversationService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Conversation>> getAllConversations() {
		return conversationService.getConversations();
	}

	@PostMapping(value = "/create/{userId}:{recipientId}")
	public ResponseEntity<Conversation> postConversation(@PathVariable long userId, @PathVariable long recipientId) {
		return conversationService.postConversation(userId, recipientId);
	}

	@DeleteMapping("/delete/{id_conversation}")
	public ResponseEntity<HttpStatus> deleteConversation(@PathVariable long id_conversation) {
		return conversationService.removeConversation(id_conversation);
	}
}
*/
