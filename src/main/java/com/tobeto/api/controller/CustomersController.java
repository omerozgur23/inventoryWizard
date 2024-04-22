package com.tobeto.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.CustomerService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.customer.CreateCustomerRequest;
import com.tobeto.dto.customer.GetAllCustomerResponse;
import com.tobeto.dto.customer.UpdateCustomerRequest;
import com.tobeto.entities.concretes.Customer;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomersController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@RequestBody CreateCustomerRequest request) {
		Customer customer = modelMapper.forRequest().map(request, Customer.class);
		customerService.create(customer);
		return new SuccessResponse();
	}

	@PutMapping("/update")
	public SuccessResponse update(@RequestBody UpdateCustomerRequest request) {
		Customer customer = modelMapper.forRequest().map(request, Customer.class);
		customerService.update(customer);
		return new SuccessResponse();
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@RequestBody UUID id) {
		customerService.delete(id);
		return new SuccessResponse();
	}

	@GetMapping("getall")
	public List<GetAllCustomerResponse> getAll() {
		List<Customer> customers = customerService.getAll();
		return customers.stream().map(customer -> modelMapper.forResponse().map(customer, GetAllCustomerResponse.class))
				.toList();
	}

	@GetMapping("/getallByPage")
	public List<GetAllCustomerResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "18") int pageSize) {
		List<Customer> customerPage = customerService.getAllByPage(pageNo, pageSize);
		return customerPage.stream()
				.map(product -> modelMapper.forResponse().map(product, GetAllCustomerResponse.class)).toList();
	}

}
