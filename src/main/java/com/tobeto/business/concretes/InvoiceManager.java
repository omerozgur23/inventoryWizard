package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.InvoiceService;
import com.tobeto.business.abstracts.OrderService;
import com.tobeto.business.abstracts.ProductService;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.InvoiceItemRepository;
import com.tobeto.dataAccess.InvoiceRepository;
import com.tobeto.entities.concretes.Customer;
import com.tobeto.entities.concretes.Invoice;
import com.tobeto.entities.concretes.InvoiceItem;
import com.tobeto.entities.concretes.Order;
import com.tobeto.entities.concretes.OrderDetails;
import com.tobeto.entities.concretes.PageResponse;
import com.tobeto.entities.concretes.Product;

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

	@Override
	@Transactional
	public Invoice create(UUID id) {

		Order order = orderService.getOrder(id);
		boolean invoiceGenerated = order.isInvoiceGenerated();

		if (invoiceGenerated)
			throw new BusinessException(Messages.INVOICE_ALREADY_EXIST);

		order.setInvoiceGenerated(true);

		Customer customer = order.getCustomer();
		double totalAmount = order.getOrderPrice();
		List<OrderDetails> orderDetails = order.getOrderDetails();

		List<InvoiceItem> invoiceItemsList = new ArrayList<InvoiceItem>();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String formattedDateTime = now.format(formatter);

		Invoice invoice = Invoice.builder().order(order).customer(customer).totalAmount(totalAmount)
				.waybillDate(formattedDateTime).build();
		Invoice savedInvoice = invoiceRepository.save(invoice);

		for (OrderDetails item : orderDetails) {
			Product product = productService.getProduct(item.getProduct().getId());
			InvoiceItem invoiceItem = InvoiceItem.builder().invoice(savedInvoice).product(product)
					.quantity(item.getQuantity()).unitPrice(item.getUnitPrice()).totalPrice(item.getTotalPrice())
					.build();
			invoiceItemsList.add(invoiceItem);
		}

		invoiceItemRepository.saveAll(invoiceItemsList);
		return savedInvoice;
	}

	@Override
	public Invoice update(Invoice entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub

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
	public List<Invoice> searchItem(String keyword) {
		return invoiceRepository.searchInvoice(keyword);
	}

}
