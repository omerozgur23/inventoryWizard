package com.tobeto.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.SupplierService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessReponseDTO;
import com.tobeto.dto.supplier.CreateSupplierRequest;
import com.tobeto.dto.supplier.GetAllSupplierResponse;
import com.tobeto.dto.supplier.UpdateSupplierRequest;
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
	/**********************************************************************/
	@PostMapping("/create")
	public SuccessReponseDTO create(@RequestBody CreateSupplierRequest request) {
		Supplier supplier = modelMapper.forRequest().map(request, Supplier.class);
		supplierService.create(supplier);
		return new SuccessReponseDTO();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
//	@GetMapping("/getall")
//	public ResponseEntity<List<GetAllSupplierResponse>> getAll() {
//		List<Supplier> suppliers = supplierService.getAll();
//		List<GetAllSupplierResponse> result = new ArrayList<>();
//		suppliers.forEach(supplier -> {
//			result.add(modelMapper.forResponse().map(supplier, GetAllSupplierResponse.class));
//		});
//		return ResponseEntity.ok(result);
//	}
	@GetMapping("/getall")
	public List<GetAllSupplierResponse> getAll() {
		List<Supplier> suppliers = supplierService.getAll();
		return suppliers.stream().map(supplier -> modelMapper.forResponse().map(supplier, GetAllSupplierResponse.class))
				.toList();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/delete")
	public SuccessReponseDTO delete(@RequestBody UUID id) {
		supplierService.delete(id);
		return new SuccessReponseDTO();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@PutMapping("/update")
	public SuccessReponseDTO update(@RequestBody UpdateSupplierRequest request) {
		Supplier supplier = modelMapper.forRequest().map(request, Supplier.class);
		supplierService.update(supplier);
		return new SuccessReponseDTO();
	}
}
