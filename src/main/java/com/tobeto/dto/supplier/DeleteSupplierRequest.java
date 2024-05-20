package com.tobeto.dto.supplier;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteSupplierRequest {

	@NotNull(message = Messages.SUPPLIER_ID_CANNOT_BE_NULL)
	private UUID id;
}
