package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CategoryService;
import com.tobeto.business.rules.category.CategoryBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.CategoryRepository;
import com.tobeto.entities.concretes.Category;
import com.tobeto.entities.concretes.PageResponse;

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
		Category category = categoryRepository.findById(clientCategory.getId())
				.orElseThrow(() -> new BusinessException(Messages.CATEGORY_ID_NOT_FOUND));

		String formattedCategoryName = capitalizeFirstLetter(clientCategory.getCategoryName());

		category.setCategoryName(formattedCategoryName);
		return categoryRepository.save(category);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new BusinessException(Messages.CATEGORY_ID_NOT_FOUND));
		categoryRepository.delete(category);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public PageResponse<Category> getAll() {
		List<Category> categories = categoryRepository.findAll();
		int totalShelvesCount = categoryRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, categories);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public PageResponse<Category> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Category> categories = categoryRepository.findAll(pageable).getContent();
		int totalShelvesCount = categoryRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, categories);
	}

	@Override
	public List<Category> searchItem(String keyword) {
		return categoryRepository.searchCategories(keyword);
	}
}
