package com.tobeto.business.rules.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.entities.concretes.Product;
import com.tobeto.entities.concretes.Shelf;

@Service
public class ProductBusinessRules {

	@Autowired
	private ShelfRepository shelfRepository;

	public void fillEmptyShelves(int count, Product product) {
//		List<Shelf> emptyShelves = shelfRepository.findAllByCount(0);
//		int siradakiIlkBosSirasi = 0;
//		while (count > 0) {
//			if (siradakiIlkBosSirasi >= emptyShelves.size()) {
//				// elimizde doldurabileceğimiz boş raf kalmadı.
//				throw new BusinessException("Boş Raf Kalmadı");
//			}
//			Shelf shelf = emptyShelves.get(siradakiIlkBosSirasi); // ilk boş kutu
//			shelf.setProduct(product);
//			int konacakMiktar = count;
//			if (konacakMiktar > shelf.getCapacity()) {
//				konacakMiktar = shelf.getCapacity();
//			}
//			shelf.setCount(konacakMiktar);
//			shelfRepository.save(shelf);
//			count -= konacakMiktar;
//			siradakiIlkBosSirasi++;
//		}
		List<Shelf> emptyShelves = shelfRepository.findAllByCountAndProductId(0, null);

		for (Shelf shelf : emptyShelves) {
			if (count <= 0) {
				break;
			}

			int quantityToAdd = Math.min(count, shelf.getCapacity());
			shelf.setProduct(product);
			shelf.setCount(quantityToAdd);
			shelfRepository.save(shelf);
			count -= quantityToAdd;
		}

		if (count > 0) {
			throw new BusinessException(Messages.NO_EMPTY_SHELF);
		}
	}

	public void clearShelf(Shelf shelf) {
		if (shelf.getCount() == 0) {
			shelf.setProduct(null);
		}
	}

	public void fullShelfSaleProduct(int count, Product product) {
		List<Shelf> availableShelves = shelfRepository.findAllByProductIdAndCountGreaterThan(product.getId(), 0);
		int nextAvailableShelf = availableShelves.size() - 1;

//		while (count > 0) {
//			if (nextAvailableShelf < 0) {
//				// elimizde satış yapabileğimiz dolu raf kalmadı.
//				throw new BusinessException("Satış yapılacak ürün kalmadı!");
//			}
//			Shelf shelf = availableShelves.get(nextAvailableShelf); // ilk dolu raf
//
//			int saleCount = count;
//			if (saleCount > shelf.getCount()) {
//				saleCount = shelf.getCount();
//			}
//			shelf.setCount(shelf.getCount() - saleCount);
//
//			productBusinessRules.clearShelf(shelf);
//
//			shelfRepository.save(shelf);
//			count -= saleCount;
//			nextAvailableShelf--;
//		}
		while (count > 0) {
			if (nextAvailableShelf < 0) {
				throw new BusinessException(Messages.NO_SHELVES_AVAILABLE_FOR_SELLING_PRODUCT);
			}

			Shelf shelf = availableShelves.get(nextAvailableShelf);

			int saleCount = Math.min(count, shelf.getCount());
			shelf.setCount(shelf.getCount() - saleCount);

			clearShelf(shelf);
			shelfRepository.save(shelf);

			count -= saleCount;
			nextAvailableShelf--;
		}
	}
}
