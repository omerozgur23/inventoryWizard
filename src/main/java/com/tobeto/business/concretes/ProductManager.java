package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.CustomerService;
import com.tobeto.business.abstracts.ProductService;
import com.tobeto.business.abstracts.UserService;
import com.tobeto.business.rules.product.ProductBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.OrderDetailsRepository;
import com.tobeto.dataAccess.OrderRepository;
import com.tobeto.dataAccess.ProductRepository;
import com.tobeto.dataAccess.ShelfRepository;
import com.tobeto.dto.SaleProductDTO;
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
	private ShelfRepository shelfRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private ProductBusinessRules productBusinessRules;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Product create(Product product) {
		return productRepository.save(product);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Product update(Product clientProduct) {
//		Product product = productRepository.findById(clientProduct.getId())
//				.orElseThrow(() -> new BusinessException(Messages.PRODUCT_ID_NOT_FOUND));
		Product product = getProduct(clientProduct.getId());

		product.setProductName(clientProduct.getProductName());
		product.setCriticalCount(clientProduct.getCriticalCount());
//		product.setCategory(clientProduct.getCategory());
//		product.setSupplier(clientProduct.getSupplier());
		product.setPurchasePrice(clientProduct.getPurchasePrice());
		product.setUnitPrice(clientProduct.getUnitPrice());

//		Product.builder().productName(clientProduct.getProductName())
//				.criticalQuantity(clientProduct.getCriticalQuantity()).purchasePrice(clientProduct.getPurchasePrice())
//				.unitPrice(clientProduct.getUnitPrice()).build();
		return productRepository.save(product);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
//		Product product = productRepository.findById(id).orElseThrow();
		Product product = getProduct(id);
		productRepository.delete(product);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Product> getAll() {
		List<Product> products = productRepository.findAll();
		return products;
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<Product> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return productRepository.findAll(pageable).getContent();
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

		productBusinessRules.setProductQuantity(productId, product);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void saleProduct(UUID productId, int count, UUID customerId, UUID userId) {
		Product product = getProduct(productId);
		Customer customer = customerService.getCustomer(customerId);
		User user = userService.getUser(userId);
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

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formattedDateTime = now.format(formatter);
		Order order = Order.builder().customer(customer).employee(user).orderDate(formattedDateTime).build();
		orderRepository.save(order);

		OrderDetails orderDetail = OrderDetails.builder().order(orderRepository.findById(order.getId()).orElseThrow())
				.product(product).quantity(count).unitPrice(product.getUnitPrice())
				.totalPrice(count * product.getUnitPrice()).build();
		orderDetailsRepository.save(orderDetail);

		productBusinessRules.setProductQuantity(productId, product);

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

	/******************* search deneme *******************/
	@Override
	public List<Product> getByProductNameStartsWith(String productName) {
		return productRepository.getByProductNameStartsWith(productName);
	}

	@Override
	@Transactional
	public void saleProductTest(List<SaleProductDTO> productItems, UUID customerId, UUID userId) {
		Customer customer = customerService.getCustomer(customerId);
		User user = userService.getUser(userId);
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formattedDateTime = now.format(formatter);

		Order order = Order.builder().customer(customer).employee(user).orderDate(formattedDateTime).build();

		for (SaleProductDTO productItem : productItems) {
			Product product = getProduct(productItem.getProductId());
			int[] remainingCount = new int[] { productItem.getCount() };

			shelfRepository.findByProductIdNotFull(productItem.getProductId()).ifPresent(shelf -> {
				int saleCount = Math.min(productItem.getCount(), shelf.getCount());
				shelf.setCount(shelf.getCount() - saleCount);

				productBusinessRules.clearShelf(shelf);
				shelfRepository.save(shelf);
				remainingCount[0] -= saleCount;

				OrderDetails orderDetail = OrderDetails.builder().order(order).product(product)
						.quantity(productItem.getCount()).unitPrice(product.getUnitPrice())
						.totalPrice(productItem.getCount() * product.getUnitPrice()).build();
				orderDetailsList.add(orderDetail);
			});

			if (remainingCount[0] > 0)
				productBusinessRules.fullShelfSaleProduct(remainingCount[0], product);

			orderRepository.save(order);
			orderDetailsRepository.saveAll(orderDetailsList);

			productBusinessRules.setProductQuantity(productItem.getProductId(), product);
		}
	}

}
