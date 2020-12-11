package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.dto.conversation.ConversationResponseDTO;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.ConversationService;
import project.bookcrossing.service.UserService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100"})
@RestController
@RequestMapping(value = "/api/conversation")
public class ConversationController {

	@Autowired
	private ConversationService conversationService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create/{username}/{recipientName}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.create}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public ConversationResponseDTO create(@ApiParam("FirstUser") @PathVariable String username,
										  @ApiParam("RecipientUser") @PathVariable String recipientName) {
		User firstUser = userService.search(username);
		User secondUser = userService.search(recipientName);
		Conversation conversation = conversationService.createConversation(firstUser, secondUser);
		return new ConversationResponseDTO(conversation.getId_conversation(), secondUser);

	}

	@GetMapping("/byUsers/{username}/{recipientName}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.getConvByUsers}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public ConversationResponseDTO getConvByUsers(@ApiParam("FirstUser") @PathVariable String username,
										  @ApiParam("RecipientUser") @PathVariable String recipientName) {
		User firstUser = userService.search(username);
		User secondUser = userService.search(recipientName);
		Conversation conversation = conversationService.getByUsers(firstUser, secondUser);
		return new ConversationResponseDTO(conversation.getId_conversation(), secondUser);
	}

	@GetMapping("/exists/{username}/{recipientName}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.checkIfExists}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public boolean checkIfExists(@ApiParam("FirstUser") @PathVariable String username,
										  @ApiParam("RecipientUser") @PathVariable String recipientName) {
		User firstUser = userService.search(username);
		User secondUser = userService.search(recipientName);
		return conversationService.checkIfExists(firstUser, secondUser);
	}

	@GetMapping(value = "/getByUser/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.searchByUser}", response = ConversationResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The conversation doesn't exist")})
	public List<ConversationResponseDTO> searchByUser(@ApiParam("User") @PathVariable String username) {
		User user = userService.search(username);
		return conversationService.search(user);
	}

	@DeleteMapping(value = "/delete/{conversationId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The conversation doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void delete(@ApiParam("ConversationId") @PathVariable long conversationId) {
		Conversation conversation = conversationService.searchById(conversationId);
		conversationService.deleteConversation(conversation);
	}

	@DeleteMapping(value = "/deleteByUser/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.deleteByUser}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The conversation doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void deleteByUser(@ApiParam("User") @PathVariable String username) {
		User user = userService.search(username);
		conversationService.deleteByUser(user);
	}

}
