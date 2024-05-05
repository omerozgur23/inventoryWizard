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

import com.tobeto.business.abstracts.CategoryService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.category.CreateCategoryRequest;
import com.tobeto.dto.category.GetAllCategoryResponse;
import com.tobeto.dto.category.UpdateCategoryRequest;
import com.tobeto.entities.concretes.Category;
import com.tobeto.entities.concretes.PageResponse;

@RestController
@RequestMapping("api/v1/category")
public class CategoriesController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ModelMapperService modelMapper;

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/create")
	public SuccessResponse create(@RequestBody CreateCategoryRequest request) {
		Category category = modelMapper.forRequest().map(request, Category.class);
		categoryService.create(category);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PutMapping("/update")
	public SuccessResponse update(@RequestBody UpdateCategoryRequest request) {
		Category category = modelMapper.forRequest().map(request, Category.class);
		categoryService.update(category);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/delete")
	public SuccessResponse delete(@RequestBody UUID id) {
		categoryService.delete(id);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public PageResponse<GetAllCategoryResponse> getAll() {
		PageResponse<Category> categoryPage = categoryService.getAll();
		List<GetAllCategoryResponse> responseList = categoryPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllCategoryResponse.class)).toList();
		return new PageResponse<>(categoryPage.getCount(), responseList);
	}

	@GetMapping("/getallByPage")
	public PageResponse<GetAllCategoryResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<Category> categoryPage = categoryService.getAllByPage(pageNo, pageSize);
		List<GetAllCategoryResponse> responseList = categoryPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllCategoryResponse.class)).toList();

		return new PageResponse<>(categoryPage.getCount(), responseList);
	}

	@GetMapping("/search")
	public List<GetAllCategoryResponse> searchCategory(@RequestParam String keyword) {
		List<Category> categories = categoryService.searchItem(keyword);
		return categories.stream()
				.map(category -> modelMapper.forResponse().map(category, GetAllCategoryResponse.class)).toList();
	}

}
