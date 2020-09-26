/*
package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.repository.BookHistoryRepository;


@Service
public class BookHistoryService {

	@Autowired
	private BookHistoryRepository historyRepository;

	//create - post
	public ResponseEntity<BookHistory> postHistory(BookHistory bookHistory) {
		try {
//			BookHistory bookHistory1 = new BookHistory( bookHistory.getCurrent_user(), bookHistory.getFirst_user(), bookHistory.getBook());
//			System.out.println(bookHistory1);
			BookHistory _bookHistory = historyRepository.save(bookHistory);
			System.out.println(_bookHistory.toString());
			return new ResponseEntity<>(_bookHistory, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	//update - put

	//getById - get





}
*/
