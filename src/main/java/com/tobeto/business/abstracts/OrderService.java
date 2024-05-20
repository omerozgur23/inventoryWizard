package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.entities.concretes.Order;

public interface OrderService extends BaseService<Order> {

	default Order create(Order entity) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default Order update(Order entity) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default void delete(UUID id) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	Order getOrder(UUID orderId);

	void invoiceCancellation(UUID orderId);
}
