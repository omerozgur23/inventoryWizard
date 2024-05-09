package com.tobeto.business.rules.order;

import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.entities.concretes.Order;

@Service
public class OrderBusinessRules {

	public void isStatusFalse(Order order) {
		if (!order.isOrderStatus()) {
			throw new BusinessException(Messages.ORDER_STATUS_ALREADY_FALSE);
		}
	}
}
