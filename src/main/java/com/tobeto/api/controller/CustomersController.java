package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.CustomerService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.PaginationRequest;
import com.tobeto.dto.SearchRequest;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.customer.CreateCustomerRequest;
import com.tobeto.dto.customer.DeleteCustomerRequest;
import com.tobeto.dto.customer.GetAllCustomerResponse;
import com.tobeto.dto.customer.UpdateCustomerRequest;
import com.tobeto.entities.concretes.Customer;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomersController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@Valid @RequestBody CreateCustomerRequest request) {
		Customer customer = modelMapper.forRequest().map(request, Customer.class);
		customerService.create(customer);
		return new SuccessResponse();
	}

	@PutMapping("/update")
	public SuccessResponse update(@Valid @RequestBody UpdateCustomerRequest request) {
		Customer customer = modelMapper.forRequest().map(request, Customer.class);
		customerService.update(customer);
		return new SuccessResponse();
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@Valid @RequestBody DeleteCustomerRequest request) {
		customerService.delete(request.getId());
		return new SuccessResponse();
	}

	@GetMapping("getall")
	public PageResponse<GetAllCustomerResponse> getAll() {
		PageResponse<Customer> customerPage = customerService.getAll();
		List<GetAllCustomerResponse> responseList = customerPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllCustomerResponse.class)).toList();
		return new PageResponse<>(customerPage.getCount(), responseList);
	}

	@PostMapping("/getallByPage")
	public PageResponse<GetAllCustomerResponse> getAllCustomerByPage(@Valid @RequestBody PaginationRequest request) {
		PageResponse<Customer> customerPage = customerService.getAllByPage(request.getPageNo(), request.getPageSize());
		List<GetAllCustomerResponse> responseList = customerPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllCustomerResponse.class)).toList();
		return new PageResponse<>(customerPage.getCount(), responseList);
	}

	@PostMapping("/search")
	public PageResponse<GetAllCustomerResponse> searchCustomer(@RequestBody SearchRequest request) {
		PageResponse<Customer> customerPage = customerService.searchItem(request.getKeyword());
		List<GetAllCustomerResponse> responseList = customerPage.getData().stream()
				.map(customer -> modelMapper.forResponse().map(customer, GetAllCustomerResponse.class)).toList();
		return new PageResponse<GetAllCustomerResponse>(customerPage.getCount(), responseList);
	}
}
