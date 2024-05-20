package com.tobeto.dto.user;

import java.util.List;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

	@NotNull(message = Messages.FIRST_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.FIRST_NAME_CANNOT_BE_BLANK)
	private String firstName;

	@NotNull(message = Messages.LAST_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.LAST_NAME_CANNOT_BE_BLANK)
	private String lastName;

	@Email(message = Messages.EMAIL_MUST_BE_VALID)
	private String email;

	@Size(min = 6, max = 16, message = Messages.PASSWORD_MUST_BE_6_CHARACTERS)
//	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must contain at least one letter and one digit")
	private String password;

	@NotNull(message = Messages.ROLES_LIST_CANNOT_BE_NULL)
	@NotEmpty(message = Messages.ROLES_LIST_CANNOT_BE_BLANK)
	private List<RoleDTO> roles;

}
