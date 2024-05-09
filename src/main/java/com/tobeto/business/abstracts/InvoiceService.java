package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

import com.tobeto.entities.concretes.Invoice;
import com.tobeto.entities.concretes.PageResponse;

public interface InvoiceService {

	Invoice create(UUID id);

	Invoice update(Invoice entity);

	Invoice invoiceCancellation(UUID invoiceId, UUID orderId);

	PageResponse<Invoice> getAll();

	PageResponse<Invoice> getAllByPage(int pageNo, int pageSize);

	List<Invoice> searchItem(String keyword);

	Invoice getInvoice(UUID invoiceId);;
}
