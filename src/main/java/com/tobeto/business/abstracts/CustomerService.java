package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.entities.concretes.Customer;

public interface CustomerService extends BaseService<Customer> {

	Customer getCustomer(UUID customerId);

}
