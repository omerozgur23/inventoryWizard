package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.ShelfService;
import com.tobeto.business.rules.shelf.ShelfBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.entities.concretes.Shelf;

@Service
public class ShelfManager implements ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	@Autowired
	private ShelfBusinessRules shelfBusinessRules;

	/**********************************************************************/
	/**********************************************************************/
//	@Override
//	public int create(int capacity, int count) {
//		long currentShelfCount = shelfRepository.count();
//		if (currentShelfCount >= 5) {
//			throw new BusinessException(Messages.WAREHOUSE_FULL);
//		}
//
//		if (count > 5) {
//			count = 5;
//		}
//
//		int createdShelfCount = 0;
//		for (int i = 0; i < count; i++) {
//			if (currentShelfCount + createdShelfCount >= 5) {
//				break;
//			}
//			Shelf newShelf = new Shelf();
//			newShelf.setCapacity(capacity);
//			shelfRepository.save(newShelf);
//			createdShelfCount++;
//		}
//		return createdShelfCount;
//	}
	@Override
	public Shelf create(Shelf shelf) {
		long currentShelfCount = shelfRepository.count();
		if (currentShelfCount >= 5)
			throw new BusinessException(Messages.WAREHOUSE_FULL);

		int count = shelf.getCount();
		if (count > 5) {
			count = 5;
		}

		int createdShelfCount = 0;
		for (int i = 0; i < count; i++) {
			if (currentShelfCount + createdShelfCount >= 5) {
				break;
			}
			Shelf newShelf = new Shelf();
			newShelf.setCapacity(shelf.getCapacity());
			shelfRepository.save(newShelf);
			createdShelfCount++;
		}
		return shelf;
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Shelf update(Shelf shelf) {
		return shelfRepository.save(shelf);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
		shelfBusinessRules.checkIfByIdExists(id);
		shelfRepository.deleteById(id);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Shelf> getAll() {
		return shelfRepository.findAll();
	}
}
