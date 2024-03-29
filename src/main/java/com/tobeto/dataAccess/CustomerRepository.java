package com.tobeto.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entities.concretes.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

	boolean existsByCompanyName(String companyName);

	boolean existsByTaxNumber(String taxNumber);

	boolean existsByEmail(String email);
}
