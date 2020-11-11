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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/conversation")
public class ConversationController {

	@Autowired
	private ConversationService conversationService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create/{userId}/{recipientId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.create}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public ConversationResponseDTO create(@ApiParam("FirstUser") @PathVariable long userId,
										  @ApiParam("RecipientUser") @PathVariable long recipientId) {
		User firstUser = userService.searchById(userId);
		User secondUser = userService.searchById(recipientId);
		return modelMapper.map(conversationService.createConversation(firstUser, secondUser), ConversationResponseDTO.class);
	}

	@GetMapping(value = "/getByUser/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.searchByUser}", response = ConversationResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The conversation doesn't exist")})
	public List<ConversationResponseDTO> searchByUser(@ApiParam("UserId") @PathVariable long userId) {
		User user = userService.searchById(userId);
		List<Conversation> conversations = conversationService.searchByUser(user);
		List<ConversationResponseDTO> response = new ArrayList<>();
		for (Conversation conversation : conversations) {
			response.add(modelMapper.map(conversation, ConversationResponseDTO.class));
		}
		return response;
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

	@DeleteMapping(value = "/deleteByUser/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ConversationController.deleteByUser}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The conversation doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void deleteByUser(@ApiParam("UserId") @PathVariable long userId) {
		User user = userService.searchById(userId);
		conversationService.deleteByUser(user);
	}

}
