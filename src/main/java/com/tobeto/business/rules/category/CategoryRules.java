package com.tobeto.business.rules.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.dataAccess.CategoryRepository;

@Service
public class CategoryRules {

	@Autowired
	private CategoryRepository categoryRepository;

	public boolean existsByCategoryName(String name) {
		if (!categoryRepository.existsByCategoryName(name)) {
			return true;
		} else {
			return false;
		}

	}
}
