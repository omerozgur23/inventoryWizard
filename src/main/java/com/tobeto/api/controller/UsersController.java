package com.tobeto.api.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.UserService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.role.GetAllRoleResponse;
import com.tobeto.dto.user.CreateUserRequest;
import com.tobeto.dto.user.GetAllUserResponse;
import com.tobeto.dto.user.PasswordChangeRequest;
import com.tobeto.dto.user.UpdateUserRequest;
import com.tobeto.entities.concretes.PageResponse;
import com.tobeto.entities.concretes.Roles;
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
	public SuccessResponse create(@RequestBody CreateUserRequest request) {
		User user = modelMapper.forRequest().map(request, User.class);
		userService.create(user);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PutMapping("/update")
	private SuccessResponse update(@RequestBody UpdateUserRequest request) {
		User user = modelMapper.forRequest().map(request, User.class);
		userService.update(user);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/delete")
	public SuccessResponse delete(@RequestBody UUID id) {
		userService.delete(id);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public ResponseEntity<PageResponse<GetAllUserResponse>> getAll() {
//		List<User> users = userService.getAll();
//		List<GetAllUserResponse> result = new ArrayList<>();
//		users.forEach(user -> {
//			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);
//
//			response.setRole(userService.getUserRoles(user));
//			result.add(response);
//		});
//		return ResponseEntity.ok(result);
		PageResponse<User> userPage = userService.getAll();
		List<GetAllUserResponse> result = new ArrayList<>();

		userPage.getData().forEach(user -> {
			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);

			response.setRole(userService.getUserRoles(user));
			result.add(response);
		});
		return ResponseEntity.ok(new PageResponse<>(userPage.getCount(), result));
	}

	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getallByPage")
	public ResponseEntity<PageResponse<GetAllUserResponse>> getAllProductsByPage(
			@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "18") int pageSize) {
		PageResponse<User> userPage = userService.getAllByPage(pageNo, pageSize);

		List<GetAllUserResponse> result = new ArrayList<>();

		userPage.getData().forEach(user -> {
			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);

			response.setRole(userService.getUserRoles(user));
			result.add(response);
		});
		return ResponseEntity.ok(new PageResponse<>(userPage.getCount(), result));
	}

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/changePassword")
	public SuccessResponse sifreDegistir(@RequestBody PasswordChangeRequest request, Principal principal) {
		userService.changePassword(request.getLastPassword(), request.getNewPassword(), principal.getName());
		return new SuccessResponse();
	}

	@GetMapping("/search")
	public List<GetAllUserResponse> searchUser(@RequestParam String keyword) {
		List<User> users = userService.searchItem(keyword);
		List<GetAllUserResponse> result = new ArrayList<>();

		users.forEach(user -> {
			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);

			response.setRole(userService.getUserRoles(user));
			result.add(response);
		});
		return result;
	}

	@GetMapping("roles")
	public List<GetAllRoleResponse> getAllRoles() {
		List<Roles> roles = userService.getAllRoles();
		return roles.stream().map(role -> modelMapper.forResponse().map(role, GetAllRoleResponse.class)).toList();
	}
}
