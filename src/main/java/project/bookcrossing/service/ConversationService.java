package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.User;
import project.bookcrossing.repository.ConversationRepository;
import project.bookcrossing.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

	@Autowired
	private ConversationRepository conversationRepository;

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<Conversation> postConversation(long userId, long recipientId) {
		try {
			Optional<User> firstUser = userRepository.findById(userId);
			Optional<User> secondUser = userRepository.findById(recipientId);
			if (firstUser.isPresent()) {
				User _firstUser = firstUser.get();

				if (secondUser.isPresent()) {
					User _secondUser = secondUser.get();
					Conversation _conversation = conversationRepository.save(new Conversation(_firstUser, _secondUser));
					return new ResponseEntity<>(_conversation, HttpStatus.CREATED);
				}
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<List<Conversation>> getConversations() {
		try {
			List<Conversation> conversations = new ArrayList<>(conversationRepository.findAll());
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

	public ResponseEntity<HttpStatus> removeConversation(long id_conversation) {

		try {
			conversationRepository.deleteById(id_conversation);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
