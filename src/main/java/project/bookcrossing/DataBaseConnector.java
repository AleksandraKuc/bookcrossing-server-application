package project.bookcrossing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import project.bookcrossing.repository.*;

@Component
public class DataBaseConnector implements CommandLineRunner {

	private BookRepository bookRepository;
	private BookHistoryRepository historyRepository;
	private ConversationRepository conversationRepository;
	private FavouriteBooksRepository favouriteBooksRepository;
	private HistoryUsersRepository historyUsersRepository;
	private MessageRepository messageRepository;
	private UserRepository userRepository;

	@Autowired
	public DataBaseConnector(BookRepository bookRepository, BookHistoryRepository historyRepository,
			ConversationRepository conversationRepository, FavouriteBooksRepository favouriteBooksRepository,
			HistoryUsersRepository historyUsersRepository, MessageRepository messageRepository,
			UserRepository userRepository) {
		this.bookRepository = bookRepository;
		this.historyRepository = historyRepository;
		this.conversationRepository = conversationRepository;
		this.favouriteBooksRepository = favouriteBooksRepository;
		this.historyUsersRepository = historyUsersRepository;
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
