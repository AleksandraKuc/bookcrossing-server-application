package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.dto.bookHistory.BookHistoryResponseDTO;
import project.bookcrossing.service.BookHistoryService;

@RestController
@RequestMapping(value = "/history")
public class BookHistoryController {

	@Autowired
	private BookHistoryService historyService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create")
	@ApiOperation(value = "${BookHistoryController.createHistory}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied")})
	public BookHistoryResponseDTO createHistory() {
		return modelMapper.map(historyService.createHistory(), BookHistoryResponseDTO.class);
	}

	@GetMapping(value = "/id/{id}")
	@ApiOperation(value = "${BookHistoryController.searchById}", response = BookHistoryResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public BookHistoryResponseDTO searchById(@ApiParam("Id") @PathVariable long id) {
		return modelMapper.map(historyService.searchById(id), BookHistoryResponseDTO.class);
	}

	@PutMapping("/update/{historyId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${BookHistoryController.update}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied")})
	public BookHistoryResponseDTO update(@ApiParam("HistoryId") @PathVariable long historyId) {
		return modelMapper.map(historyService.updateHistory(historyId), BookHistoryResponseDTO.class);
	}

	@DeleteMapping(value = "/{historyId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${BookHistoryController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The user doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public long delete(@ApiParam("HistoryId") @PathVariable long historyId) {
		historyService.deleteHistory(historyId);
		return historyId;
	}
}
