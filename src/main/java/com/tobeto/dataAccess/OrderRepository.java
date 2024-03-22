package com.tobeto.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entities.concretes.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
