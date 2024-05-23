package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.OrderService;
import com.tobeto.business.rules.order.OrderBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.OrderRepository;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.concretes.OrderDetails;
import com.tobeto.entities.enums.Status;

@Service
public class OrderManager implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderBusinessRules orderBusinessRules;

	@Override
	public PageResponse<Order> getAll() {
		List<Order> orders = orderRepository.findAll();
		int totalOrderCount = orderRepository.findAll().size();
		return new PageResponse<Order>(totalOrderCount, orders);
	}

	@Override
	public PageResponse<Order> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Order> orders = orderRepository.findAll(pageable).getContent();
		int totalOrderCount = orderRepository.findAll().size();
		return new PageResponse<>(totalOrderCount, orders);
	}

	@Override
	public PageResponse<Order> searchItem(String keyword) {
		List<Order> orders = orderRepository.searchOrder(keyword);
		int totalOrderCount = orderRepository.searchOrder(keyword).size();
		return new PageResponse<Order>(totalOrderCount, orders);
	}

	@Override
	public void invoiceCancellation(UUID orderId) {
		Order order = getOrder(orderId);
		orderBusinessRules.isStatusInactive(order);
		order.setInvoiceGenerated(false);

		for (OrderDetails orderDetail : order.getOrderDetails()) {
			if (orderDetail.getInvoicedQuantity() == 0) {
				orderDetail.setStatus(Status.INACTIVE);
				orderDetail.setInactiveDate(LocalDateTime.now());
			}
		}

		boolean allInvoicesInactive = order.getInvoice().stream().allMatch(inv -> inv.getStatus() == Status.INACTIVE);

		if (allInvoicesInactive) {
			order.setStatus(Status.INACTIVE);
			order.setInactiveDate(LocalDateTime.now());
		}
		orderRepository.save(order);
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
