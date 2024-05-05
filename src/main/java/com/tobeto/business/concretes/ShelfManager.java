package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.ShelfService;
import com.tobeto.business.rules.shelf.ShelfBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.entities.concretes.PageResponse;
import com.tobeto.entities.concretes.Shelf;

@Service
public class ShelfManager implements ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	@Autowired
	private ShelfBusinessRules shelfBusinessRules;

	/**********************************************************************/
	/**********************************************************************/

	@Override
	public Shelf create(Shelf shelf) {
		long currentShelfCount = shelfRepository.count();
		shelfBusinessRules.checkIfWarehouseFull(currentShelfCount);
		shelfBusinessRules.checkCapacityGreater(shelf.getCapacity());

		int count = shelf.getCount();
		if (count > 10) {
			count = 10;
		}

		int createdShelfCount = 0;
		for (int i = 0; i < count; i++) {
			if (currentShelfCount + createdShelfCount >= 100) {
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
	public Shelf update(Shelf clientShelf) {
		Shelf shelf = shelfRepository.findById(clientShelf.getId()).orElseThrow();
		shelfBusinessRules.checkCapacityGreater(clientShelf.getCapacity());
		shelf.setCapacity(clientShelf.getCapacity());
		return shelfRepository.save(shelf);

	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
//		shelfBusinessRules.checkIfByIdExists(id);
		Shelf shelf = shelfRepository.findById(id)
				.orElseThrow(() -> new BusinessException(Messages.SHELF_ID_NOT_FOUND));

		shelfBusinessRules.checkIfShelfEmpty(shelf);

		shelfRepository.deleteById(id);

	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public PageResponse<Shelf> getAll() {
		List<Shelf> shelves = shelfRepository.findAll();
		int totalShelvesCount = shelfRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, shelves);
	}

	@Override
	public PageResponse<Shelf> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Shelf> shelves = shelfRepository.findAll(pageable).getContent();
		int totalShelvesCount = shelfRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, shelves);
	}

	@Override
	public List<Shelf> searchItem(String keyword) {
		return shelfRepository.searchShelf(keyword);
	}
}
