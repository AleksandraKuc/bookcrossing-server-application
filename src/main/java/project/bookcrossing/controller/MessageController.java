package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.dto.message.MessageDataDTO;
import project.bookcrossing.dto.message.MessageResponseDTO;
import project.bookcrossing.entity.Conversation;
import project.bookcrossing.entity.Message;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.ConversationService;
import project.bookcrossing.service.MessageService;
import project.bookcrossing.service.UserService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100"})
@RestController
@RequestMapping(value = "/api/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private ConversationService conversationService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${MessageController.create}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public MessageResponseDTO create(@ApiParam("Message") @RequestBody MessageDataDTO message) {
		User user = userService.search(message.getSender());
		Conversation conversation = conversationService.searchById(message.getConversationId());
		return messageService.createMessage(modelMapper.map(message, Message.class), user, conversation);
	}

	@GetMapping(value = "/get/{conversationId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${MessageController.searchByConversation}", response = MessageResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The conversation doesn't exist")})
	public List<MessageResponseDTO> searchByConversation(@ApiParam("ConversationId") @PathVariable long conversationId) {
		Conversation conversation = conversationService.searchById(conversationId);
		return messageService.searchByConversation(conversation);
	}

	@GetMapping(value = "/getLast/{conversationId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${MessageController.searchLastByConversation}", response = MessageResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The conversation doesn't exist")})
	public MessageResponseDTO searchLastByConversation(@ApiParam("ConversationId") @PathVariable long conversationId) {
		Conversation conversation = conversationService.searchById(conversationId);
		return modelMapper.map(messageService.searchLastByConversation(conversation), MessageResponseDTO.class);
	}

	@DeleteMapping(value = "/delete/{messageId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${MessageController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The message doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void delete(@ApiParam("MessageId") @PathVariable long messageId) {
		messageService.deleteMessage(messageId);
	}

	@DeleteMapping(value = "/deleteByConversation/{conversationId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${MessageController.deleteByConversation}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The message doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void deleteByConversation(@ApiParam("ConversationId") @PathVariable long conversationId) {
		Conversation conversation = conversationService.searchById(conversationId);
		messageService.deleteByConversation(conversation);
	}
}
