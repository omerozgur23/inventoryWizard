package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
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
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.product.ProductItemDTO;
import com.tobeto.entities.concretes.Customer;
import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.concretes.OrderDetails;
import com.tobeto.entities.concretes.Product;
import com.tobeto.entities.concretes.User;
import com.tobeto.entities.enums.Status;

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

	@Override
	public Product create(Product product) {
		product.setCreatedDate(LocalDateTime.now());
		product.setStatus(Status.ACTIVE);
		return productRepository.save(product);
	}

	@Override
	public Product update(Product clientProduct) {
		Product product = getProduct(clientProduct.getId());
		BeanUtils.copyProperties(clientProduct, product, "quantity", "createdDate", "status");
		return productRepository.save(product);
	}

	@Override
	public void delete(UUID id) {
		Product product = getProduct(id);
		product.setStatus(Status.INACTIVE);
		product.setInactiveDate(LocalDateTime.now());
		productRepository.save(product);
	}

	@Override
	public PageResponse<Product> getAll() {
		List<Product> products = productRepository.findAll();
		int totalProductsCount = productRepository.findAll().size();
		return new PageResponse<>(totalProductsCount, products);
	}

	@Override
	public PageResponse<Product> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Product> products = productRepository.findAll(pageable).getContent();
		int totalProductsCount = productRepository.findAll().size();
		return new PageResponse<>(totalProductsCount, products);
	}

	@Override
	public PageResponse<Product> searchItem(String keyword) {
		List<Product> products = productRepository.searchProducts(keyword);
		int totalProductsCount = productRepository.searchProducts(keyword).size();
		return new PageResponse<Product>(totalProductsCount, products);
	}

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

	@Override
	@Transactional
	public void saleProduct(List<ProductItemDTO> productItems, UUID customerId, UUID userId) {
		Customer customer = customerService.getCustomer(customerId);
		User user = userService.getUser(userId);
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String formattedDateTime = now.format(formatter);

		Order order = Order.builder().customer(customer).employee(user).orderDate(formattedDateTime).build();
		order.setCreatedDate(now);
		order.setStatus(Status.ACTIVE);

		// Yarı dolu raftan satış
		for (ProductItemDTO productItem : productItems) {
			Product product = getProduct(productItem.getProductId());
			int[] remainingCount = new int[] { productItem.getCount() };

			shelfRepository.findByProductIdNotFull(productItem.getProductId()).ifPresent(shelf -> {
				int saleCount = Math.min(productItem.getCount(), shelf.getCount());
				shelf.setCount(shelf.getCount() - saleCount);

				productBusinessRules.clearShelf(shelf);
				shelfRepository.save(shelf);
				remainingCount[0] -= saleCount;
			});

			// Tam dolu raftan satış
			if (remainingCount[0] > 0) {
				productBusinessRules.fullShelfSaleProduct(remainingCount[0], product);
			}
			OrderDetails orderDetail = OrderDetails.builder().order(order).product(product)
					.quantity(productItem.getCount()).unitPrice(product.getUnitPrice())
					.totalPrice(productItem.getCount() * product.getUnitPrice()).build();
			orderDetail.setCreatedDate(now);
			orderDetail.setStatus(Status.ACTIVE);
			orderDetailsList.add(orderDetail);

			productBusinessRules.setProductQuantity(productItem.getProductId(), product);
		}
		double totalPriceSum = orderDetailsList.stream().mapToDouble(od -> od.getTotalPrice()).sum();
		order.setOrderPrice(totalPriceSum);
		orderRepository.save(order);
		orderDetailsRepository.saveAll(orderDetailsList);
	}

	@Override
	public Product getProduct(UUID productId) {
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
