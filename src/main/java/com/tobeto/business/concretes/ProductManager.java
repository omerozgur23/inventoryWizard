package com.tobeto.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.ProductService;
import com.tobeto.business.rules.product.ProductBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.ProductRepository;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.dto.product.ProductWithCategoryResponse;
import com.tobeto.entities.concretes.Product;

import jakarta.transaction.Transactional;

@Service
public class ProductManager implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductBusinessRules productBusinessRules;

	@Autowired
	private ShelfRepository shelfRepository;

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Product create(Product product) {
		return productRepository.save(product);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Product update(Product product) {
		return productRepository.save(product);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
		Product product = productRepository.findById(id).orElseThrow();
		productRepository.delete(product);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	/**********************************************************************/
	/**********************************************************************/
	@Transactional
	public void acceptProduct(UUID productId, int count) {
		Product product = getProduct(productId);
		int[] remainingCount = new int[] { count };
//		Optional<Shelf> oShelf = shelfRepository.findByProductIdNotFull(productId);
//		if (oShelf.isPresent()) {
//			// yarı dolu shelf bulundu. İçine aldığı kadar product koyalım.
//			Shelf shelf = oShelf.get();
//			int konacakMiktar = count;
//			int boxIcindeKalanKisim = shelf.getCapacity() - shelf.getCount();
//			if (konacakMiktar > boxIcindeKalanKisim) {
//				konacakMiktar = boxIcindeKalanKisim;
//			}
//			shelf.setCount(shelf.getCount() + konacakMiktar);
//			shelfRepository.save(shelf);
//			count -= konacakMiktar;
//		}
//		if (count > 0) {
//			fillEmptyShelves(count, product);
//		}

		// Yarı dolu bir rafta ürün kabul etme
		shelfRepository.findByProductIdNotFull(productId).ifPresent(shelf -> {

			int availableSpace = shelf.getCapacity() - shelf.getCount();
			int quantityToAdd = Math.min(remainingCount[0], availableSpace);
			shelf.setCount(shelf.getCount() + quantityToAdd);
			shelfRepository.save(shelf);
			remainingCount[0] -= quantityToAdd;
		});

		// Eğer hala ürün kaldıysa, boş rafları doldurma
		if (remainingCount[0] > 0) {
			productBusinessRules.fillEmptyShelves(remainingCount[0], product);
		}
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void saleProduct(UUID productId, int count) {
		Product product = getProduct(productId);
		int[] remainingCount = new int[] { count };

		shelfRepository.findByProductIdNotFull(productId).ifPresent(shelf -> {
			int saleCount = Math.min(count, shelf.getCount());
			shelf.setCount(shelf.getCount() - saleCount);

			productBusinessRules.clearShelf(shelf);
			shelfRepository.save(shelf);
			remainingCount[0] -= saleCount;
		});
//		Optional<Shelf> oShelf = shelfRepository.findByProductIdNotFull(productId);
//
//		if (oShelf.isPresent()) {
//			Shelf shelf = oShelf.get();
//
//			int saleCount = Math.min(count, shelf.getCount());
//			shelf.setCount(shelf.getCount() - saleCount);
//
//			productBusinessRules.clearShelf(shelf);
//			shelfRepository.save(shelf);
//			count -= saleCount;
//		}
		if (remainingCount[0] > 0)
			productBusinessRules.fullShelfSaleProduct(remainingCount[0], product);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<ProductWithCategoryResponse> getProductWithCategoryDetails() {
		return productRepository.getProductWithCategoryDetails();
	}

	/**********************************************************************/
	/**********************************************************************/
	private Product getProduct(UUID productId) {
		Optional<Product> oProduct = productRepository.findById(productId);
		Product product = null;
		if (oProduct.isPresent()) {
			product = oProduct.get();
		} else {
			throw new BusinessException(Messages.PRODUCT_ID_NOT_FOUND);
		}
		return product;
	}

}
