package com.tobeto.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entities.concretes.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

}
