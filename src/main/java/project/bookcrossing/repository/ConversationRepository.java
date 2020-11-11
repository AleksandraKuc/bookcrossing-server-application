package project.bookcrossing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.User;

import java.util.List;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {
	List<Conversation> getAllByConversationUsers(User user);
}
