package com.tobeto.dto.user;

import lombok.Data;

@Data
public class GetAllUserResponse {

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String role;
}
