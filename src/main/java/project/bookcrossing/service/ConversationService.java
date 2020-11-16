package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.bookcrossing.dto.conversation.ConversationResponseDTO;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.User;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.ConversationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private MessageService messageService;

	public Conversation createConversation(User firstUser, User secondUser) {
		if (!checkIfExists(firstUser, secondUser)) {
			return conversationRepository.save(new Conversation(firstUser, secondUser));
		} else {
			throw new CustomException("Conversation is already created", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public List<ConversationResponseDTO> search(User user) {
		List<Conversation> conversations = searchByUser(user);
		List<ConversationResponseDTO> result = new ArrayList<>();
		for (Conversation conversation : conversations) {
			User _user = conversation.getFirstUser().getUsername().equals(user.getUsername())
					? conversation.getSecondUser()
					: conversation.getFirstUser();
			result.add(new ConversationResponseDTO(conversation.getId_conversation(), _user));
		}
		return result;
	}

	public List<Conversation> searchByUser(User user) {
		return conversationRepository.getAllByConversationUsers(user);
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

	public Conversation getByUsers(User first, User second) {
		List<Conversation> conversations = searchByUser(first);
		for (Conversation conv : conversations) {
			if ((conv.getFirstUser().getId() == first.getId() && conv.getSecondUser().getId() == second.getId()) ||
					(conv.getFirstUser().getId() == second.getId() && conv.getSecondUser().getId() == first.getId())) {
				return conv;
			}
		}
		return null;
	}
	public boolean checkIfExists(User first, User second) {
		List<Conversation> conversations = searchByUser(first);
		for (Conversation conv : conversations) {
			if ((conv.getFirstUser().getId() == first.getId() && conv.getSecondUser().getId() == second.getId()) ||
					(conv.getFirstUser().getId() == second.getId() && conv.getSecondUser().getId() == first.getId())) {
				System.out.println("true");
				return true;
			}
		}
		System.out.println("false");
		return false;
	}
}
