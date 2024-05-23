package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.Customer;
import com.tobeto.entities.enums.Status;

import jakarta.persistence.criteria.Predicate;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

	@Query("SELECT c FROM Customer c WHERE c.status = Status.ACTIVE")
	List<Customer> findAllActive();

	@Query("SELECT c FROM Customer c WHERE c.status = Status.ACTIVE")
	Page<Customer> findAllByPagination(Pageable pageable);

	boolean existsByCompanyName(String companyName);

	boolean existsByTaxNumber(String taxNumber);

	boolean existsByContactEmail(String email);

	default List<Customer> searchCustomer(String keyword) {
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
				keywordPredicates
						.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactPhone")), likeKeyword));
				keywordPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("taxNumber")), likeKeyword));
				keywordPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), likeKeyword));
				predicates.add(criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0])));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
	}
}
