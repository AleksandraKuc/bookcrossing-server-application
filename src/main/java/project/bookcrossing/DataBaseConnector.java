package project.bookcrossing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.bookcrossing.entity.*;
import project.bookcrossing.repository.*;

import java.util.Optional;

@Component
public class DataBaseConnector implements CommandLineRunner {
	private BookRepository bookRepository;
	private UserRepository userRepository;
//	private BookHistoryRepository historyRepository;
//	private MessageRepository messageRepository;
//	private ConversationRepository conversationRepository;

	@Autowired
	public DataBaseConnector(BookRepository bookRepository, UserRepository userRepository) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
//		this.historyRepository = historyRepository;
//		this.conversationRepository = conversationRepository;
	}

	@Override
	public void run(String... args) throws Exception {

//		this.userRepository.save(new User("login1", "haslo1", "imie1", "nazwisko1", "email1", "city1", "wojewodztwo1", 123456));
//		this.userRepository.save(new User("login2", "haslo2", "imie2", "nazwisko2", "email2", "city2", "wojewodztwo2", 123456));
//		this.userRepository.save(new User("login3", "haslo3", "imie3", "nazwisko3", "email3", "city3", "wojewodztwo3", 123456));
//		this.userRepository.save(new User("login4", "haslo4", "imie4", "nazwisko4", "email4", "city4", "wojewodztwo4", 123456));
//		Optional<User> _user1 = this.userRepository.findById(1);
//		Optional<User> _user2 = this.userRepository.findById(2);
//		Optional<Book> _book = this.bookRepository.findById(1);
//		if (_user1.isPresent() && _user2.isPresent() && _book.isPresent()){
//			User user1 = _user1.get();
//			System.out.println(user1.toString());
//			User user2 = _user2.get();
//			System.out.println(user2.toString());
//			Book book = _book.get();
////			this.conversationRepository.save(new Conversation(user1, user2));
//			this.historyRepository.save(new BookHistory(user1, book));
//		}
//		this.historyRepository.save(new BookHistory());
//		this.bookRepository.save(new Book("Poleska knieja oczarowani", "Albigowski Andrzej M.J", "", "83-86660-70-8", BookCategory.PopularScience));
//		this.bookRepository.save(new Book("Jakie piekne samobojstwo", "Rafal A. Ziemkiewicz", "", "9788375741056", BookCategory.Criminal));
//		this.bookRepository.save(new Book("Zywioly", "Kava Alex, Spindler Erica, Ellison J. T.", "", "", BookCategory.Thriller));
	}
}
