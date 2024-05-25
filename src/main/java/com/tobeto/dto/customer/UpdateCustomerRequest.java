package com.tobeto.dto.customer;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCustomerRequest {

	@NotNull(message = Messages.CUSTOMER_ID_CANNOT_BE_NULL)
	private UUID id;

	@NotNull(message = Messages.CUSTOMER_COMPANY_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_COMPANY_NAME_CANNOT_BE_BLANK)
	private String companyName;

	@NotNull(message = Messages.CUSTOMER_CONTACT_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_CONTACT_NAME_CANNOT_BE_BLANK)
	private String contactName;

	@NotNull(message = Messages.CUSTOMER_CONTACT_EMAIL_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_CONTACT_EMAIL_CANNOT_BE_BLANK)
	@Email(message = Messages.EMAIL_MUST_BE_VALID)
	private String contactEmail;

	@NotNull(message = Messages.CUSTOMER_CONTACT_PHONE_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_CONTACT_PHONE_CANNOT_BE_BLANK)
	private String contactPhone;

	@NotNull(message = Messages.CUSTOMER_ADDRESS_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_ADDRESS_CANNOT_BE_BLANK)
	private String address;
}
