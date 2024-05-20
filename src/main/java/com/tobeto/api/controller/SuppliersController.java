package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.SupplierService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.PaginationRequest;
import com.tobeto.dto.SearchRequest;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.supplier.CreateSupplierRequest;
import com.tobeto.dto.supplier.DeleteSupplierRequest;
import com.tobeto.dto.supplier.GetAllSupplierResponse;
import com.tobeto.dto.supplier.UpdateSupplierRequest;
import com.tobeto.entities.concretes.Supplier;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/supplier")
public class SuppliersController {

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@Valid @RequestBody CreateSupplierRequest request) {
		Supplier supplier = modelMapper.forRequest().map(request, Supplier.class);
		supplierService.create(supplier);
		return new SuccessResponse();
	}

	@PutMapping("/update")
	public SuccessResponse update(@Valid @RequestBody UpdateSupplierRequest request) {
		Supplier supplier = modelMapper.forRequest().map(request, Supplier.class);
		supplierService.update(supplier);
		return new SuccessResponse();
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@Valid @RequestBody DeleteSupplierRequest request) {
		supplierService.delete(request.getId());
		return new SuccessResponse();
	}

	@GetMapping("/getall")
	public PageResponse<GetAllSupplierResponse> getAll() {
		PageResponse<Supplier> supplierPage = supplierService.getAll();
		List<GetAllSupplierResponse> responseList = supplierPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllSupplierResponse.class)).toList();
		return new PageResponse<>(supplierPage.getCount(), responseList);
	}

	@PostMapping("/getallByPage")
	public PageResponse<GetAllSupplierResponse> getAllProductsByPage(@Valid @RequestBody PaginationRequest request) {
		PageResponse<Supplier> supplierPage = supplierService.getAllByPage(request.getPageNo(), request.getPageSize());
		List<GetAllSupplierResponse> responseList = supplierPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllSupplierResponse.class)).toList();
		return new PageResponse<>(supplierPage.getCount(), responseList);
	}

	@PostMapping("/search")
	public PageResponse<GetAllSupplierResponse> searchSupplier(@RequestBody SearchRequest request) {
		PageResponse<Supplier> supplierPage = supplierService.searchItem(request.getKeyword());
		List<GetAllSupplierResponse> responseList = supplierPage.getData().stream()
				.map(supplier -> modelMapper.forResponse().map(supplier, GetAllSupplierResponse.class)).toList();
		return new PageResponse<GetAllSupplierResponse>(supplierPage.getCount(), responseList);
	}
}
