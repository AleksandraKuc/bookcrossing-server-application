package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.repository.BookHistoryRepository;

import java.util.Date;
import java.util.Optional;


@Service
public class BookHistoryService {

	@Autowired
	private BookHistoryRepository historyRepository;

	public ResponseEntity<BookHistory> createHistory() {
		try {
			BookHistory _bookHistory = historyRepository.save(new BookHistory(new Date(), new Date()));
			return new ResponseEntity<>(_bookHistory, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<BookHistory> getHistoryById(long id) {
		Optional<BookHistory> history = historyRepository.findById(id);
		return history.map(_history -> new ResponseEntity<>(_history, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public ResponseEntity<BookHistory> updateHistory(long history_id) {

		Optional<BookHistory> historyData = historyRepository.findById(history_id);

		if (historyData.isPresent()) {

			BookHistory _history = historyData.get();
			_history.setLast_hire();
			return new ResponseEntity<>(historyRepository.save(_history), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteHistory(long history_id) {
		try {
			historyRepository.deleteById(history_id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
