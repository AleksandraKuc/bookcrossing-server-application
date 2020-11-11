package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.BookHistoryRepository;

import java.util.Optional;


@Service
public class BookHistoryService {

	@Autowired
	private BookHistoryRepository historyRepository;
	@Autowired
	private BookService bookService;

	public BookHistory createHistory() {
		return historyRepository.save(new BookHistory());
	}

	public BookHistory searchById(long id) {
		Optional<BookHistory> history = historyRepository.findById(id);
		if (history.isEmpty()) {
			throw new CustomException("The history doesn't exist", HttpStatus.NOT_FOUND);
		}
		return history.get();
	}

	public BookHistory updateHistory(long historyId) {

		Optional<BookHistory> historyData = historyRepository.findById(historyId);

		if (historyData.isEmpty()) {
			throw new CustomException("The history doesn't exist", HttpStatus.NOT_FOUND);
		} else {
			BookHistory history = historyData.get();
			history.setLast_hire();
			return historyRepository.save(history);
		}
	}

	public void deleteHistory(long historyId) {
		historyRepository.deleteById(historyId);
	}

	public void updateBookHireDate(long historyId){
		BookHistory bookHistory = searchById(historyId);
		bookService.updateBookHireDate(bookHistory);
	}
}
