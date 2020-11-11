package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.HistoryUsers;
import project.bookcrossing.entity.HistoryUsersKey;
import project.bookcrossing.entity.User;
import project.bookcrossing.exception.CustomException;
import project.bookcrossing.repository.HistoryUsersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryUsersService {

	@Autowired
	private HistoryUsersRepository historyUsersRepository;

	public HistoryUsers createHistoryUsers(long userId, long historyId, String type) {
		HistoryUsers historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey(historyId, userId);
		historyUser.setId_historyUsers(key);
		historyUser.setUserType(type);
		return historyUsersRepository.save(historyUser);
	}

	public List<HistoryUsers> searchByHistoryKey(long historyId) {
		HistoryUsers historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey();

		historyUser.setId_historyUsers(key);
		key.setId_history(historyId);

		return getByKey(historyUser);
	}

	public List<HistoryUsers> searchByCurrentUserKey(long user_id) {
		return getByUserKey(user_id, "currentUser");
	}

	public List<HistoryUsers> searchByFirstUserKey(long user_id) {
		return getByUserKey(user_id, "firstUser");
	}

	public HistoryUsers updateHistoryUsers(long historyId, long userId) {
		HistoryUsers _historyUser = new HistoryUsers();
		//searching by historyId for update user Id
		HistoryUsersKey key = new HistoryUsersKey(historyId);
		_historyUser.setId_historyUsers(key);

		List<HistoryUsers> history = getByKey(_historyUser);
		if (history.size() > 1){
			HistoryUsers historyData;
			if (history.get(0).getUserType().equals("currentUser")) {
				historyData = history.get(0);
			} else {
				historyData = history.get(1);
			}
			historyUsersRepository.deleteById(historyData.getId_historyUsers());
		}
		return createHistoryUsers(userId, historyId, "currentUser");
	}

	public void deleteByUser(long user_id) {
		HistoryUsers historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey();
		key.setId_user(user_id);
		historyUser.setId_historyUsers(key);
		this.deleteList(historyUser);
	}

	public void deleteByHistory(long history_id) {
		System.out.println("deleting history");
		HistoryUsers historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey(history_id);
		historyUser.setId_historyUsers(key);
		System.out.println("deleting By List");
		this.deleteList(historyUser);
	}

	// helper methods

	private void deleteList(HistoryUsers historyUser){
		System.out.println("del history step 2");
		Example<HistoryUsers> historyExample = Example.of(historyUser);
		List<HistoryUsers> _users = historyUsersRepository.findAll(historyExample);
		System.out.println(_users.toString());
		for (HistoryUsers us : _users) {
			historyUsersRepository.deleteById(us.getId_historyUsers());
		}
	}

	private List<HistoryUsers> getByUserKey(long user_id, String type) {
		HistoryUsers _historyUser = new HistoryUsers();
		HistoryUsersKey key = new HistoryUsersKey();

		_historyUser.setId_historyUsers(key);
		key.setId_user(user_id);

		List<HistoryUsers> usersList = getByKey(_historyUser);

		List<HistoryUsers> currentUsersList = new ArrayList<>();
		List<HistoryUsers> firstUsersList = new ArrayList<>();
		for (HistoryUsers item : usersList) {
			List<HistoryUsers> history = searchByHistoryKey(item.getId_historyUsers().getId_history());

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
		if (type.equals("currentUser")){
			if (currentUsersList.isEmpty()){
				throw new CustomException("Current historyUser doesn't exist", HttpStatus.NOT_FOUND);
			}
			return currentUsersList;
		} else {
			if (firstUsersList.isEmpty()) {
				throw new CustomException("First historyUser doesn't exist", HttpStatus.NOT_FOUND);
			}
			return firstUsersList;
		}

	}

	private List<HistoryUsers> getByKey(HistoryUsers historyUser) {
		Example<HistoryUsers> historyExample = Example.of(historyUser);

		List<HistoryUsers> results = historyUsersRepository.findAll(historyExample);
		if (results.isEmpty()) {
			throw new CustomException("The history user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return results;
	}
}
