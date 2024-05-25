package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CategoryService;
import com.tobeto.business.rules.category.CategoryBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.CategoryRepository;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.Category;
import com.tobeto.entities.enums.Status;

@Service
public class CategoryManager implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryBusinessRules categoryBusinessRules;

	@Override
	public Category create(Category category) {
		category.setCategoryName(category.getCategoryName());
		category.setCreatedDate(LocalDateTime.now());
		category.setStatus(Status.ACTIVE);
		categoryBusinessRules.checkIfCategoryNameExist(category.getCategoryName());
		return categoryRepository.save(category);
	}

	@Override
	public Category update(Category clientCategory) {
		Category category = getCategory(clientCategory.getId());
		categoryBusinessRules.checkIfCategoryNameExist(clientCategory.getCategoryName());
		BeanUtils.copyProperties(clientCategory, category, "createdDate", "status");
		return categoryRepository.save(category);
	}

	@Override
	public void delete(UUID id) {
		Category category = getCategory(id);
		category.setStatus(Status.INACTIVE);
		category.setInactiveDate(LocalDateTime.now());
		categoryRepository.save(category);
	}

	@Override
	public PageResponse<Category> getAll() {
		List<Category> categories = categoryRepository.findAllActive();
		int totalCategoriesCount = categoryRepository.findAllActive().size();
		return new PageResponse<>(totalCategoriesCount, categories);
	}

	@Override
	public PageResponse<Category> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Category> categoryPage = categoryRepository.findAllByPagination(pageable).getContent();
		int totalCategoryCount = categoryRepository.findAllActive().size();
		return new PageResponse<>(totalCategoryCount, categoryPage);
	}

	@Override
	public PageResponse<Category> searchItem(String keyword) {
		List<Category> categories = categoryRepository.searchCategories(keyword);
		int totalCategoryCount = categoryRepository.searchCategories(keyword).size();
		return new PageResponse<Category>(totalCategoryCount, categories);
	}

	@Override
	public Category getCategory(UUID categoryId) {
		Optional<Category> oCategory = categoryRepository.findById(categoryId);
		Category category = null;
		if (oCategory.isPresent()) {
			category = oCategory.get();
		} else {
			throw new BusinessException(Messages.CATEGORY_ID_NOT_FOUND);
		}
		return category;
	}
}
