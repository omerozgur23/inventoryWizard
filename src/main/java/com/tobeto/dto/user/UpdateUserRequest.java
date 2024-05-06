package com.tobeto.dto.user;

import lombok.Data;

@Data
public class UpdateUserRequest {

	private String id;

	private String email;

	private String password;
}
