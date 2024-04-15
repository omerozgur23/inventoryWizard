package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

import com.tobeto.entities.concretes.OrderDetails;

public interface OrderDetailsService {

	List<OrderDetails> getAll();

	List<OrderDetails> getByOrderId(UUID orderId);
}
