package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.entities.concretes.Shelf;

public interface ShelfService extends BaseService<Shelf> {

	Shelf getShelf(UUID shelfId);
}
