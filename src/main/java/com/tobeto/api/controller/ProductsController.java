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

import com.tobeto.business.abstracts.ProductService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.product.AcceptProductRequest;
import com.tobeto.dto.product.CreateProductRequest;
import com.tobeto.dto.product.GetAllProductResponse;
import com.tobeto.dto.product.SaleProductRequest;
import com.tobeto.dto.product.UpdateProductRequest;
import com.tobeto.entities.concretes.PageResponse;
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
	@PostMapping("/create")
	public SuccessResponse create(@RequestBody CreateProductRequest request) {
		Product product = modelMapper.forRequest().map(request, Product.class);
		productService.create(product);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PutMapping("/update")
	public SuccessResponse update(@RequestBody UpdateProductRequest request) {
		Product product = modelMapper.forRequest().map(request, Product.class);
		productService.update(product);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/delete")
	public SuccessResponse delete(@RequestBody UUID id) {
		productService.delete(id);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public PageResponse<GetAllProductResponse> getAll() {
		PageResponse<Product> productPage = productService.getAll();
		List<GetAllProductResponse> responseList = productPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllProductResponse.class)).toList();
		return new PageResponse<>(productPage.getCount(), responseList);
	}

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/accept")
	public SuccessResponse acceptFruit(@RequestBody AcceptProductRequest request) {
		productService.acceptProduct(request.getProductId(), request.getCount());
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/sale")
	public SuccessResponse saleProduct(@RequestBody SaleProductRequest request) {
		productService.saleProduct(request.getProductItems(), request.getCustomerId(), request.getUserId());
		return new SuccessResponse();
	}

	@GetMapping("/getallByPage")
	public PageResponse<GetAllProductResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<Product> productPage = productService.getAllByPage(pageNo, pageSize);
		List<GetAllProductResponse> responseList = productPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllProductResponse.class)).toList();
		return new PageResponse<>(productPage.getCount(), responseList);
	}

	@GetMapping("/search")
	public List<GetAllProductResponse> searchProducts(@RequestParam String keyword) {
		List<Product> products = productService.searchItem(keyword);
		return products.stream().map(product -> modelMapper.forResponse().map(product, GetAllProductResponse.class))
				.toList();
	}
}
