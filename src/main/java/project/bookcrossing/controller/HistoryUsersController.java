package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.dto.historyUsers.HistoryUserResponseDTO;
import project.bookcrossing.entity.HistoryUsers;
import project.bookcrossing.service.BookHistoryService;
import project.bookcrossing.service.HistoryUsersService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/historyUsers")
public class HistoryUsersController {

	@Autowired
	private HistoryUsersService historyUsersService;
	@Autowired
	private BookHistoryService historyService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create/{userId}/{historyId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
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
		List<HistoryUsers> users = historyUsersService.searchByCurrentUserKey(userId);
		List<HistoryUserResponseDTO> response = new ArrayList<>();
		for (HistoryUsers user : users) {
			response.add(modelMapper.map(user, HistoryUserResponseDTO.class));
		}
		return response;
	}

	@GetMapping(value = "/firstUser/{userId}")
	@ApiOperation(value = "${HistoryUsersController.searchByFirstUser}", response = HistoryUserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The history doesn't exist")})
	public List<HistoryUserResponseDTO> searchByFirstUser(@ApiParam("UserId") @PathVariable long userId) {
		List<HistoryUsers> users = historyUsersService.searchByFirstUserKey(userId);
		List<HistoryUserResponseDTO> response = new ArrayList<>();
		for (HistoryUsers user : users) {
			response.add(modelMapper.map(user, HistoryUserResponseDTO.class));
		}
		return response;
	}

	// TODO tutaj bookId i username!!
	@PutMapping("/update/{historyId}/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${HistoryUsersController.update}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied")})
	public HistoryUserResponseDTO update(@ApiParam("HistoryId") @PathVariable long historyId,
										 @ApiParam("UserId") @PathVariable long userId) {
		historyService.updateBookHireDate(historyId);
		return modelMapper.map(historyUsersService.updateHistoryUsers(historyId, userId), HistoryUserResponseDTO.class);
	}

	@DeleteMapping(value = "/delete/{historyId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${HistoryUsersController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The history user doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public long delete(@ApiParam("HistoryId") @PathVariable long historyId) {
		historyUsersService.deleteByHistory(historyId);
		return historyId;
	}

	@DeleteMapping(value = "/deleteByUser/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${HistoryUsersController.deleteByUser}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The history user doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public long deleteByUser(@ApiParam("UserId") @PathVariable long userId) {
		historyUsersService.deleteByUser(userId);
		return userId;
	}

}
