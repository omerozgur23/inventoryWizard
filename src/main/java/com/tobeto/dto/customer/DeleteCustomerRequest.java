package com.tobeto.dto.customer;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteCustomerRequest {

	@NotNull(message = Messages.CUSTOMER_ID_CANNOT_BE_NULL)
	private UUID id;
}
