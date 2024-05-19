package com.tobeto.dto.user;

import java.util.List;

import com.tobeto.dto.RoleDTO;

import lombok.Data;

@Data
public class UpdateUserRequest {

	private String id;

	private String email;

	private String password;

	private List<RoleDTO> roles;
}
