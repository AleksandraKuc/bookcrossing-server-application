package project.bookcrossing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
	List<Message> getAllByConversation(Conversation conversation);
//	List<Message> findLastByDate(Conversation conversation);
	List<Message> findByConversationOrderByDateAsc(Conversation conversation);
}
