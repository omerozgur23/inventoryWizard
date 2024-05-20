package com.tobeto.dto.shelf;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteShelfRequest {

	@NotNull(message = Messages.SHELF_ID_CANNOT_BE_NULL)
	private UUID id;
}
