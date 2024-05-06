package com.tobeto.business.abstracts;

import java.util.List;

import com.tobeto.dto.report.GetBestSellingProductsResponse;
import com.tobeto.dto.report.GetCustomerByOrderCountResponse;
import com.tobeto.dto.report.GetEmployeeByOrderCountResponse;
import com.tobeto.entities.concretes.Order;

public interface ReportService {

	double getTotalSale();

	double getTotalSalesCost();

	double getWinning();

	List<Order> getLastFiveOrders();

	List<GetCustomerByOrderCountResponse> getOrdersTheMost();

	List<GetEmployeeByOrderCountResponse> getMostOrderSenders();

	List<GetBestSellingProductsResponse> getBestSellingProducts();
}
