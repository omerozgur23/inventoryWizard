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

import com.tobeto.business.abstracts.SupplierService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.supplier.CreateSupplierRequest;
import com.tobeto.dto.supplier.GetAllSupplierResponse;
import com.tobeto.dto.supplier.UpdateSupplierRequest;
import com.tobeto.entities.concretes.PageResponse;
import com.tobeto.entities.concretes.Supplier;

@RestController
@RequestMapping("/api/v1/supplier")
public class SuppliersController {

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private ModelMapperService modelMapper;

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/create")
	public SuccessResponse create(@RequestBody CreateSupplierRequest request) {
		Supplier supplier = modelMapper.forRequest().map(request, Supplier.class);
		supplierService.create(supplier);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PutMapping("/update")
	public SuccessResponse update(@RequestBody UpdateSupplierRequest request) {
		Supplier supplier = modelMapper.forRequest().map(request, Supplier.class);
		supplierService.update(supplier);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/delete")
	public SuccessResponse delete(@RequestBody UUID id) {
		supplierService.delete(id);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public PageResponse<GetAllSupplierResponse> getAll() {
		PageResponse<Supplier> supplierPage = supplierService.getAll();
		List<GetAllSupplierResponse> responseList = supplierPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllSupplierResponse.class)).toList();
		return new PageResponse<>(supplierPage.getCount(), responseList);
	}

	@GetMapping("/getallByPage")
	public PageResponse<GetAllSupplierResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<Supplier> supplierPage = supplierService.getAllByPage(pageNo, pageSize);
		List<GetAllSupplierResponse> responseList = supplierPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllSupplierResponse.class)).toList();
		return new PageResponse<>(supplierPage.getCount(), responseList);
	}

	@GetMapping("/search")
	public List<GetAllSupplierResponse> searchSupplier(@RequestParam String keyword) {
		List<Supplier> suppliers = supplierService.searchItem(keyword);
		return suppliers.stream().map(supplier -> modelMapper.forResponse().map(supplier, GetAllSupplierResponse.class))
				.toList();
	}
}
