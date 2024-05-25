package com.tobeto.dto.user;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangeRequest {

	@Size(min = 6, max = 16, message = Messages.PASSWORD_MUST_BE_6_CHARACTERS)
	private String lastPassword;

	@Size(min = 6, max = 16, message = Messages.PASSWORD_MUST_BE_6_CHARACTERS)
	private String newPassword;
}
