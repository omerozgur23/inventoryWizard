package com.tobeto.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUserResponse {

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String role;

}
