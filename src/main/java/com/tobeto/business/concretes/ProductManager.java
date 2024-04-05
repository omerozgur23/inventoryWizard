package com.tobeto.business.concretes;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.ProductService;
import com.tobeto.business.rules.product.ProductBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.CustomerRepository;
import com.tobeto.dataAccess.OrderDetailsRepository;
import com.tobeto.dataAccess.OrderRepository;
import com.tobeto.dataAccess.ProductRepository;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.dataAccess.UserRepository;
import com.tobeto.dto.product.ProductWithCategoryResponse;
import com.tobeto.entities.concretes.Customer;
import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.concretes.OrderDetails;
import com.tobeto.entities.concretes.Product;
import com.tobeto.entities.concretes.User;

import jakarta.transaction.Transactional;

@Service
public class ProductManager implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductBusinessRules productBusinessRules;

	@Autowired
	private OrderRepository orderRepository;

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
		List<Product> products = productRepository.findAll();
		// products.forEach(product -> product.setQuantity(50));
		return products;
	}

	/**********************************************************************/
	/**********************************************************************/
	@Transactional
	public void acceptProduct(UUID productId, int count) {
		Product product = getProduct(productId);
		int[] remainingCount = new int[] { count };

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
	public void saleProduct(UUID productId, int count, UUID customerId, UUID userId) {
		Product product = getProduct(productId);
		Customer customer = getCustomer(customerId);
		User user = getUser(userId);
		int[] remainingCount = new int[] { count };

		shelfRepository.findByProductIdNotFull(productId).ifPresent(shelf -> {
			int saleCount = Math.min(count, shelf.getCount());
			shelf.setCount(shelf.getCount() - saleCount);

			productBusinessRules.clearShelf(shelf);
			shelfRepository.save(shelf);
			remainingCount[0] -= saleCount;

		});

		if (remainingCount[0] > 0)
			productBusinessRules.fullShelfSaleProduct(remainingCount[0], product);

		Order order = new Order();
		order.setCustomer(customer);
		order.setEmployee(user);
		order.setOrderDate(new Date(2024, 03, 26));
		orderRepository.save(order);

		OrderDetails od = new OrderDetails();
		od.setOrder(orderRepository.findById(order.getId()).orElseThrow());
		od.setProduct(product);
		od.setQuantity(count);
		od.setUnitPrice(product.getUnitPrice());
		orderDetailsRepository.save(od);

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

	private Customer getCustomer(UUID customerId) {
		Optional<Customer> oCustomer = customerRepository.findById(customerId);
		Customer customer = null;
		if (oCustomer.isPresent()) {
			customer = oCustomer.get();
		} else {
			throw new BusinessException(Messages.PRODUCT_ID_NOT_FOUND);
		}
		return customer;
	}

	private User getUser(UUID userId) {
		Optional<User> oUser = userRepository.findById(userId);
		User user = null;
		if (oUser.isPresent()) {
			user = oUser.get();
		} else {
			throw new BusinessException(Messages.PRODUCT_ID_NOT_FOUND);
		}
		return user;
	}

	@Override
	public List<Product> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return productRepository.findAll(pageable).getContent();
	}

	/******************* search deneme *******************/
	@Override
	public List<Product> getByProductNameStartsWith(String productName) {
		return productRepository.getByProductNameStartsWith(productName);
	}

}
