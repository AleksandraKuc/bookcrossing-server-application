/*
package project.bookcrossing.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByConversation(Conversation conversation);
//	List<Message> findLastByDate(Conversation conversation);
	List<Message> findByConversationOrderByDateAsc(Conversation conversation);
}
*/
