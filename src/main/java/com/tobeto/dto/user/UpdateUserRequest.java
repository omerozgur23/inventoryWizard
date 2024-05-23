package com.tobeto.dto.user;

import java.util.List;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

	@NotNull(message = Messages.USER_ID_CANNOT_BE_NULL)
	private String id;

	@Email(message = Messages.EMAIL_MUST_BE_VALID)
	private String email;

	@Size(min = 6, max = 16, message = Messages.PASSWORD_MUST_BE_6_CHARACTERS)
	private String password;

	@NotNull(message = Messages.ROLES_LIST_CANNOT_BE_NULL)
	@NotEmpty(message = Messages.ROLES_LIST_CANNOT_BE_BLANK)
	private List<RoleDTO> roles;
}
