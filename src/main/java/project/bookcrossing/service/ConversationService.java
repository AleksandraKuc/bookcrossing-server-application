package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.User;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.ConversationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private MessageService messageService;

	public Conversation createConversation(User firstUser, User secondUser) {
//		if (!conversationRepository.existsByFirstUserAndSecondUser(firstUser, secondUser)) {
			return conversationRepository.save(new Conversation(firstUser, secondUser));
//		} else {
//			throw new CustomException("Conversation is already created", HttpStatus.UNPROCESSABLE_ENTITY);
//		}
	}

	public List<Conversation> searchByUser(User user) {
		List<Conversation> conversations = conversationRepository.getAllByConversationUsers(user);
		if (conversations.isEmpty()) {
			throw new CustomException("The conversation doesn't exist", HttpStatus.NOT_FOUND);
		}
		return conversations;

	}

	public Conversation searchById(long conversationId) {
		Optional<Conversation> conversation = conversationRepository.findById(conversationId);
		if (conversation.isEmpty()) {
			throw new CustomException("The conversation doesn't exist", HttpStatus.NOT_FOUND);
		}
		return conversation.get();
	}

	public void deleteConversation(Conversation conversation) {
		messageService.deleteByConversation(conversation);
		conversationRepository.deleteById(conversation.getId_conversation());
	}

	public void deleteByUser(User user) {
		List<Conversation> conversations = conversationRepository.getAllByConversationUsers(user);
		if (conversations != null && !conversations.isEmpty()) {
			for (Conversation item : conversations) {
				deleteConversation(item);
			}
		}
	}
}
