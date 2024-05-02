package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

import com.tobeto.dto.product.ProductItemDTO;
import com.tobeto.entities.concretes.Product;

public interface ProductService extends BaseService<Product> {

	void acceptProduct(UUID productId, int count);

	void saleProduct(List<ProductItemDTO> productItems, UUID customerId, UUID userId);
}
