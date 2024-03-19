package com.tobeto.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entities.concretes.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
	boolean existsByCategoryName(String name);

	boolean existsById(UUID id);
}
