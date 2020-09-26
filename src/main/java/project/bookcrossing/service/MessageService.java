package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;
import project.bookcrossing.repository.ConversationRepository;
import project.bookcrossing.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private ConversationRepository conversationRepository;

	public ResponseEntity<List<Message>> getMessagesByConversation(long id_conversation) {
		Optional<Conversation> _conversation = conversationRepository.findById(id_conversation);
		if (_conversation.isPresent()) {
			Conversation conversation = _conversation.get();
			List<Message> messages = new ArrayList<>(messageRepository.findByConversationOrderByDateAsc(conversation));
			return new ResponseEntity<>(messages, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	public ResponseEntity<Message> getLastMessageByConversation(long id_conversation) {
		ResponseEntity<List<Message>> _allMessages = this.getMessagesByConversation(id_conversation);
		if (_allMessages.getStatusCode().equals(HttpStatus.OK)) {
			List<Message> messages = _allMessages.getBody();
			if (messages != null) {
				Message message = messages.get(0);
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<Message> postMessage(Message message, Conversation conversation) {
		try {
			Message _message = messageRepository.save(new Message(message.getContent(), conversation));
			return new ResponseEntity<>(_message, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
}
