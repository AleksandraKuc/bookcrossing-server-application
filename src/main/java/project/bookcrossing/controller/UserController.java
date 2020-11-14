package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.dto.JwtResponse;
import project.bookcrossing.dto.user.LoginDataDTO;
import project.bookcrossing.dto.user.UserDataDTO;
import project.bookcrossing.dto.user.UserResponseDTO;
import project.bookcrossing.entity.FavouritesKey;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.ConversationService;
import project.bookcrossing.service.FavouriteBooksService;
import project.bookcrossing.service.HistoryUsersService;
import project.bookcrossing.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private FavouriteBooksService favouriteBooksService;
	@Autowired
	private ConversationService conversationService;
	@Autowired
	private HistoryUsersService historyUsersService;
	@Autowired
	private ModelMapper modelMapper;


	@PostMapping("/signin")
	@ApiOperation(value = "${UserController.signin}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 422, message = "Invalid username/password supplied")})
	public JwtResponse login(@ApiParam("credentials") @RequestBody LoginDataDTO credentials) {
		return userService.signin(credentials.getUsername(), credentials.getPassword());
	}

	@PostMapping("/signup")
	@ApiOperation(value = "${UserController.signup}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public JwtResponse signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
		return userService.signup(modelMapper.map(user, User.class));
	}

	@DeleteMapping(value = "/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${UserController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The user doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public String delete(@ApiParam("Username") @PathVariable String username) {
		User user = userService.search(username);
		//delete favourites books
		FavouritesKey key = new FavouritesKey();
		key.setId_user(user.getId());
		favouriteBooksService.deleteFromList(key);
		// delete conversations
		conversationService.deleteByUser(user);
		// delete user from books history
		historyUsersService.deleteByUser(user.getId());
		userService.delete(username);
		return username;
	}

	@GetMapping(value = "/username/{username}")
	@ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
		return modelMapper.map(userService.search(username), UserResponseDTO.class);
	}

	@GetMapping(value = "/names/{firstname}/{lastname}")
	@ApiOperation(value = "${UserController.searchByNames}", response = UserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public List<UserResponseDTO> searchByNames(@ApiParam("FirstName") @PathVariable String firstname,
											   @ApiParam("LastName") @PathVariable String lastname) {
		List<User> users = userService.searchByNames(firstname, lastname);
		List<UserResponseDTO> response = new ArrayList<>();
		for (User user : users) {
			response.add(modelMapper.map(user, UserResponseDTO.class));
		}
		return response;
	}

	@GetMapping(value = "/id/{id}")
	@ApiOperation(value = "${UserController.searchById}", response = UserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public UserResponseDTO searchById(@ApiParam("Id") @PathVariable long id) {
		return modelMapper.map(userService.searchById(id), UserResponseDTO.class);
	}

	@GetMapping(value = "/all")
	@ApiOperation(value = "${UserController.getAll}", response = UserResponseDTO.class)
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 404, message = "The user doesn't exist")})
	public List<UserResponseDTO> getAll() {
		List<User> users = userService.getAllUsers();
		List<UserResponseDTO> response = new ArrayList<>();
		for (User user : users) {
			response.add(modelMapper.map(user, UserResponseDTO.class));
		}
		return response;
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${UserController.update}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use")})
	public JwtResponse update(@ApiParam("Update User") @RequestBody UserDataDTO user) {
		return userService.update(modelMapper.map(user, User.class));
	}

	@GetMapping(value = "/me")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public UserResponseDTO whoami(HttpServletRequest req) {
		return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
	}

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}
}
