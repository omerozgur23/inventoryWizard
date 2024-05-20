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
import com.tobeto.dto.PageResponse;
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
	public PageResponse<GetAllOrderDetailsResponse> getAll() {
		PageResponse<OrderDetails> orderDetailsPage = orderDetailsService.getAll();
		List<GetAllOrderDetailsResponse> responseList = orderDetailsPage.getData().stream()
				.map(od -> modelMapper.forResponse().map(od, GetAllOrderDetailsResponse.class)).toList();
		return new PageResponse<GetAllOrderDetailsResponse>(orderDetailsPage.getCount(), responseList);
	}

	@GetMapping("/getByOrderId")
	public PageResponse<GetAllOrderDetailsResponse> getByOrderId(@RequestParam UUID orderId,
			@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<OrderDetails> orderDetailPage = orderDetailsService.getByOrderId(orderId, pageNo, pageSize);
		List<GetAllOrderDetailsResponse> responseList = orderDetailPage.getData().stream()
				.map(orderDetail -> modelMapper.forResponse().map(orderDetail, GetAllOrderDetailsResponse.class))
				.toList();
		return new PageResponse<>(orderDetailPage.getCount(), responseList);
	}

}
