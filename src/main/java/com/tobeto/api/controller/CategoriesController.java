package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.CategoryService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.PaginationRequest;
import com.tobeto.dto.SearchRequest;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.category.CreateCategoryRequest;
import com.tobeto.dto.category.DeleteCategoryRequest;
import com.tobeto.dto.category.GetAllCategoryResponse;
import com.tobeto.dto.category.UpdateCategoryRequest;
import com.tobeto.entities.concretes.Category;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/category")
public class CategoriesController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@Valid @RequestBody CreateCategoryRequest request) {
		Category category = modelMapper.forRequest().map(request, Category.class);
		categoryService.create(category);
		return new SuccessResponse();
	}

	@PutMapping("/update")
	public SuccessResponse update(@Valid @RequestBody UpdateCategoryRequest request) {
		Category category = modelMapper.forRequest().map(request, Category.class);
		categoryService.update(category);
		return new SuccessResponse();
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@Valid @RequestBody DeleteCategoryRequest request) {
		categoryService.delete(request.getId());
		return new SuccessResponse();
	}

	@GetMapping("/getall")
	public PageResponse<GetAllCategoryResponse> getAll() {
		PageResponse<Category> categoryPage = categoryService.getAll();
		List<GetAllCategoryResponse> responseList = categoryPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllCategoryResponse.class)).toList();
		return new PageResponse<>(categoryPage.getCount(), responseList);
	}

	@PostMapping("/getallByPage")
	public PageResponse<GetAllCategoryResponse> getAllCategoryByPage(@Valid @RequestBody PaginationRequest request) {
		PageResponse<Category> categoryPage = categoryService.getAllByPage(request.getPageNo(), request.getPageSize());
		List<GetAllCategoryResponse> responseList = categoryPage.getData().stream()
				.map(category -> modelMapper.forResponse().map(category, GetAllCategoryResponse.class)).toList();

		return new PageResponse<>(categoryPage.getCount(), responseList);
	}

	@PostMapping("/search")
	public PageResponse<GetAllCategoryResponse> searchCategory(@RequestBody SearchRequest request) {
		PageResponse<Category> categoryPage = categoryService.searchItem(request.getKeyword());
		List<GetAllCategoryResponse> responseList = categoryPage.getData().stream()
				.map(category -> modelMapper.forResponse().map(category, GetAllCategoryResponse.class)).toList();
		return new PageResponse<GetAllCategoryResponse>(categoryPage.getCount(), responseList);
	}

}
