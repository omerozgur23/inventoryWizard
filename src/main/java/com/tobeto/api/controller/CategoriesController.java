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

import com.tobeto.business.abstracts.CategoryService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessReponseDTO;
import com.tobeto.dto.category.CreateCategoryRequest;
import com.tobeto.dto.category.GetAllCategoryResponse;
import com.tobeto.dto.category.UpdateCategoryRequest;
import com.tobeto.entities.concretes.Category;

@RestController
@RequestMapping("api/v1/category")
public class CategoriesController {

	// test

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ModelMapperService modelMapper;

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/create")
	public SuccessReponseDTO create(@RequestBody CreateCategoryRequest reqeust) {
		Category category = modelMapper.forRequest().map(reqeust, Category.class);
		categoryService.create(category);
		return new SuccessReponseDTO();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public ResponseEntity<List<GetAllCategoryResponse>> getAll() {
		List<Category> categories = categoryService.getAll();
		List<GetAllCategoryResponse> result = new ArrayList<>();
		categories.forEach(category -> {
			result.add(modelMapper.forResponse().map(category, GetAllCategoryResponse.class));
		});
		return ResponseEntity.ok(result);
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/delete")
	public SuccessReponseDTO delete(@RequestBody UUID id) {
		categoryService.delete(id);
		return new SuccessReponseDTO();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@PutMapping("/update")
	public SuccessReponseDTO update(@RequestBody UpdateCategoryRequest request) {
		Category category = this.modelMapper.forRequest().map(request, Category.class);
		categoryService.update(category);
		return new SuccessReponseDTO();
	}

}
