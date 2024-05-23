package com.tobeto.dto.category;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteCategoryRequest {

	@NotNull(message = Messages.CATEGORY_ID_CANNOT_BE_NULL)
	private UUID id;
}
