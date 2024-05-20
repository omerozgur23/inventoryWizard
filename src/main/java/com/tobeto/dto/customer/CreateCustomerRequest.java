package com.tobeto.dto.customer;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerRequest {

	@NotNull(message = Messages.CUSTOMER_COMPANY_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_COMPANY_NAME_CANNOT_BE_BLANK)
	private String companyName;

	@NotNull(message = Messages.CUSTOMER_CONTACT_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_CONTACT_NAME_CANNOT_BE_BLANK)
	private String contactName;

	@NotNull(message = Messages.CUSTOMER_CONTACT_EMAIL_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_CONTACT_EMAIL_CANNOT_BE_BLANK)
	private String contactEmail;

	@NotNull(message = Messages.CUSTOMER_CONTACT_PHONE_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_CONTACT_PHONE_CANNOT_BE_BLANK)
	private String contactPhone;

	@NotNull(message = Messages.CUSTOMER_TAX_NUMBER_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_TAX_NUMBER_CANNOT_BE_BLANK)
	private String taxNumber;

	@NotNull(message = Messages.CUSTOMER_ADDRESS_CANNOT_BE_NULL)
	@NotBlank(message = Messages.CUSTOMER_ADDRESS_CANNOT_BE_BLANK)
	private String address;
}
