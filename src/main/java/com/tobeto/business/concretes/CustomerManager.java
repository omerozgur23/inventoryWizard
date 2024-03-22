package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CustomerService;
import com.tobeto.business.rules.customer.CustomerBusinessRules;
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
		customerBusinessRules.checkIfCompanyNameExists(customer.getCompanyName());
		customerBusinessRules.checkIfTaxNumberExists(customer.getTaxNumber());
		customerBusinessRules.checkIfEmailExists(customer.getEmail());
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

}
