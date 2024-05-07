package com.tobeto.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.OrderService;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.OrderRepository;
import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.concretes.PageResponse;

@Service
public class OrderManager implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	@Override
	public PageResponse<Order> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Order> orders = orderRepository.findAll(pageable).getContent();
		int totalShelvesCount = orderRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, orders);
	}

	@Override
	public List<Order> searchItem(String keyword) {
		return orderRepository.searchOrder(keyword);
	}

	@Override
	public Order getOrder(UUID orderId) {
		Optional<Order> oOrder = orderRepository.findById(orderId);
		Order order = null;
		if (oOrder.isPresent()) {
			order = oOrder.get();
		} else {
			throw new BusinessException(Messages.ORDER_ID_NOT_FOUND);
		}
		return order;
	}
}
