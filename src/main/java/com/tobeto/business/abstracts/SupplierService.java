package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.entities.concretes.Supplier;

public interface SupplierService extends BaseService<Supplier> {
	Supplier getSupplier(UUID supplierId);
}
