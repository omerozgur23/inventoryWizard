package com.tobeto.dataAccess;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.dto.report.GetBestSellingProductsResponse;
import com.tobeto.entities.concretes.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID> {

	@Query("SELECT o FROM OrderDetails o WHERE o.order.id = :orderId")
	List<OrderDetails> getOrderDetailsByOrderId(UUID orderId);

	List<OrderDetails> findByOrderId(UUID orderId, Pageable pageable);

	@Query("SELECT new com.tobeto.dto.report.GetBestSellingProductsResponse(p.id, p.productName, p.quantity, p.purchasePrice, p.unitPrice, SUM(od.quantity) AS count) "
			+ "FROM OrderDetails od JOIN od.product p " + "WHERE od.status = Status.ACTIVE "
			+ "GROUP BY p.id, p.productName, p.quantity, p.purchasePrice, p.unitPrice " + "ORDER BY count DESC LIMIT 5")
	List<GetBestSellingProductsResponse> getBestSellingProducts();

	@Query("SELECT SUM(od.totalPrice) FROM OrderDetails od WHERE od.status = Status.ACTIVE")
	double getTotalSale();

	@Query("SELECT SUM(od.quantity * p.purchasePrice) AS totalSalesCost FROM OrderDetails od JOIN od.product p WHERE od.status = Status.ACTIVE")
	double getTotalSalesCost();
}
