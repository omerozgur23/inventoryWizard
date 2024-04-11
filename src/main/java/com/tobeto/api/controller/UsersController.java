package com.tobeto.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.UserService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.user.CreateUserRequest;
import com.tobeto.dto.user.GetAllUserResponse;
import com.tobeto.entities.concretes.User;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapperService modelMapper;

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/create")
	public void create(@RequestBody CreateUserRequest request) {
		User user = modelMapper.forRequest().map(request, User.class);
		userService.create(user);
	}

	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public ResponseEntity<List<GetAllUserResponse>> getAll() {
		List<User> users = userService.getAll();
		List<GetAllUserResponse> result = new ArrayList<>();
		users.forEach(user -> {
			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);

			response.setRole(userService.getUserRoles(user));
			result.add(response);
		});
		return ResponseEntity.ok(result);
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@RequestBody UUID id) {
		userService.delete(id);
		return new SuccessResponse();
	}

	@GetMapping("/getallByPage")
	public ResponseEntity<List<GetAllUserResponse>> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "2") int pageSize) {
		List<User> userPage = userService.getAllByPage(pageNo, pageSize);
		List<GetAllUserResponse> result = new ArrayList<>();

		userPage.forEach(user -> {
			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);

			response.setRole(userService.getUserRoles(user));
			result.add(response);
		});

		return ResponseEntity.ok(result);
	}
}
