package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.User;
import project.bookcrossing.repository.ConversationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

	@Autowired
	private ConversationRepository conversationRepository;

	public ResponseEntity<Conversation> createConversation(User firstUser, User secondUser) {
		try {
			return new ResponseEntity<>(conversationRepository.save(new Conversation(firstUser, secondUser)), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<List<Conversation>> getConversationsByUser(User user) {
		try {
			List<Conversation> conversations = conversationRepository.getAllByConversationUsers(user);
			if (conversations.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(conversations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<Conversation> getConversationById(long id) {
		Optional<Conversation> conversationData = conversationRepository.findById(id);
		return conversationData.map(conversation -> new ResponseEntity<>(conversation, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public ResponseEntity<HttpStatus> deleteConversation(long id_conversation) {
		try {
			conversationRepository.deleteById(id_conversation);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<HttpStatus> deleteConversationByUser(User user) {
		List<Conversation> conversations = conversationRepository.getAllByConversationUsers(user);
		if (conversations != null && !conversations.isEmpty()) {
			for (Conversation item : conversations) {
				deleteConversation(item.getId_conversation());
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
}
