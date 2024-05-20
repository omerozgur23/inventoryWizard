package com.tobeto.business.rules.shelf;

import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.entities.concretes.Product;
import com.tobeto.entities.concretes.Shelf;

@Service
public class ShelfBusinessRules {

	public void checkIfShelfEmpty(Shelf shelf) {
		Product product = shelf.getProduct();
		if (product == null || product.getProductName() == null || product.getProductName().isEmpty()) {
			return;
		} else {
			throw new BusinessException(Messages.SHELF_IS_NOT_EMPTY);
		}
	}

	public void checkCapacityGreater(int capacity) {
		if (capacity > 10) {
			throw new BusinessException(Messages.CAPACITY_CANNOT_BE_BIG);
		}
	}

	public void checkIfWarehouseFull(long currentShelfCount) {
		if (currentShelfCount >= 100) {
			throw new BusinessException(Messages.WAREHOUSE_FULL);
		}
	}

}
