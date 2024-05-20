package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.Shelf;
import com.tobeto.entities.enums.Status;

import jakarta.persistence.criteria.Predicate;

public interface ShelfRepository extends JpaRepository<Shelf, UUID>, JpaSpecificationExecutor<Shelf> {

	@Query("SELECT s FROM Shelf s WHERE s.status = Status.ACTIVE")
	List<Shelf> findAll();

	@Query("SELECT s FROM Shelf s WHERE s.status = Status.ACTIVE ORDER BY s.product DESC")
	List<Shelf> findAllOrderByProductIdNotNull(Pageable pageable);

	@Query("SELECT s FROM Shelf s WHERE s.product.id = :productId and s.count < s.capacity")
	Optional<Shelf> findByProductIdNotFull(UUID productId);

	List<Shelf> findAllByProductIdAndCountGreaterThan(UUID productId, int count);

	@Query("SELECT COALESCE(sum(s.count), 0) FROM Shelf s WHERE s.product.id = :productId")
	Integer sumCountByProductId(UUID productId);

	List<Shelf> findAllByCountAndProductId(int count, UUID productId);

	default List<Shelf> searchShelf(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("status"), Status.ACTIVE));
			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";
				List<Predicate> keywordPredicates = new ArrayList<>();
				keywordPredicates.add(criteriaBuilder
						.like(criteriaBuilder.lower(root.get("product").get("productName")), likeKeyword));
				predicates.add(criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0])));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
	}
}
