package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tobeto.entities.concretes.Product;

import jakarta.persistence.criteria.Predicate;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

	boolean existsByProductName(String name);

	default List<Product> searchProducts(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";

				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("categoryName")),
						likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("supplier").get("companyName")),
						likeKeyword));

//				try {
//					int quantity = Integer.parseInt(keyword);
//					predicates.add(criteriaBuilder.equal(root.get("quantity"), quantity));
//				} catch (NumberFormatException e) {
			}

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		});
	}
}
