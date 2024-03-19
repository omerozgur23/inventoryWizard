package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.ProductService;
import com.tobeto.dataAccess.ProductRepository;
import com.tobeto.dto.product.ProductWithCategoryDTO;
import com.tobeto.entities.concretes.Product;

@Service
public class ProductManager implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Product create(Product product) {
		return productRepository.save(product);
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
}
