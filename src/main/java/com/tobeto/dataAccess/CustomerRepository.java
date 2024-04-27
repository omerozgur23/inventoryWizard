package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tobeto.entities.concretes.Customer;

import jakarta.persistence.criteria.Predicate;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

	boolean existsByCompanyName(String companyName);

	boolean existsByTaxNumber(String taxNumber);

	boolean existsByContactEmail(String email);

	default List<Customer> searchCustomer(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";

				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("companyName")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactName")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactEmail")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactPhone")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("taxNumber")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), likeKeyword));
			}

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		});
	}
}
