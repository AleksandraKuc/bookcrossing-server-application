package project.bookcrossing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.service.BookHistoryService;

@RestController
@RequestMapping(value = "/history")
public class BookHistoryController {

	@Autowired
	private BookHistoryService historyService;

	@PostMapping(value = "/create")
	public ResponseEntity<BookHistory> createHistory() {
		return historyService.createHistory();
	}

	@GetMapping(value = "/getHistory/{history_id}")
	public ResponseEntity<BookHistory> getBookDetails(@PathVariable long history_id) {
		return historyService.getHistoryById(history_id);
	}

	@PutMapping(value = "/update/{history_id}")
	public ResponseEntity<BookHistory> updateBook(@PathVariable long history_id) {
		return historyService.updateHistory(history_id);
	}

	@DeleteMapping(value = "/delete/{history_id}")
	public ResponseEntity<HttpStatus> deleteHistory(@PathVariable long history_id) {
		return historyService.deleteHistory(history_id);
	}
}
