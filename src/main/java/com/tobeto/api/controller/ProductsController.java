package com.tobeto.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.ProductService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessReponseDTO;
import com.tobeto.dto.product.CreateProductRequest;
import com.tobeto.dto.product.GetAllProductResponse;
import com.tobeto.dto.product.ProductWithCategoryDTO;
import com.tobeto.dto.product.UpdateProductRequest;
import com.tobeto.entities.concretes.Product;

@RestController
@RequestMapping("/api/v1/product")
public class ProductsController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapperService modelMapper;

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/create")
	public SuccessReponseDTO create(@RequestBody CreateProductRequest request) {
		Product product = modelMapper.forRequest().map(request, Product.class);
		productService.create(product);
		return new SuccessReponseDTO();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public ResponseEntity<List<GetAllProductResponse>> getAll() {
		List<Product> products = productService.getAll();
		List<GetAllProductResponse> result = new ArrayList<>();
		products.forEach(product -> {
			result.add(modelMapper.forResponse().map(product, GetAllProductResponse.class));
		});
		return ResponseEntity.ok(result);
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getProductWithCategoryDetails")
	public List<ProductWithCategoryDTO> getProductWithCategoryDetails() {
		return productService.getProductWithCategoryDetails();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/delete")
	public SuccessReponseDTO delete(@RequestBody UUID id) {
		productService.delete(id);
		return new SuccessReponseDTO();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@PutMapping("/update")
	public SuccessReponseDTO update(UpdateProductRequest request) {
		Product product = this.modelMapper.forRequest().map(request, Product.class);
		productService.update(product);
		return new SuccessReponseDTO();
	}
}
