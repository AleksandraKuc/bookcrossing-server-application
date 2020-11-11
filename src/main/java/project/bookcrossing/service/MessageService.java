package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;
import project.bookcrossing.entity.User;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	public Message createMessage(Message message, User user, Conversation conversation) {
		if (conversation.getFirstUser().getId() != user.getId() &&
				conversation.getSecondUser().getId() != user.getId() ) {
			throw new CustomException("User doesn't belong to this conversation", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		message.setSender(user);
		message.setConversation(conversation);
		return messageRepository.save(message);
	}

	public List<Message> searchByConversation(Conversation conversation) {
		List<Message> messages = messageRepository.findByConversationOrderByDateDesc(conversation);
		if (messages.isEmpty()) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return messages;
	}

	public Message searchLastByConversation(Conversation conversation) {
		List<Message> allMessages = this.searchByConversation(conversation);
		return allMessages.get(0);
	}

	public void deleteMessage(long messageId){
		this.setSender(messageId);
		messageRepository.deleteById(messageId);
	}

	private void setSender(long messageId) {
		Optional<Message> _message = messageRepository.findByIdMessage(messageId);
		if (_message.isEmpty()) {
			throw new CustomException("The message doesn't exist", HttpStatus.NOT_FOUND);
		}
		_message.get().setSender(null);
		Message message = _message.get();
		messageRepository.save(message);
	}

	public void deleteByConversation(Conversation conversation){
		List<Message> messages = messageRepository.getAllByConversation(conversation);
		if (messages != null && !messages.isEmpty()) {
			for (Message item : messages) {
				deleteMessage(item.getId_message());
			}
		}
	}
}
