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

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public int create(Shelf shelf, int counter) {
		if (counter>20) {
		  counter=20;	
		}
		
		for (int i=0; i<counter; i++ )   {
			 Shelf newShelf = new Shelf();
			 newShelf.setCapacity(shelf.getCapacity());
			 newShelf.setCount(shelf.getCount());
			 
			 shelfRepository.save(newShelf);
			 
			 
		}
		return counter; 
		
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Shelf> getAll() {
		return shelfRepository.findAll();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
//		
//		shelfRepository.delete(shelf);
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void update(Shelf entity) {

	}

}
