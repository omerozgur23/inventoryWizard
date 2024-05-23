package com.tobeto.business.rules.order;

import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.enums.Status;

@Service
public class OrderBusinessRules {

	public void isStatusInactive(Order order) {
		if (order.getStatus() == Status.INACTIVE) {
			throw new BusinessException(Messages.ORDER_STATUS_ALREADY_FALSE);
		}
	}
}
