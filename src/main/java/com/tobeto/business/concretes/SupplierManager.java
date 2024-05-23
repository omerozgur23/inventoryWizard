package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.SupplierService;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.SupplierRepository;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.Supplier;
import com.tobeto.entities.enums.Status;

@Service
public class SupplierManager implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public Supplier create(Supplier supplier) {
		supplier.setCreatedDate(LocalDateTime.now());
		supplier.setStatus(Status.ACTIVE);
		return supplierRepository.save(supplier);
	}

	@Override
	public Supplier update(Supplier clientSupplier) {
		Supplier supplier = getSupplier(clientSupplier.getId());
		BeanUtils.copyProperties(clientSupplier, supplier, "id", "taxNumber", "createdDate", "status");
		return supplierRepository.save(supplier);
	}

	@Override
	public void delete(UUID id) {
		Supplier supplier = getSupplier(id);
		supplier.setStatus(Status.INACTIVE);
		supplier.setInactiveDate(LocalDateTime.now());
		supplierRepository.save(supplier);
	}

	@Override
	public PageResponse<Supplier> getAll() {
		List<Supplier> suppliers = supplierRepository.findAllActive();
		int totalSupplierCount = supplierRepository.findAllActive().size();
		return new PageResponse<>(totalSupplierCount, suppliers);
	}

	@Override
	public PageResponse<Supplier> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Supplier> suppliers = supplierRepository.findAllByPagination(pageable).getContent();
		int totalSupplierCount = supplierRepository.findAllActive().size();
		return new PageResponse<>(totalSupplierCount, suppliers);
	}

	@Override
	public PageResponse<Supplier> searchItem(String keyword) {
		List<Supplier> suppliers = supplierRepository.searchSupplier(keyword);
		int totalSupplierCount = supplierRepository.searchSupplier(keyword).size();
		return new PageResponse<Supplier>(totalSupplierCount, suppliers);
	}

	@Override
	public Supplier getSupplier(UUID supplierId) {
		Optional<Supplier> oSupplier = supplierRepository.findById(supplierId);
		Supplier supplier = null;
		if (oSupplier.isPresent()) {
			supplier = oSupplier.get();
		} else {
			throw new BusinessException(Messages.SUPPLIER_ID_NOT_FOUND);
		}
		return supplier;
	}
}
