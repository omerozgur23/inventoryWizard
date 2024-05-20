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

import com.tobeto.business.abstracts.ShelfService;
import com.tobeto.business.rules.shelf.ShelfBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.Shelf;
import com.tobeto.entities.enums.Status;

@Service
public class ShelfManager implements ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	@Autowired
	private ShelfBusinessRules shelfBusinessRules;

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
			newShelf.setCreatedDate(LocalDateTime.now());
			newShelf.setStatus(Status.ACTIVE);
			shelfRepository.save(newShelf);
			createdShelfCount++;
		}
		return shelf;
	}

	@Override
	public Shelf update(Shelf clientShelf) {
		Shelf shelf = getShelf(clientShelf.getId());
		shelfBusinessRules.checkCapacityGreater(clientShelf.getCapacity());
		BeanUtils.copyProperties(clientShelf, shelf, "createdDate", "status");
		return shelfRepository.save(shelf);
	}

	@Override
	public void delete(UUID id) {
		Shelf shelf = getShelf(id);
		shelfBusinessRules.checkIfShelfEmpty(shelf);
		shelf.setStatus(Status.INACTIVE);
		shelf.setInactiveDate(LocalDateTime.now());
		shelfRepository.save(shelf);
	}

	@Override
	public PageResponse<Shelf> getAll() {
		List<Shelf> shelves = shelfRepository.findAll();
		int totalShelvesCount = shelfRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, shelves);
	}

	@Override
	public PageResponse<Shelf> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Shelf> shelves = shelfRepository.findAllOrderByProductIdNotNull(pageable);
		int totalShelvesCount = shelfRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, shelves);
	}

	@Override
	public PageResponse<Shelf> searchItem(String keyword) {
		List<Shelf> shelves = shelfRepository.searchShelf(keyword);
		int totalShelvesCount = shelfRepository.searchShelf(keyword).size();
		return new PageResponse<Shelf>(totalShelvesCount, shelves);
	}

	@Override
	public Shelf getShelf(UUID shelfId) {
		Optional<Shelf> oShelf = shelfRepository.findById(shelfId);
		Shelf shelf = null;
		if (oShelf.isPresent()) {
			shelf = oShelf.get();
		} else {
			throw new BusinessException(Messages.SHELF_ID_NOT_FOUND);
		}
		return shelf;
	}
}
