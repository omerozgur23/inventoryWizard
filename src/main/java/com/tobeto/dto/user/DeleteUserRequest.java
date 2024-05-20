package com.tobeto.dto.user;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteUserRequest {

	@NotNull(message = Messages.USER_ID_CANNOT_BE_NULL)
	private UUID id;
}
