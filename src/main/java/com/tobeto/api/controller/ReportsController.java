package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.ReportService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.order.GetAllOrderResponse;
import com.tobeto.dto.report.GetBestSellingProductsResponse;
import com.tobeto.dto.report.GetCustomerByOrderCountResponse;
import com.tobeto.dto.report.GetEmployeeByOrderCountResponse;
import com.tobeto.entities.concretes.Order;

@RestController
@RequestMapping("/api/v1/report")
public class ReportsController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private ModelMapperService modelMapper;

	@GetMapping("/totalsales")
	public double getTotalSales() {
		return reportService.getTotalSale();
	}

	@GetMapping("/totalsalescost")
	public double getTotalSalesCost() {
		return reportService.getTotalSalesCost();
	}

	@GetMapping("/winning")
	public double getWinning() {
		return reportService.getWinning();
	}

	@GetMapping("/lastfiveorders")
	public List<GetAllOrderResponse> getLastFiveOrders() {
		List<Order> orders = reportService.getLastFiveOrders();
		return orders.stream().map(order -> modelMapper.forResponse().map(order, GetAllOrderResponse.class)).toList();
	}

	@GetMapping("/orderthemost")
	public List<GetCustomerByOrderCountResponse> getOrdersTheMost() {
		return reportService.getOrdersTheMost();
	}

	@GetMapping("/mostordersenders")
	public List<GetEmployeeByOrderCountResponse> getMostOrderSenders() {
		return reportService.getMostOrderSenders();
	}

	@GetMapping("/bestsellingproducts")
	public List<GetBestSellingProductsResponse> getBestSellingProduct() {
		return reportService.getBestSellingProducts();
	}
}
