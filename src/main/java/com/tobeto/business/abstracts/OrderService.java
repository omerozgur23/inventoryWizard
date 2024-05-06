package com.tobeto.business.abstracts;

import java.util.List;

import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.concretes.PageResponse;

public interface OrderService {

	List<Order> getAll();

//	List<Order> getAllByPage(int pageNo, int pageSize);

	List<Order> searchItem(String keyword);

	PageResponse<Order> getAllByPage(int pageNo, int pageSize);

}
