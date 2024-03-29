package com.tobeto.dto.user;

import java.util.UUID;

import lombok.Data;

@Data
public class GetAllUserResponse {

	private UUID id;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String role;
}
