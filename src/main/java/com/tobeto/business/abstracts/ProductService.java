package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

import com.tobeto.dto.product.ProductWithCategoryResponse;
import com.tobeto.entities.concretes.Product;

public interface ProductService extends BaseService<Product> {
	List<ProductWithCategoryResponse> getProductWithCategoryDetails();

	void acceptProduct(UUID productId, int count);

	void saleProduct(UUID productId, int count, UUID customerId, UUID userId);

	List<Product> getAllByPage(int pageNo, int pageSize);

	/************************ search deneme *******************/
	List<Product> getByProductNameStartsWith(String productName);

}
