package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tobeto.entities.concretes.Order;

import jakarta.persistence.criteria.Predicate;

public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {

	default List<Order> searchOrder(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";

				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("customer").get("companyName")),
						likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("employee").get("firstName")),
						likeKeyword));
			}

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		});
	}

}
