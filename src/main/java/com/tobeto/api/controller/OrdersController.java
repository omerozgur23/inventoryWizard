package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.concretes.OrderManager;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.order.GetAllOrderResponse;
import com.tobeto.entities.concretes.Order;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

	@Autowired
	private OrderManager orderManager;

	@Autowired
	private ModelMapperService modelMapper;

	@GetMapping("/getall")
	public List<GetAllOrderResponse> getAll() {
		List<Order> orders = orderManager.getAll();
		return orders.stream().map(order -> modelMapper.forResponse().map(order, GetAllOrderResponse.class)).toList();
	}

	@GetMapping("/getallByPage")
	public List<GetAllOrderResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "2") int pageSize) {
		List<Order> orderPage = orderManager.getAllByPage(pageNo, pageSize);
		return orderPage.stream().map(product -> modelMapper.forResponse().map(product, GetAllOrderResponse.class))
				.toList();
	}

}
