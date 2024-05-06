package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.OrderDetailsService;
import com.tobeto.dataAccess.OrderDetailsRepository;
import com.tobeto.entities.concretes.OrderDetails;
import com.tobeto.entities.concretes.PageResponse;

@Service
public class OrderDetailsManager implements OrderDetailsService {

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Override
	public List<OrderDetails> getAll() {
		return orderDetailsRepository.findAll();
	}

	@Override
	public List<OrderDetails> getByOrderId(UUID orderId) {
		List<OrderDetails> orderDetails = orderDetailsRepository.getOrderDetailsByOrderId(orderId);
		return orderDetails;
	}

	@Override
	public PageResponse<OrderDetails> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<OrderDetails> orderDetails = orderDetailsRepository.findAll(pageable).getContent();
		int totalShelvesCount = orderDetailsRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, orderDetails);
	}

}
