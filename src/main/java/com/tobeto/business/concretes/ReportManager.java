package com.tobeto.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.ReportService;
import com.tobeto.dataAccess.OrderDetailsRepository;
import com.tobeto.dataAccess.OrderRepository;
import com.tobeto.dto.report.GetBestSellingProductsResponse;
import com.tobeto.dto.report.GetCustomerByOrderCountResponse;
import com.tobeto.dto.report.GetEmployeeByOrderCountResponse;
import com.tobeto.entities.concretes.Order;

@Service
public class ReportManager implements ReportService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Override
	public double getTotalSale() {
		return orderDetailsRepository.getTotalSale();
	}

	@Override
	public double getTotalSalesCost() {
		return orderDetailsRepository.getTotalSalesCost();
	}

	@Override
	public double getWinning() {
		double winnings = getTotalSale() - getTotalSalesCost();
		return winnings;
	}

	@Override
	public List<Order> getLastFiveOrders() {
		return orderRepository.getLastFiveOrders();
	}

	@Override
	public List<GetCustomerByOrderCountResponse> getOrdersTheMost() {
		return orderRepository.getOrdersTheMost();
	}

	@Override
	public List<GetEmployeeByOrderCountResponse> getMostOrderSenders() {
		return orderRepository.getMostOrderSenders();
	}

	@Override
	public List<GetBestSellingProductsResponse> getBestSellingProducts() {
		return orderDetailsRepository.getBestSellingProducts();
	}
}
