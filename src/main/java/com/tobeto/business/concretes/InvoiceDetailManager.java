package com.tobeto.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.InvoiceDetailService;
import com.tobeto.dataAccess.InvoiceItemRepository;
import com.tobeto.entities.concretes.InvoiceItem;
import com.tobeto.entities.concretes.PageResponse;

@Service
public class InvoiceDetailManager implements InvoiceDetailService {

	@Autowired
	private InvoiceItemRepository invoiceItemRepository;

	@Override
	public PageResponse<InvoiceItem> getAll() {
		List<InvoiceItem> invoiceDetails = invoiceItemRepository.findAll();
		int totalInvoiceDetailCount = invoiceItemRepository.findAll().size();
		return new PageResponse<InvoiceItem>(totalInvoiceDetailCount, invoiceDetails);
	}

	@Override
	public PageResponse<InvoiceItem> getByInvoiceId(UUID invoiceId) {
		List<InvoiceItem> invoiceDetails = invoiceItemRepository.getInvoiceDetailByInvoiceId(invoiceId);
		int totalInvoiceDetailCount = invoiceItemRepository.findAll().size();
		return new PageResponse<InvoiceItem>(totalInvoiceDetailCount, invoiceDetails);
	}

	@Override
	public PageResponse<InvoiceItem> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<InvoiceItem> invoiceDetails = invoiceItemRepository.findAll(pageable).getContent();
		int totalInvoiceDetailCount = invoiceItemRepository.findAll().size();
		return new PageResponse<InvoiceItem>(totalInvoiceDetailCount, invoiceDetails);
	}

}
