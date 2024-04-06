package com.tobeto.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CustomerService;
import com.tobeto.business.rules.customer.CustomerBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.CustomerRepository;
import com.tobeto.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerBusinessRules customerBusinessRules;

	@Override
	public Customer create(Customer customer) {
		customerBusinessRules.checkIfCompanyNameExists(customer.getCompanyName());
		customerBusinessRules.checkIfTaxNumberExists(customer.getTaxNumber());
		customerBusinessRules.checkIfEmailExists(customer.getEmail());
		return customerRepository.save(customer);
	}

	@Override
	public Customer update(Customer customer) {
		Customer newCustomer = customerRepository.findById(customer.getId()).orElseThrow();
		customer.setCompanyName(newCustomer.getCompanyName());
		customer.setTaxNumber(newCustomer.getTaxNumber());
		return customerRepository.save(customer);
	}

	@Override
	public void delete(UUID id) {
		customerBusinessRules.checkIfByIdExists(id);
		customerRepository.deleteById(id);
	}

	@Override
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomer(UUID customerId) {
		Optional<Customer> oCustomer = customerRepository.findById(customerId);
		Customer customer = null;
		if (oCustomer.isPresent()) {
			customer = oCustomer.get();
		} else {
			throw new BusinessException(Messages.CUSTOMER_ID_NOT_FOUND);
		}
		return customer;
	}
}
