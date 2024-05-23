package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.OrderDetailsService;
import com.tobeto.dataAccess.OrderDetailsRepository;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.OrderDetails;

@Service
public class OrderDetailsManager implements OrderDetailsService {

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Override
	public PageResponse<OrderDetails> getAll() {
		List<OrderDetails> orderDetials = orderDetailsRepository.findAll();
		int totalOrderDetailCount = orderDetailsRepository.findAll().size();
		return new PageResponse<OrderDetails>(totalOrderDetailCount, orderDetials);
	}

	@Override
	public PageResponse<OrderDetails> getByOrderId(UUID orderId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<OrderDetails> orderDetailsPage = orderDetailsRepository.findByOrderId(orderId, pageable);
		int totalOrderDetailCount = orderDetailsRepository.findAll().size();
		return new PageResponse<OrderDetails>(totalOrderDetailCount, orderDetailsPage);
	}
}
