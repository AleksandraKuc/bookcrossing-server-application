package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.HistoryUsers;
import project.bookcrossing.entity.HistoryUsersKey;
import project.bookcrossing.repository.HistoryUsersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryUsersService {

	@Autowired
	private HistoryUsersRepository historyUsersRepository;

	public ResponseEntity<HistoryUsers> createHistoryUsers(long history_id, long user_id, String type) {
		try {
			HistoryUsers historyUser = new HistoryUsers();
			HistoryUsersKey key = new HistoryUsersKey(history_id, user_id);
			historyUser.setId_historyUsers(key);
			historyUser.setUserType(type);
			return new ResponseEntity<>(historyUsersRepository.save(historyUser), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<List<HistoryUsers>> getByHistoryKey(long historyId) {
		HistoryUsers _historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey();

		_historyUser.setId_historyUsers(key);
		key.setId_history(historyId);
		return getByKey(_historyUser);
	}

	public ResponseEntity<List<HistoryUsers>> getByCurrentUserKey(long user_id) {
		return getByUserKey(user_id, "currentUser");
	}

	public ResponseEntity<List<HistoryUsers>> getByFirstUserKey(long user_id) {
		return getByUserKey(user_id, "firstUser");
	}

	private ResponseEntity<List<HistoryUsers>> getByUserKey(long user_id, String type) {
		HistoryUsers _historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey();

		_historyUser.setId_historyUsers(key);
		key.setId_user(user_id);
		List<HistoryUsers> usersList = getByKey(_historyUser).getBody();
		List<HistoryUsers> currentUsersList = new ArrayList<>();
		List<HistoryUsers> firstUsersList = new ArrayList<>();
		if (usersList != null) {
			for (HistoryUsers item : usersList) {
				List<HistoryUsers> history = getByHistoryKey(item.getId_historyUsers().getId_history()).getBody();

				if (history != null && !history.isEmpty()) {
					if (history.size() == 1) {
						currentUsersList.add(item);
						firstUsersList.add(item);
					} else {
						if (history.get(0).getId_historyUsers().getId_user().equals(user_id)) {
							firstUsersList.add(item);
						} else {
							currentUsersList.add(item);
						}
					}
				}
			}
			if (type.equals("currentUser")){
				return new ResponseEntity<>(currentUsersList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(firstUsersList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<HistoryUsers> updateHistoryUsers(long user_id, long history_id) {
		HistoryUsers _historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey(history_id);
		_historyUser.setId_historyUsers(key);
		key.setId_history(history_id);
		List<HistoryUsers> history = getByKey(_historyUser).getBody();
		if (history != null && !history.isEmpty()) {
			try {
				if (history.size() > 1){
					HistoryUsers historyData;
					if (history.get(0).getUserType().equals("currentUser")) {
						historyData = history.get(0);
					} else {
						historyData = history.get(1);
					}
					historyUsersRepository.deleteById(historyData.getId_historyUsers());
				}
				return createHistoryUsers(history_id, user_id, "currentUser");
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<HttpStatus> deleteByUser(long user_id) {
		HistoryUsers historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey();
		key.setId_user(user_id);
		historyUser.setId_historyUsers(key);
		return this.deleteList(historyUser);
	}

	public ResponseEntity<HttpStatus> deleteByHistory(long history_id) {
		HistoryUsers historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey(history_id);
		historyUser.setId_historyUsers(key);
		return this.deleteList(historyUser);
	}

	private ResponseEntity<HttpStatus> deleteList(HistoryUsers historyUser){
		List<HistoryUsers> _users = getByKey(historyUser).getBody();
		if (_users != null && !_users.isEmpty()) {
			for (HistoryUsers us : _users) {
				historyUsersRepository.deleteById(us.getId_historyUsers());
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}

	private ResponseEntity<List<HistoryUsers>> getByKey(HistoryUsers _historyUser) {
		Example<HistoryUsers> historyExample = Example.of(_historyUser);

		try {
			List<HistoryUsers> _results = historyUsersRepository.findAll(historyExample);
			if (_results.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_results, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
