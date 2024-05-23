package com.tobeto.business.rules.invoice;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.OrderService;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.OrderRepository;
import com.tobeto.entities.concretes.Invoice;
import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.concretes.OrderDetails;
import com.tobeto.entities.enums.Status;

@Service
public class InvoiceBusinessRules {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	public void isStatusFalse(Invoice invoice) {
		if (invoice.getStatus() == Status.INACTIVE) {
			throw new BusinessException(Messages.INVOICE_STATUS_ALREADY_FALSE);
		}
	}

	public void isOrderStatusFalse(Order order) {
		if (order.getStatus() == Status.INACTIVE) {
			throw new BusinessException(Messages.AN_INVOICE_CANNOT_BE_CRATED_FOR_CANCELED_ORDER);
		}
	}

	public void isInvoiceAlreadyExıst(Order order) {
		if (order.isInvoiceGenerated()) {
			throw new BusinessException(Messages.INVOICE_ALREADY_EXIST);
		}
	}

	public void isInvoicedQuantityFull(List<OrderDetails> products) {
		Map<UUID, List<OrderDetails>> orderDetailsMap = products.stream()
				.collect(Collectors.groupingBy(product -> product.getOrder().getId()));

		for (Map.Entry<UUID, List<OrderDetails>> entry : orderDetailsMap.entrySet()) {
			UUID orderId = entry.getKey();
			List<OrderDetails> orderDetailsList = entry.getValue();

			boolean allMatch = orderDetailsList.stream()
					.allMatch(product -> product.getInvoicedQuantity() == product.getQuantity());

			if (allMatch) {
				Order order = orderService.getOrder(orderId);
				order.setInvoiceGenerated(true);
				orderRepository.save(order);
			}
		}
	}
}
