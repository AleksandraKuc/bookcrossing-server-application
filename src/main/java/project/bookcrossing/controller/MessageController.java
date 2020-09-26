/*
package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;
import project.bookcrossing.service.ConversationService;
import project.bookcrossing.service.MessageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private ConversationService conversationService;

	@GetMapping(value = "/all/{id_conversation}")
	public ResponseEntity<List<Message>> getAllMessages(@PathVariable long id_conversation){
		return messageService.getMessagesByConversation(id_conversation);
	}

	@GetMapping(value = "/lastByConversation/{id_conversation}")
	public ResponseEntity<Message> getLastMessageByConversation(@PathVariable long id_conversation){
		return messageService.getLastMessageByConversation(id_conversation);
	}

	@PostMapping(value = "/create/{id_conversation}")
	public ResponseEntity<Message> postMessage(@PathVariable long id_conversation, @RequestBody Message message){
		ResponseEntity<Conversation> conversation = conversationService.getConversationById(id_conversation);
		if (conversation.getStatusCode().equals(HttpStatus.OK)) {
			return messageService.postMessage(message, conversation.getBody());
		}
		return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	}
}
*/
