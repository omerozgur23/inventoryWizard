package com.tobeto.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.dto.report.GetCustomerByOrderCountResponse;
import com.tobeto.dto.report.GetEmployeeByOrderCountResponse;
import com.tobeto.entities.concretes.Order;

import jakarta.persistence.criteria.Predicate;

public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {

	@Query("SELECT o FROM Order o ORDER BY o.orderDate DESC LIMIT 5")
	List<Order> getLastFiveOrders();

	@Query("SELECT new com.tobeto.dto.report.GetCustomerByOrderCountResponse(c.id, c.companyName, COUNT(o.customer.id) AS count) "
			+ "FROM Order o JOIN o.customer c " + "GROUP BY c.id, c.companyName " + "ORDER BY count DESC LIMIT 5")
	List<GetCustomerByOrderCountResponse> getOrdersTheMost();

	@Query("SELECT new com.tobeto.dto.report.GetEmployeeByOrderCountResponse(e.id, e.firstName, e.lastName, COUNT(o.employee.id) AS count) "
			+ "FROM Order o JOIN o.employee e " + "GROUP BY e.id, e.firstName, e.lastName "
			+ "ORDER BY count DESC LIMIT 5")
	List<GetEmployeeByOrderCountResponse> getMostOrderSenders();

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
