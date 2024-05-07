package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tobeto.entities.concretes.Invoice;

import jakarta.persistence.criteria.Predicate;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID>, JpaSpecificationExecutor<Invoice> {

//	@Query("SELECT o.customer FROM Order o WHERE o.id = :orderId")
//	Customer getCustomerIdFromOrders(UUID orderId);
//
//	@Query("SELECT o.orderPrice FROM Order o WHERE o.id = :orderId")
//	double getTotalPriceFromOrders(UUID orderId);
//
//	@Query("SELECT od FROM OrderDetails od WHERE od.order.id = :orderId")
//	List<OrderDetails> getProductsFromOrderDetails(UUID orderId);

//	@Query("SELECT o.id FROM Order o WHERE o.id = :orderId")
//	Order getOrder(UUID orderId);

	default List<Invoice> searchInvoice(String keyword) {
		return findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (keyword != null && !keyword.isEmpty()) {
				String likeKeyword = "%" + keyword + "%";

				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("customer").get("companyName")),
						likeKeyword));
			}
			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		});
	}
}
