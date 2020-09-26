package project.bookcrossing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.BookCategory;
import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.entity.User;
import project.bookcrossing.repository.*;

@Component
public class DataBaseConnector implements CommandLineRunner {
	private BookRepository bookRepository;
	private UserRepository userRepository;
//	private BookHistoryRepository historyRepository;
//	private MessageRepository messageRepository;
	private ConversationRepository conversationRepository;

	@Autowired
	public DataBaseConnector(BookRepository bookRepository, UserRepository userRepository, BookHistoryRepository historyRepository, MessageRepository messageRepository, ConversationRepository conversationRepository) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
//		this.historyRepository = historyRepository;
		this.conversationRepository = conversationRepository;
	}

	@Override
	public void run(String... args) throws Exception {
//		this.userRepository.save(new User("loginxdd", "haslo", "imie", "nazwisko", "email", "city", "wojewodztwo", 123456));

//		this.bookRepository.save(new Book("Poleska knieja oczarowani", "Albigowski Andrzej M.J", "", "83-86660-70-8", BookCategory.PopularScience));
//		this.bookRepository.save(new Book("Jakie piekne samobojstwo", "Rafal A. Ziemkiewicz", "", "9788375741056", BookCategory.Criminal));
//		this.bookRepository.save(new Book("Zywioly", "Kava Alex, Spindler Erica, Ellison J. T.", "", "", BookCategory.Thriller));
	}
}
