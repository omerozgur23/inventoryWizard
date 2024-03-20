package com.tobeto.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.ProductService;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.dataAccess.ProductRepository;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.dto.product.ProductWithCategoryDTO;
import com.tobeto.entities.concretes.Product;
import com.tobeto.entities.concretes.Shelf;

import jakarta.transaction.Transactional;

@Service
public class ProductManager implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ShelfRepository shelfRepository;

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Product create(Product product) {
		return productRepository.save(product);
	}

	@Transactional
	public void acceptProduct(UUID productId, int count) {
		Product product = getProduct(productId);
		Optional<Shelf> oShelf = shelfRepository.findByProductIdNotFull(productId);
		if (oShelf.isPresent()) {
			// yarı dolu box bulundu. İçine aldığı kadar fruit koyalım.
			Shelf shelf = oShelf.get();
			int konacakMiktar = count;
			int boxIcindeKalanKisim = shelf.getCapacity() - shelf.getCount();
			if (konacakMiktar > boxIcindeKalanKisim) {
				konacakMiktar = boxIcindeKalanKisim;
			}
			shelf.setCount(shelf.getCount() + konacakMiktar);
			shelfRepository.save(shelf);
			count -= konacakMiktar;
		}
		// kalan fruit'ler varsa kalan boş box'lara doldurulacak.
		if (count > 0) {
			bosShelfDoldur(count, product);
		}
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<ProductWithCategoryDTO> getProductWithCategoryDetails() {
		return productRepository.getProductWithCategoryDetails();
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
		Product product = productRepository.findById(id).orElseThrow();
		productRepository.delete(product);
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void update(Product product) {
		productRepository.save(product);
	}

	private Product getProduct(UUID productId) {
		Optional<Product> oProduct = productRepository.findById(productId);
		Product product = null;
		if (oProduct.isPresent()) {
			product = oProduct.get();
		} else {
			// fruit bulunamadı. hata ver
			throw new BusinessException("Ürün Bulunamadı");
		}
		return product;
	}

	private void bosShelfDoldur(int count, Product product) {
		List<Shelf> emptyShelfs = shelfRepository.findAllByCount(0);
		int siradakiIlkBosSirasi = 0;
		while (count > 0) {
			if (siradakiIlkBosSirasi >= emptyShelfs.size()) {
				// elimizde doldurabileceğimiz boş raf kalmadı.
				throw new BusinessException("Boş Raf Kalmadı");
			}
			Shelf shelf = emptyShelfs.get(siradakiIlkBosSirasi); // ilk boş kutu
			shelf.setProduct(product);
			int konacakMiktar = count;
			if (konacakMiktar > shelf.getCapacity()) {
				konacakMiktar = shelf.getCapacity();
			}
			shelf.setCount(konacakMiktar);
			shelfRepository.save(shelf);
			count -= konacakMiktar;
			siradakiIlkBosSirasi++;
		}
	}
}
