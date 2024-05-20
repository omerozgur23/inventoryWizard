package com.tobeto.dto.supplier;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSupplierRequest {

	@NotNull(message = Messages.SUPPLIER_COMPANY_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_COMPANY_NAME_CANNOT_BE_BLANK)
	private String companyName;

	@NotNull(message = Messages.SUPPLIER_CONTACT_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_CONTACT_NAME_CANNOT_BE_BLANK)
	private String contactName;

	@NotNull(message = Messages.SUPPLIER_CONTACT_EMAIL_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_CONTACT_EMAIL_CANNOT_BE_BLANK)
	private String contactEmail;

	@NotNull(message = Messages.SUPPLIER_CONTACT_PHONE_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_CONTACT_PHONE_CANNOT_BE_BLANK)
	private String contactPhone;

	@NotNull(message = Messages.SUPPLIER_TAX_NUMBER_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_TAX_NUMBER_CANNOT_BE_BLANK)
	private String taxNumber;

	@NotNull(message = Messages.SUPPLIER_ADDRESS_CANNOT_BE_NULL)
	@NotBlank(message = Messages.SUPPLIER_ADDRESS_CANNOT_BE_BLANK)
	private String address;
}
