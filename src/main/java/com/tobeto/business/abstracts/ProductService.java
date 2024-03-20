package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

import com.tobeto.dto.product.ProductWithCategoryDTO;
import com.tobeto.entities.concretes.Product;

public interface ProductService extends BaseService<Product> {
	List<ProductWithCategoryDTO> getProductWithCategoryDetails();

	void acceptProduct(UUID productId, int count);

}
