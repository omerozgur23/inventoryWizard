package com.tobeto.business.abstracts;

import java.util.List;

import com.tobeto.entities.concretes.Order;

public interface OrderService {

	List<Order> getAll();

	List<Order> getAllByPage(int pageNo, int pageSize);

	List<Order> searchItem(String keyword);

}
