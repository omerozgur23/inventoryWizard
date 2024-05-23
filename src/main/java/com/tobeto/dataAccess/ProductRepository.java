package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.Product;
import com.tobeto.entities.enums.Status;

import jakarta.persistence.criteria.Predicate;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

	@Query("SELECT p FROM Product p WHERE p.status = Status.ACTIVE")
	List<Product> findAllActive();

	@Query("SELECT p FROM Product p WHERE p.status = Status.ACTIVE")
	Page<Product> findAllByPagination(Pageable pageable);

	boolean existsByProductName(String name);

	default List<Product> searchProducts(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("status"), Status.ACTIVE));

			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";

				List<Predicate> keywordPredicates = new ArrayList<>();
				keywordPredicates
						.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), likeKeyword));
				keywordPredicates.add(criteriaBuilder
						.like(criteriaBuilder.lower(root.get("category").get("categoryName")), likeKeyword));
				keywordPredicates.add(criteriaBuilder
						.like(criteriaBuilder.lower(root.get("supplier").get("companyName")), likeKeyword));
				predicates.add(criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0])));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
	}
}
