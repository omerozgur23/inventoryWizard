package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.SupplierService;
import com.tobeto.dataAccess.SupplierRepository;
import com.tobeto.entities.concretes.PageResponse;
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
	public Supplier update(Supplier clientSupplier) {
		Supplier supplier = supplierRepository.findById(clientSupplier.getId()).orElseThrow();
		supplier.setCompanyName(clientSupplier.getCompanyName());
		supplier.setContactName(clientSupplier.getContactName());
		supplier.setContactEmail(clientSupplier.getContactEmail());
		supplier.setContactPhone(clientSupplier.getContactPhone());
		supplier.setAddress(clientSupplier.getAddress());
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
	public PageResponse<Supplier> getAll() {
		List<Supplier> suppliers = supplierRepository.findAll();
		int totalShelvesCount = supplierRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, suppliers);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public PageResponse<Supplier> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Supplier> suppliers = supplierRepository.findAll(pageable).getContent();
		int totalShelvesCount = supplierRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, suppliers);
	}

	@Override
	public List<Supplier> searchItem(String keyword) {
		return supplierRepository.searchSupplier(keyword);
	}
}
