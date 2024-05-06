package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tobeto.entities.concretes.User;

import jakarta.persistence.criteria.Predicate;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

	Optional<User> findByEmail(String email);

	default List<User> searchUser(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";

				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likeKeyword));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("roles").get("role")), likeKeyword));
			}

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		});
	}

}
