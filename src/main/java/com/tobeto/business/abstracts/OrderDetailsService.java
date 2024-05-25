package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.OrderDetails;

public interface OrderDetailsService extends BaseService<OrderDetails> {

	default OrderDetails create(OrderDetails entity) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default OrderDetails update(OrderDetails entity) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default void delete(UUID id) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default PageResponse<OrderDetails> searchItem(String keyword) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default PageResponse<OrderDetails> getAllByPage(int pageNo, int pageSize) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	PageResponse<OrderDetails> getByOrderId(UUID orderId, int pageNo, int pageSize);
}
