package com.tobeto.dataAccess;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID> {

	@Query("SELECT o FROM OrderDetails o WHERE o.order.id = :orderId")
	List<OrderDetails> getOrderDetailsByOrderId(UUID orderId);
}
