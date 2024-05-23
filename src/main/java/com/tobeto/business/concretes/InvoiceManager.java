package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.InvoiceService;
import com.tobeto.business.abstracts.OrderService;
import com.tobeto.business.abstracts.ProductService;
import com.tobeto.business.rules.invoice.InvoiceBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.InvoiceItemRepository;
import com.tobeto.dataAccess.InvoiceRepository;
import com.tobeto.dataAccess.OrderDetailsRepository;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.product.ProductItemDTO;
import com.tobeto.entities.concretes.Customer;
import com.tobeto.entities.concretes.Invoice;
import com.tobeto.entities.concretes.InvoiceItem;
import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.concretes.OrderDetails;
import com.tobeto.entities.concretes.Product;
import com.tobeto.entities.enums.Status;

import jakarta.transaction.Transactional;

@Service
public class InvoiceManager implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InvoiceItemRepository invoiceItemRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private InvoiceBusinessRules invoiceBusinessRules;

	@Override
	@Transactional
	public Invoice create(UUID orderId, List<ProductItemDTO> productList) {

		Order order = orderService.getOrder(orderId);
		invoiceBusinessRules.isOrderStatusInactive(order);
		invoiceBusinessRules.isInvoiceAlreadyExıst(order);

		Customer customer = order.getCustomer();
		double totalAmount = 0.0;
		List<OrderDetails> orderDetails = order.getOrderDetails();

		List<InvoiceItem> invoiceItemsList = new ArrayList<InvoiceItem>();
		LocalDateTime now = LocalDateTime.now();

		Invoice invoice = Invoice.builder().order(order).customer(customer).build();
		invoice.setCreatedDate(now);
		invoice.setStatus(Status.ACTIVE);

		for (ProductItemDTO requestProduct : productList) {
			Product invoiceProduct = productService.getProduct(requestProduct.getProductId());
			double productTotalPrice = invoiceProduct.getUnitPrice() * requestProduct.getCount();

			totalAmount += productTotalPrice;
			invoice.setTotalAmount(totalAmount);

			InvoiceItem invoiceItem = InvoiceItem.builder().invoice(invoice).product(invoiceProduct)
					.quantity(requestProduct.getCount()).unitPrice(invoiceProduct.getUnitPrice())
					.totalPrice(invoiceProduct.getUnitPrice() * requestProduct.getCount()).build();
			invoiceItem.setCreatedDate(now);
			invoiceItem.setStatus(Status.ACTIVE);
			invoiceItemsList.add(invoiceItem);

			for (OrderDetails orderDetail : orderDetails) {
				if (orderDetail.getProduct().getId().equals(requestProduct.getProductId())) {
					int newInvoicedQuantity = orderDetail.getInvoicedQuantity() + requestProduct.getCount();
					if (newInvoicedQuantity > orderDetail.getQuantity()) {
						throw new BusinessException("Hata");
					}
					orderDetail.setInvoicedQuantity(newInvoicedQuantity);
				}
			}

		}
		invoiceRepository.save(invoice);
		invoiceBusinessRules.isInvoicedQuantityFull(orderDetails);
		orderDetailsRepository.saveAll(orderDetails);
		invoiceItemRepository.saveAll(invoiceItemsList);
		return invoice;
	}

	@Override
	@Transactional
	public void invoiceCancellation(UUID invoiceId) {
		Invoice invoice = getInvoice(invoiceId);
		invoiceBusinessRules.isStatusInactive(invoice);
		invoice.setStatus(Status.INACTIVE);
		invoice.setInactiveDate(LocalDateTime.now());

		for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
			invoiceItem.setStatus(Status.INACTIVE);
			invoiceItem.setInactiveDate(LocalDateTime.now());

			OrderDetails orderDetail = orderDetailsRepository.findByOrderIdAndProductId(invoice.getOrder().getId(),
					invoiceItem.getProduct().getId());
			if (orderDetail != null) {
				int newInvoicedQuantity = orderDetail.getInvoicedQuantity() - invoiceItem.getQuantity();
				orderDetail.setInvoicedQuantity(newInvoicedQuantity);

				if (newInvoicedQuantity <= 0) {
					orderDetail.setStatus(Status.INACTIVE);
					productService.acceptProduct(orderDetail.getProduct().getId(), orderDetail.getQuantity());
				}
			}
		}

		invoiceRepository.save(invoice);
		invoiceItemRepository.saveAll(invoice.getInvoiceItems());
		orderDetailsRepository.saveAll(invoice.getOrder().getOrderDetails());
		orderService.invoiceCancellation(invoice.getOrder().getId());
	}

	@Override
	public PageResponse<Invoice> getAll() {
		List<Invoice> invoices = invoiceRepository.findAll();
		int totalInvoicesCount = invoiceRepository.findAll().size();
		return new PageResponse<Invoice>(totalInvoicesCount, invoices);
	}

	@Override
	public PageResponse<Invoice> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Invoice> invoices = invoiceRepository.findAll(pageable).getContent();
		int totalInvoiceCount = invoiceRepository.findAll().size();
		return new PageResponse<Invoice>(totalInvoiceCount, invoices);
	}

	@Override
	public PageResponse<Invoice> searchItem(String keyword) {
		List<Invoice> invoices = invoiceRepository.searchInvoice(keyword);
		int totalInvoiceCount = invoiceRepository.searchInvoice(keyword).size();
		return new PageResponse<Invoice>(totalInvoiceCount, invoices);
	}

	@Override
	public Invoice getInvoice(UUID invoiceId) {
		Optional<Invoice> oInvoice = invoiceRepository.findById(invoiceId);
		Invoice invoice = null;
		if (oInvoice.isPresent()) {
			invoice = oInvoice.get();
		} else {
			throw new BusinessException(Messages.INVOICE_ID_NOT_FOUND);
		}
		return invoice;
	}
}
