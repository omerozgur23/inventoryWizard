package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CategoryService;
import com.tobeto.business.rules.category.CategoryBusinessRules;
import com.tobeto.dataAccess.CategoryRepository;
import com.tobeto.entities.concretes.Category;

@Service
public class CategoryManager implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryBusinessRules categoryBusinessRules;

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Category create(Category category) {

		String formattedCategoryName = capitalizeFirstLetter(category.getCategoryName());

		categoryBusinessRules.checkIfCategoryNameExist(formattedCategoryName);
		category.setCategoryName(formattedCategoryName);
		return categoryRepository.save(category);
	}

	private String capitalizeFirstLetter(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Category update(Category clientCategory) {
		Category category = categoryRepository.findById(clientCategory.getId()).orElseThrow();

		category.setCategoryName(clientCategory.getCategoryName());
		return categoryRepository.save(category);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
		Category category = categoryRepository.findById(id).orElseThrow();
		categoryRepository.delete(category);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Category> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return categoryRepository.findAll(pageable).getContent();
	}
}
