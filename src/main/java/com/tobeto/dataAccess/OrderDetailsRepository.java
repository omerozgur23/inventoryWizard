package com.tobeto.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entities.concretes.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID> {

}
