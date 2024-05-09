package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.entities.concretes.InvoiceItem;
import com.tobeto.entities.concretes.PageResponse;

public interface InvoiceDetailService {

	PageResponse<InvoiceItem> getAll();

	PageResponse<InvoiceItem> getByInvoiceId(UUID invoiceId, int pageNo, int pageSize);

	PageResponse<InvoiceItem> getAllByPage(int pageNo, int pageSize);
}
