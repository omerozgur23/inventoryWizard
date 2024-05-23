package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CustomerService;
import com.tobeto.business.rules.customer.CustomerBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.CustomerRepository;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.Customer;
import com.tobeto.entities.enums.Status;

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
		customer.setCreatedDate(LocalDateTime.now());
		customer.setStatus(Status.ACTIVE);
		return customerRepository.save(customer);
	}

	@Override
	public Customer update(Customer clientCustomer) {
		Customer customer = getCustomer(clientCustomer.getId());
		customerBusinessRules.checkIfTaxNumberExists(clientCustomer.getTaxNumber());
		BeanUtils.copyProperties(clientCustomer, customer, "taxNumber", "createdDate", "status");
		return customerRepository.save(customer);
	}

	@Override
	public void delete(UUID id) {
		Customer customer = getCustomer(id);
		customer.setStatus(Status.INACTIVE);
		customer.setInactiveDate(LocalDateTime.now());
		customerRepository.save(customer);
	}

	@Override
	public PageResponse<Customer> getAll() {
		List<Customer> customers = customerRepository.findAllActive();
		int totalCustomerCount = customerRepository.findAllActive().size();
		return new PageResponse<>(totalCustomerCount, customers);
	}

	@Override
	public PageResponse<Customer> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Customer> customers = customerRepository.findAllByPagination(pageable).getContent();
		int totalCustomerCount = customerRepository.findAllActive().size();
		return new PageResponse<>(totalCustomerCount, customers);
	}

	@Override
	public PageResponse<Customer> searchItem(String keyword) {
		List<Customer> customers = customerRepository.searchCustomer(keyword);
		int totalCustomerCount = customerRepository.searchCustomer(keyword).size();
		return new PageResponse<Customer>(totalCustomerCount, customers);
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
