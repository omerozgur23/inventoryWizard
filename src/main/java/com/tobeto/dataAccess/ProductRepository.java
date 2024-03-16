package com.tobeto.dataAccess;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.dto.product.ProductWithCategoryDTO;
import com.tobeto.entities.concretes.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
	boolean existsByProductName(String name);

	@Query("Select new com.tobeto.dto.product.ProductWithCategoryDTO(p.id, p.productName, c.categoryName) From Category c Inner Join c.products p")
	List<ProductWithCategoryDTO> getProductWithCategoryDetails();
}
