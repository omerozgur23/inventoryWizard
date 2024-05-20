package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.Category;
import com.tobeto.entities.enums.Status;

import jakarta.persistence.criteria.Predicate;

public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {

	@Query("SELECT c FROM Category c WHERE c.status = Status.ACTIVE")
	List<Category> findAll();

	boolean existsByCategoryName(String name);

	boolean existsById(UUID id);

	default List<Category> searchCategories(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("status"), Status.ACTIVE));
			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";
				List<Predicate> keywordPredicates = new ArrayList<>();
				keywordPredicates
						.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("categoryName")), likeKeyword));
				predicates.add(criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0])));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
	}
}
