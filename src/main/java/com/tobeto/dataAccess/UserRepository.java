package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.User;
import com.tobeto.entities.enums.Status;

import jakarta.persistence.criteria.Predicate;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

	@Query("SELECT u FROM User u WHERE u.status = Status.ACTIVE")
	List<User> findAllActive();

	@Query("SELECT u FROM User u WHERE u.status = Status.ACTIVE")
	Page<User> findAllByPagination(Pageable pageable);

	Optional<User> findByEmail(String email);

	default List<User> searchUser(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("status"), Status.ACTIVE));
			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";
				List<Predicate> keywordPredicates = new ArrayList<>();
				keywordPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likeKeyword));
				keywordPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likeKeyword));
				keywordPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likeKeyword));
				keywordPredicates
						.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("roles").get("role")), likeKeyword));
				predicates.add(criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0])));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
	}

}
