package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.ProductService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.PaginationRequest;
import com.tobeto.dto.SearchRequest;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.product.AcceptProductRequest;
import com.tobeto.dto.product.CreateProductRequest;
import com.tobeto.dto.product.DeleteProductRequest;
import com.tobeto.dto.product.GetAllProductResponse;
import com.tobeto.dto.product.SaleProductRequest;
import com.tobeto.dto.product.UpdateProductRequest;
import com.tobeto.entities.concretes.Product;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/product")
public class ProductsController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@Valid @RequestBody CreateProductRequest request) {
		Product product = modelMapper.forRequest().map(request, Product.class);
		productService.create(product);
		return new SuccessResponse();
	}

	@PutMapping("/update")
	public SuccessResponse update(@Valid @RequestBody UpdateProductRequest request) {
		Product product = modelMapper.forRequest().map(request, Product.class);
		productService.update(product);
		return new SuccessResponse();
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@Valid @RequestBody DeleteProductRequest request) {
		productService.delete(request.getId());
		return new SuccessResponse();
	}

	@GetMapping("/getall")
	public PageResponse<GetAllProductResponse> getAll() {
		PageResponse<Product> productPage = productService.getAll();
		List<GetAllProductResponse> responseList = productPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllProductResponse.class)).toList();
		return new PageResponse<>(productPage.getCount(), responseList);
	}

	@PostMapping("/accept")
	public SuccessResponse acceptFruit(@Valid @RequestBody AcceptProductRequest request) {
		productService.acceptProduct(request.getProductId(), request.getCount());
		return new SuccessResponse();
	}

	@PostMapping("/sale")
	public SuccessResponse saleProduct(@Valid @RequestBody SaleProductRequest request) {
		productService.saleProduct(request.getProductItems(), request.getCustomerId(), request.getUserId());
		return new SuccessResponse();
	}

	@PostMapping("/getallByPage")
	public PageResponse<GetAllProductResponse> getAllProductsByPage(@Valid @RequestBody PaginationRequest request) {
		PageResponse<Product> productPage = productService.getAllByPage(request.getPageNo(), request.getPageSize());
		List<GetAllProductResponse> responseList = productPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllProductResponse.class)).toList();
		return new PageResponse<>(productPage.getCount(), responseList);
	}

	@PostMapping("/search")
	public PageResponse<GetAllProductResponse> searchProducts(@RequestBody SearchRequest request) {
		PageResponse<Product> productPage = productService.searchItem(request.getKeyword());
		List<GetAllProductResponse> responseList = productPage.getData().stream()
				.map(product -> modelMapper.forResponse().map(product, GetAllProductResponse.class)).toList();
		return new PageResponse<GetAllProductResponse>(productPage.getCount(), responseList);
	}
}
