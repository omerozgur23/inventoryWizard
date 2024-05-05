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
import com.tobeto.entities.concretes.PageResponse;

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
	public PageResponse<GetAllCustomerResponse> getAll() {
		PageResponse<Customer> customerPage = customerService.getAll();
		List<GetAllCustomerResponse> responseList = customerPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllCustomerResponse.class)).toList();
		return new PageResponse<>(customerPage.getCount(), responseList);
	}

	@GetMapping("/getallByPage")
	public PageResponse<GetAllCustomerResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<Customer> customerPage = customerService.getAllByPage(pageNo, pageSize);
		List<GetAllCustomerResponse> responseList = customerPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllCustomerResponse.class)).toList();
		return new PageResponse<>(customerPage.getCount(), responseList);
	}

	@GetMapping("/search")
	public List<GetAllCustomerResponse> searchCustomer(@RequestParam String keyword) {
		List<Customer> customers = customerService.searchItem(keyword);
		return customers.stream().map(customer -> modelMapper.forResponse().map(customer, GetAllCustomerResponse.class))
				.toList();
	}
}
