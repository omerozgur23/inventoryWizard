package com.tobeto.dto.category;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCategoryRequest {

	@NotNull(message = Messages.CATEGORY_ID_CANNOT_BE_NULL)
	private UUID id;

	@NotNull(message = Messages.CATEGORY_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CATEGORY_NAME_CANNOT_BE_BLANK)
	@Size(min = 2, message = Messages.CATEGORY_NAME_TOO_SHORT)
	private String categoryName;
}
