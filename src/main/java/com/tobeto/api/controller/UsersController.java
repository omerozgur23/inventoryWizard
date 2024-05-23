package com.tobeto.api.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.UserService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.PaginationRequest;
import com.tobeto.dto.SearchRequest;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.role.GetAllRoleResponse;
import com.tobeto.dto.user.CreateUserRequest;
import com.tobeto.dto.user.DeleteUserRequest;
import com.tobeto.dto.user.GetAllUserResponse;
import com.tobeto.dto.user.PasswordChangeRequest;
import com.tobeto.dto.user.UpdateUserRequest;
import com.tobeto.entities.concretes.Roles;
import com.tobeto.entities.concretes.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@Valid @RequestBody CreateUserRequest request) {
		User user = modelMapper.forRequest().map(request, User.class);
		userService.create(user);
		return new SuccessResponse();
	}

	@PutMapping("/update")
	private SuccessResponse update(@Valid @RequestBody UpdateUserRequest request) {
		User user = modelMapper.forRequest().map(request, User.class);
		userService.update(user);
		return new SuccessResponse();
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@Valid @RequestBody DeleteUserRequest request) {
		userService.delete(request.getId());
		return new SuccessResponse();
	}

	@GetMapping("/getall")
	public PageResponse<GetAllUserResponse> getAll() {
		PageResponse<User> userPage = userService.getAll();
		List<GetAllUserResponse> responseList = new ArrayList<>();

		userPage.getData().forEach(user -> {
			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);

			response.setRole(userService.getUserRoles(user));
			responseList.add(response);
		});
		return new PageResponse<>(userPage.getCount(), responseList);
	}

	@PostMapping("/getallByPage")
	public PageResponse<GetAllUserResponse> getAllProductsByPage(@Valid @RequestBody PaginationRequest request) {
		PageResponse<User> userPage = userService.getAllByPage(request.getPageNo(), request.getPageSize());
		List<GetAllUserResponse> responseList = new ArrayList<>();

		userPage.getData().forEach(user -> {
			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);
			response.setRole(userService.getUserRoles(user));
			responseList.add(response);
		});
		return new PageResponse<>(userPage.getCount(), responseList);
	}

	@PostMapping("/changePassword")
	public SuccessResponse sifreDegistir(@RequestBody PasswordChangeRequest request, Principal principal) {
		userService.changePassword(request.getLastPassword(), request.getNewPassword(), principal.getName());
		return new SuccessResponse();
	}

	@PostMapping("/search")
	public PageResponse<GetAllUserResponse> searchUser(@RequestBody SearchRequest request) {
		PageResponse<User> userPage = userService.searchItem(request.getKeyword());
		List<GetAllUserResponse> responseList = new ArrayList<>();

		userPage.getData().forEach(user -> {
			GetAllUserResponse response = modelMapper.forResponse().map(user, GetAllUserResponse.class);
			response.setRole(userService.getUserRoles(user));
			responseList.add(response);
		});
		return new PageResponse<GetAllUserResponse>(userPage.getCount(), responseList);
	}

	@GetMapping("roles")
	public List<GetAllRoleResponse> getAllRoles() {
		List<Roles> roles = userService.getAllRoles();
		return roles.stream().map(role -> modelMapper.forResponse().map(role, GetAllRoleResponse.class)).toList();
	}
}
