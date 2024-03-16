package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CategoryService;
import com.tobeto.business.abstracts.ShelfService;
import com.tobeto.dataAccess.CategoryRepository;
import com.tobeto.entities.concretes.Category;
import com.tobeto.entities.concretes.Shelf;

@Service
public class CategoryManager implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

//	@Autowired
//	private CategoryRules categoryRules;

	@Autowired
	private ShelfService shelfService;

	@Override
	public void create(Category category) {
		if (!categoryRepository.existsByCategoryName(category.getCategoryName())) {
			categoryRepository.save(category);
			Shelf shelf = new Shelf(null, 0, 5, category);
			shelfService.create(shelf);
		}
//		categoryRules.existsByCategoryName(category.getCategoryName());
//		categoryRepository.save(category);
//		Shelf shelf = new Shelf(null, 0, 5, category);
//		shelfService.create(shelf);

	}

	@Override
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Override
	public void delete(UUID id) {
		Category category = categoryRepository.findById(id).orElseThrow();
		shelfService.delete(id);
		categoryRepository.delete(category);
	}

	@Override
	public void update(Category entity) {
		// TODO Auto-generated method stub
	}

}
