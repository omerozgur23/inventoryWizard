package com.tobeto.business.rules.shelf;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.ShelfRepository;

@Service
public class ShelfBusinessRules {

	@Autowired
	private ShelfRepository shelfRepository;

	public void checkIfByIdExists(UUID id) {
		if (!shelfRepository.existsById(id)) {
			throw new BusinessException(Messages.SHELF_ID_NOT_FOUND);
		}
	}

}
