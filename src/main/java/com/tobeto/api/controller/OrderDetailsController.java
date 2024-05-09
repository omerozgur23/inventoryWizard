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
import com.tobeto.entities.concretes.PageResponse;

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

	@GetMapping("/getallByPage")
	public PageResponse<GetAllOrderDetailsResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<OrderDetails> shelfPage = orderDetailsService.getAllByPage(pageNo, pageSize);
		List<GetAllOrderDetailsResponse> responseList = shelfPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllOrderDetailsResponse.class)).toList();
		return new PageResponse<>(shelfPage.getCount(), responseList);
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
