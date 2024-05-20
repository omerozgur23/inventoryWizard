package com.tobeto.dto.supplier;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSupplierRequest {

	@NotNull(message = Messages.SUPPLIER_ID_CANNOT_BE_NULL)
	private UUID id;

	@NotNull(message = Messages.SUPPLIER_COMPANY_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_COMPANY_NAME_CANNOT_BE_BLANK)
	private String companyName;

	@NotNull(message = Messages.SUPPLIER_CONTACT_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_CONTACT_NAME_CANNOT_BE_BLANK)
	private String contactName;

	@NotNull(message = Messages.SUPPLIER_CONTACT_EMAIL_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_CONTACT_EMAIL_CANNOT_BE_BLANK)
	@Email(message = Messages.EMAIL_MUST_BE_VALID)
	private String contactEmail;

	@NotNull(message = Messages.SUPPLIER_CONTACT_PHONE_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_CONTACT_PHONE_CANNOT_BE_BLANK)
	private String contactPhone;

	@NotNull(message = Messages.SUPPLIER_ADDRESS_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_ADDRESS_CANNOT_BE_BLANK)
	private String address;
}
