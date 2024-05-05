package com.tobeto.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CustomerService;
import com.tobeto.business.rules.customer.CustomerBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.CustomerRepository;
import com.tobeto.entities.concretes.Customer;
import com.tobeto.entities.concretes.PageResponse;

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
		customerBusinessRules.checkIfEmailExists(customer.getContactEmail());
		return customerRepository.save(customer);
	}

	@Override
	public Customer update(Customer clientCustomer) {
		Customer customer = customerRepository.findById(clientCustomer.getId()).orElseThrow();
		customer.setCompanyName(clientCustomer.getCompanyName());
		customer.setContactName(clientCustomer.getContactName());
		customer.setContactEmail(clientCustomer.getContactEmail());
		customer.setContactPhone(clientCustomer.getContactPhone());
		customer.setAddress(clientCustomer.getAddress());
		return customerRepository.save(customer);
	}

	@Override
	public void delete(UUID id) {
		customerBusinessRules.checkIfByIdExists(id);
		customerRepository.deleteById(id);
	}

	@Override
	public PageResponse<Customer> getAll() {
		List<Customer> customers = customerRepository.findAll();
		int totalShelvesCount = customerRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, customers);
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

	@Override
	public PageResponse<Customer> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Customer> customers = customerRepository.findAll(pageable).getContent();
		int totalShelvesCount = customerRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, customers);
	}

	@Override
	public List<Customer> searchItem(String keyword) {
		return customerRepository.searchCustomer(keyword);
	}
}
