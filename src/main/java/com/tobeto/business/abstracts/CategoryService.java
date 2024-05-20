package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.entities.concretes.Category;

public interface CategoryService extends BaseService<Category> {
	Category getCategory(UUID categoryId);
}
