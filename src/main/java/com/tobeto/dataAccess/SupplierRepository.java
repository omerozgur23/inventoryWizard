package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.Supplier;
import com.tobeto.entities.enums.Status;

import jakarta.persistence.criteria.Predicate;

public interface SupplierRepository extends JpaRepository<Supplier, UUID>, JpaSpecificationExecutor<Supplier> {

	@Query("SELECT s FROM Supplier s WHERE s.status = Status.ACTIVE")
	List<Supplier> findAllActive();

	@Query("SELECT s FROM Supplier s WHERE s.status = Status.ACTIVE")
	Page<Supplier> findAllByPagination(Pageable pageable);

	default List<Supplier> searchSupplier(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("status"), Status.ACTIVE));
			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";
				List<Predicate> keywordPredicates = new ArrayList<>();
				keywordPredicates
						.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("companyName")), likeKeyword));
				keywordPredicates
						.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactName")), likeKeyword));
				keywordPredicates
						.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactEmail")), likeKeyword));
				keywordPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("taxNumber")), likeKeyword));
				keywordPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), likeKeyword));
				predicates.add(criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0])));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
	}
}
