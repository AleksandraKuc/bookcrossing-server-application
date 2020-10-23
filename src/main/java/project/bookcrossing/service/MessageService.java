package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;
import project.bookcrossing.entity.User;
import project.bookcrossing.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	public ResponseEntity<Message> createMessage(Message message, User user, Conversation conversation) {
		try {
			Message _message = messageRepository.save(new Message(message.getContent(), user, conversation));
			return new ResponseEntity<>(_message, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<List<Message>> getMessagesByConversation(Conversation conversation) {
		List<Message> messages = new ArrayList<>(messageRepository.findByConversationOrderByDateDesc(conversation));
		if (!messages.isEmpty()) {
			return new ResponseEntity<>(messages, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

	public ResponseEntity<Message> getLastMessageByConversation(Conversation conversation) {
		List<Message> allMessages = this.getMessagesByConversation(conversation).getBody();
		if (allMessages != null && !allMessages.isEmpty()) {
			Message message = allMessages.get(0);
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<HttpStatus> deleteMessage(long message_id){
		try {
			if (this.setUser(message_id).getStatusCode().equals(HttpStatus.OK)) {
				messageRepository.deleteById(message_id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	private ResponseEntity<Message> setUser(long messageId) {
		Optional<Message> _message = messageRepository.findByIdMessage(messageId);
		if (_message.isPresent()) {
			Message message = _message.get();
			message.setSender(null);
			return new ResponseEntity<>(messageRepository.save(message), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteByConversation(Conversation conversation){
		List<Message> messages = messageRepository.getAllByConversation(conversation);
		if (messages != null) {
			if (!messages.isEmpty()) {
				for (Message item : messages) {
					deleteMessage(item.getId_message());
				}
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
}
