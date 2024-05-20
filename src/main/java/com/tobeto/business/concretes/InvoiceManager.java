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
import com.tobeto.dto.PageResponse;
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
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private InvoiceBusinessRules invoiceBusinessRules;

	@Override
	@Transactional
	public Invoice create(UUID orderId) {

		Order order = orderService.getOrder(orderId);
		invoiceBusinessRules.isOrderStatusFalse(order);
		invoiceBusinessRules.isInvoiceAlreadyExıst(order);
		order.setInvoiceGenerated(true);

		Customer customer = order.getCustomer();
		double totalAmount = order.getOrderPrice();
		List<OrderDetails> orderDetails = order.getOrderDetails();

		List<InvoiceItem> invoiceItemsList = new ArrayList<InvoiceItem>();
		LocalDateTime now = LocalDateTime.now();

		Invoice invoice = Invoice.builder().order(order).customer(customer).totalAmount(totalAmount).build();
		invoice.setCreatedDate(now);
		invoice.setStatus(Status.ACTIVE);
		Invoice savedInvoice = invoiceRepository.save(invoice);

		for (OrderDetails item : orderDetails) {
			Product product = productService.getProduct(item.getProduct().getId());
			InvoiceItem invoiceItem = InvoiceItem.builder().invoice(savedInvoice).product(product)
					.quantity(item.getQuantity()).unitPrice(item.getUnitPrice()).totalPrice(item.getTotalPrice())
					.build();
			invoiceItem.setCreatedDate(now);
			invoiceItem.setStatus(Status.ACTIVE);
			invoiceItemsList.add(invoiceItem);
		}

		invoiceItemRepository.saveAll(invoiceItemsList);
		return savedInvoice;
	}

	@Override
	@Transactional
	public void invoiceCancellation(UUID invoiceId) {
		Invoice invoice = getInvoice(invoiceId);
		invoiceBusinessRules.isStatusFalse(invoice);
		invoice.setStatus(Status.INACTIVE);

		for (InvoiceItem invoiceItems : invoice.getInvoiceItems()) {
			invoiceItems.setStatus(Status.INACTIVE);
		}
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
