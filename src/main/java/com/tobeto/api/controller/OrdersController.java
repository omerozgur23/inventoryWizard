package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.OrderService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.PaginationRequest;
import com.tobeto.dto.SearchRequest;
import com.tobeto.dto.order.GetAllOrderResponse;
import com.tobeto.entities.concretes.Order;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ModelMapperService modelMapper;

	@GetMapping("/getall")
	public PageResponse<GetAllOrderResponse> getAll() {
		PageResponse<Order> orderPage = orderService.getAll();
		List<GetAllOrderResponse> responseList = orderPage.getData().stream()
				.map(order -> modelMapper.forResponse().map(order, GetAllOrderResponse.class)).toList();
		return new PageResponse<GetAllOrderResponse>(orderPage.getCount(), responseList);
	}

	@PostMapping("/getallByPage")
	public PageResponse<GetAllOrderResponse> getAllOrderByPage(@Valid @RequestBody PaginationRequest request) {
		PageResponse<Order> orderPage = orderService.getAllByPage(request.getPageNo(), request.getPageSize());
		List<GetAllOrderResponse> responseList = orderPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllOrderResponse.class)).toList();
		return new PageResponse<>(orderPage.getCount(), responseList);
	}

	@PostMapping("/search")
	public PageResponse<GetAllOrderResponse> searchOrder(@RequestBody SearchRequest request) {
		PageResponse<Order> orderPage = orderService.searchItem(request.getKeyword());
		List<GetAllOrderResponse> responseList = orderPage.getData().stream()
				.map(order -> modelMapper.forResponse().map(order, GetAllOrderResponse.class)).toList();
		return new PageResponse<GetAllOrderResponse>(orderPage.getCount(), responseList);
	}
}
