package com.tobeto.business.rules.customer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.CustomerRepository;

@Service
public class CustomerBusinessRules {

	@Autowired
	private CustomerRepository customerRepository;

	public void checkIfCompanyNameExists(String companyName) {
		if (customerRepository.existsByCompanyName(companyName)) {
			throw new BusinessException(Messages.COMPANY_NAME_ALREADY_EXISTS);
		}
	}

	public void checkIfTaxNumberExists(String taxNumber) {
		if (customerRepository.existsByTaxNumber(taxNumber)) {
			throw new BusinessException(Messages.TAX_NUMBER_ALREADY_EXISTS);
		}
	}

	public void checkIfEmailExists(String email) {
		if (customerRepository.existsByContactEmail(email)) {
			throw new BusinessException(Messages.EMAIL_ALREADY_EXISTS);
		}
	}

	public void checkIfByIdExists(UUID id) {
		if (!customerRepository.existsById(id)) {
			throw new BusinessException(Messages.CUSTOMER_ID_NOT_FOUND);
		}
	}
}
