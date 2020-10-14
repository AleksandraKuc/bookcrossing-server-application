package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;
import project.bookcrossing.service.ConversationService;
import project.bookcrossing.service.MessageService;

import java.util.List;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private ConversationService conversationService;

//	@GetMapping(value = "/all/{id_conversation}")
//	public ResponseEntity<List<Message>> getAllMessages(@PathVariable long id_conversation){
//		return messageService.getMessagesByConversation(id_conversation);
//	}

	@PostMapping(value = "/create/{id_conversation}")
	public ResponseEntity<Message> postMessage(@PathVariable long id_conversation, @RequestBody Message message){
		ResponseEntity<Conversation> conversation = conversationService.getConversationById(id_conversation);
		if (conversation.getStatusCode().equals(HttpStatus.OK)) {
			return messageService.createMessage(message, conversation.getBody());
		}
		return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	}

	@GetMapping(value = "/getByConversation/{conversation_id}")
	public ResponseEntity<List<Message>> getMessagesByConversation(@PathVariable long conversation_id){
		Conversation conversation = conversationService.getConversationById(conversation_id).getBody();
		return messageService.getMessagesByConversation(conversation);
	}

	@GetMapping(value = "/lastByConversation/{conversation_id}")
	public ResponseEntity<Message> getLastMessageByConversation(@PathVariable long conversation_id){
		Conversation conversation = conversationService.getConversationById(conversation_id).getBody();
		return messageService.getLastMessageByConversation(conversation);
	}

	@DeleteMapping("/delete/{message_id}")
	public ResponseEntity<HttpStatus> deleteMessage(@PathVariable long message_id) {
		return messageService.deleteMessage(message_id);
	}

	@DeleteMapping("/deleteByUser/{conversation_id}")
	public ResponseEntity<HttpStatus> deleteMessageByConversation(@PathVariable long conversation_id) {
		Conversation conversation = conversationService.getConversationById(conversation_id).getBody();
		return messageService.deleteByConversation(conversation);
	}
}
