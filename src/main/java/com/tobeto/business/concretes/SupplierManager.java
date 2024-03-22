package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.SupplierService;
import com.tobeto.dataAccess.SupplierRepository;
import com.tobeto.entities.concretes.Supplier;

@Service
public class SupplierManager implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Supplier create(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Supplier update(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
		Supplier supplier = supplierRepository.findById(id).orElseThrow();
		supplierRepository.delete(supplier);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Supplier> getAll() {
		return supplierRepository.findAll();
	}
}
