package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
		categoryBusinessRules.checkIfCategoryNameExist(category.getCategoryName());
		return categoryRepository.save(category);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Category update(Category category) {
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
}
