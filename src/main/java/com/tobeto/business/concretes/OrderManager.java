package com.tobeto.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.OrderService;
import com.tobeto.dataAccess.OrderRepository;
import com.tobeto.entities.concretes.Order;

@Service
public class OrderManager implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return orderRepository.findAll(pageable).getContent();
	}
}
