package com.tobeto.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.OrderDetailsService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.orderDetails.GetAllOrderDetailsResponse;
import com.tobeto.entities.concretes.OrderDetails;

@RestController
@RequestMapping("/api/v1/orderDetails")
public class OrderDetailsController {

	@Autowired
	private OrderDetailsService orderDetailsService;

	@Autowired
	private ModelMapperService modelMapper;

	@GetMapping("/getall")
	public List<GetAllOrderDetailsResponse> getAll() {
		List<OrderDetails> orderDetails = orderDetailsService.getAll();
		return orderDetails.stream().map(od -> modelMapper.forResponse().map(od, GetAllOrderDetailsResponse.class))
				.toList();
	}

	@GetMapping("/getByOrderId")
	public List<GetAllOrderDetailsResponse> getByOrderId(@RequestParam UUID orderId) {
		List<OrderDetails> orderDetails = orderDetailsService.getByOrderId(orderId);
		return orderDetails.stream().map(od -> modelMapper.forResponse().map(od, GetAllOrderDetailsResponse.class))
				.toList();
	}

}
