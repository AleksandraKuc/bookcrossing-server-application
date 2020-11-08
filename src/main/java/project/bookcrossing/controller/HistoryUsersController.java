package project.bookcrossing.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bookcrossing.dto.historyUsers.HistoryUserResponseDTO;
import project.bookcrossing.dto.user.UserDataDTO;
import project.bookcrossing.dto.user.UserResponseDTO;
import project.bookcrossing.entity.Book;
import project.bookcrossing.entity.BookHistory;
import project.bookcrossing.entity.HistoryUsers;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.BookHistoryService;
import project.bookcrossing.service.BookService;
import project.bookcrossing.service.HistoryUsersService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/historyUsers")
public class HistoryUsersController {

	@Autowired
	private HistoryUsersService historyUsersService;
	@Autowired
	private BookService bookService;
	@Autowired
	private BookHistoryService historyService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create/{userId}/{historyId}")
	@ApiOperation(value = "${HistoryUsersController.create}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public HistoryUserResponseDTO create(@ApiParam("UserId") @PathVariable long userId,
										 @ApiParam("HistoryId") @PathVariable long historyId) {
		return modelMapper.map(historyUsersService.createHistoryUsers(userId, historyId, "firstUser"), HistoryUserResponseDTO.class);
	}

	@GetMapping(value = "/history/{historyId}")
	@ApiOperation(value = "${HistoryUsersController.searchByHistory}", response = HistoryUserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The history doesn't exist")})
	public List<HistoryUserResponseDTO> searchByHistory(@ApiParam("HistoryId") @PathVariable long historyId) {
		List<HistoryUsers> users = historyUsersService.searchByHistoryKey(historyId);
		List<HistoryUserResponseDTO> response = new ArrayList<>();
		for (HistoryUsers user : users) {
			response.add(modelMapper.map(user, HistoryUserResponseDTO.class));
		}
		return response;
	}


	@GetMapping(value = "/currentUser/{userId}")
	@ApiOperation(value = "${HistoryUsersController.searchByCurrentUser}", response = HistoryUserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The history doesn't exist")})
	public List<HistoryUserResponseDTO> searchByCurrentUser(@ApiParam("UserId") @PathVariable long userId) {
		List<HistoryUsers> users = historyUsersService.getByCurrentUserKey(userId);
		List<HistoryUserResponseDTO> response = new ArrayList<>();
		for (HistoryUsers user : users) {
			response.add(modelMapper.map(user, HistoryUserResponseDTO.class));
		}
		return response;
	}

//	@GetMapping(value = "/getByCurrentUser/{user_id}")
//	public List<HistoryUsers> getByCurrentUser(@PathVariable long user_id) {
//		return historyUsersService.getByCurrentUserKey(user_id);
//	}

	@GetMapping(value = "/getByFirstUser/{user_id}")
	public List<HistoryUsers> getByFirstUser(@PathVariable long user_id) {
		return historyUsersService.getByFirstUserKey(user_id);
	}

	@PostMapping(value = "/update/{user_id}/{history_id}")
	public ResponseEntity<HistoryUsers> updateHistoryUsers(@PathVariable long user_id, @PathVariable long history_id) {
		BookHistory bookHistory = historyService.searchById(history_id);
		Book book = bookService.getBookByHistory(bookHistory);
		if (book != null) {
//			ResponseEntity<HttpStatus> _status = bookService.updateLastHired(book.getId_book());
//			if (_status.getStatusCode().equals(HttpStatus.OK)) {
//				return historyUsersService.updateHistoryUsers(user_id, history_id);
//			}
			Book _book = bookService.updateLastHired(book.getId_book());
			return historyUsersService.updateHistoryUsers(user_id, history_id);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/deleteByHistory/{history_id}")
	public ResponseEntity<HttpStatus> deleteHistoryUsers(@PathVariable long history_id) {
		return historyUsersService.deleteByHistory(history_id);
	}

	@DeleteMapping(value = "/deleteByUser/{user_id}")
	public ResponseEntity<HttpStatus> deleteByUsers(@PathVariable long user_id) {
		return historyUsersService.deleteByUser(user_id);
	}
}
