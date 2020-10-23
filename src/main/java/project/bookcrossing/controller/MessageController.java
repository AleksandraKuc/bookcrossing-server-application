package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;
import project.bookcrossing.entity.User;
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

	@PostMapping(value = "/create/{id_conversation}/{id_user}")
	public ResponseEntity<Message> postMessage(@PathVariable long id_conversation, @PathVariable long id_user, @RequestBody Message message){
		ResponseEntity<Conversation> conversation = conversationService.getConversationById(id_conversation);
		if (conversation.getStatusCode().equals(HttpStatus.OK)) {
			if(conversation.getBody() != null) {
				User user = conversation.getBody().getFirstUser();
				if (id_user == user.getId()) {
					return messageService.createMessage(message, user, conversation.getBody());
				} else {
					user = conversation.getBody().getSecondUser();
					return messageService.createMessage(message, user, conversation.getBody());
				}
			}
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

	@DeleteMapping("/deleteByConversation/{conversation_id}")
	public ResponseEntity<HttpStatus> deleteMessageByConversation(@PathVariable long conversation_id) {
		Conversation conversation = conversationService.getConversationById(conversation_id).getBody();
		return messageService.deleteByConversation(conversation);
	}
}
