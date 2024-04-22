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
	public SuccessResponse create(@RequestBody CreateCategoryRequest reqeust) {
		Category category = modelMapper.forRequest().map(reqeust, Category.class);
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
	public List<GetAllCategoryResponse> getAll() {
		List<Category> categories = categoryService.getAll();
		return categories.stream()
				.map(category -> modelMapper.forResponse().map(category, GetAllCategoryResponse.class)).toList();
	}

	@GetMapping("/getallByPage")
	public List<GetAllCategoryResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "18") int pageSize) {
		List<Category> categoryPage = categoryService.getAllByPage(pageNo, pageSize);
		return categoryPage.stream()
				.map(product -> modelMapper.forResponse().map(product, GetAllCategoryResponse.class)).toList();
	}

}
