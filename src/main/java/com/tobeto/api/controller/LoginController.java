package com.tobeto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.LoginService;
import com.tobeto.dto.login.LoginRequest;
import com.tobeto.dto.login.LoginResponse;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest dto) {
		String token = loginService.login(dto.getEmail(), dto.getPassword());
		return new LoginResponse(token);
	}
}
