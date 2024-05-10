package com.tobeto.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.InvoiceService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.invoice.GetAllInvoiceResponse;
import com.tobeto.entities.concretes.Invoice;
import com.tobeto.entities.concretes.PageResponse;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoicesController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@RequestBody UUID id) {
		invoiceService.create(id);
		return new SuccessResponse();
	}

	@PostMapping("/cancellation")
	public SuccessResponse invoiceCancellation(@RequestBody UUID id) {
		invoiceService.invoiceCancellation(id);
		return new SuccessResponse();
	}

	@GetMapping("getall")
	public PageResponse<GetAllInvoiceResponse> getAll() {
		PageResponse<Invoice> invoicePage = invoiceService.getAll();
		List<GetAllInvoiceResponse> responseList = invoicePage.getData().stream()
				.map(invoice -> modelMapper.forResponse().map(invoice, GetAllInvoiceResponse.class)).toList();
		return new PageResponse<GetAllInvoiceResponse>(invoicePage.getCount(), responseList);
	}

	@GetMapping("/getallByPage")
	public PageResponse<GetAllInvoiceResponse> getAllInvoiceByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<Invoice> invoicePage = invoiceService.getAllByPage(pageNo, pageSize);
		List<GetAllInvoiceResponse> responseList = invoicePage.getData().stream()
				.map(invoice -> modelMapper.forResponse().map(invoice, GetAllInvoiceResponse.class)).toList();

		return new PageResponse<>(invoicePage.getCount(), responseList);
	}

	@GetMapping("/search")
	public List<GetAllInvoiceResponse> searchInvoice(@RequestParam String keyword) {
		List<Invoice> invoices = invoiceService.searchItem(keyword);
		return invoices.stream().map(invoice -> modelMapper.forResponse().map(invoice, GetAllInvoiceResponse.class))
				.toList();
	}

}
