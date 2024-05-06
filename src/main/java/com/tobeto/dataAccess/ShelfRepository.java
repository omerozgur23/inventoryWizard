package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.Shelf;

import jakarta.persistence.criteria.Predicate;

public interface ShelfRepository extends JpaRepository<Shelf, UUID>, JpaSpecificationExecutor<Shelf> {

	@Query("SELECT s FROM Shelf s WHERE s.product.id = :productId and s.count < s.capacity")
	Optional<Shelf> findByProductIdNotFull(UUID productId);

	List<Shelf> findAllByProductIdAndCountGreaterThan(UUID productId, int count);

	@Query("SELECT COALESCE(sum(s.count), 0) FROM Shelf s WHERE s.product.id = :productId")
	Integer sumCountByProductId(UUID productId);

	List<Shelf> findAllByCountAndProductId(int count, UUID productId);

	default List<Shelf> searchShelf(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";

				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("product").get("productName")),
						likeKeyword));
			}

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		});
	}
}
