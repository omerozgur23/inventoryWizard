package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.ShelfService;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.entities.concretes.Shelf;

@Service
public class ShelfManager implements ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	@Override
	public void create(Shelf shelf) {
		shelfRepository.save(shelf);
	}

	@Override
	public List<Shelf> getAll() {
		return shelfRepository.findAll();
	}

	@Override
	public void delete(UUID id) {
		Shelf shelf = shelfRepository.findByCategoryId(id);
		shelfRepository.delete(shelf);
	}

	@Override
	public void update(Shelf entity) {

	}

}
