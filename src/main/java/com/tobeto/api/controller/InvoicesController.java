package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.InvoiceService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.PaginationRequest;
import com.tobeto.dto.SearchRequest;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.invoice.CancellationInvoiceRequest;
import com.tobeto.dto.invoice.CreateInvoiceRequest;
import com.tobeto.dto.invoice.GetAllInvoiceResponse;
import com.tobeto.entities.concretes.Invoice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoicesController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@Valid @RequestBody CreateInvoiceRequest request) {
		invoiceService.create(request.getOrderId(), request.getProductItems());
		return new SuccessResponse();
	}

	@PostMapping("/cancellation")
	public SuccessResponse invoiceCancellation(@Valid @RequestBody CancellationInvoiceRequest request) {
		invoiceService.invoiceCancellation(request.getInvoiceId());
		return new SuccessResponse();
	}

	@GetMapping("getall")
	public PageResponse<GetAllInvoiceResponse> getAll() {
		PageResponse<Invoice> invoicePage = invoiceService.getAll();
		List<GetAllInvoiceResponse> responseList = invoicePage.getData().stream()
				.map(invoice -> modelMapper.forResponse().map(invoice, GetAllInvoiceResponse.class)).toList();
		return new PageResponse<GetAllInvoiceResponse>(invoicePage.getCount(), responseList);
	}

	@PostMapping("/getallByPage")
	public PageResponse<GetAllInvoiceResponse> getAllInvoiceByPage(@Valid @RequestBody PaginationRequest request) {
		PageResponse<Invoice> invoicePage = invoiceService.getAllByPage(request.getPageNo(), request.getPageSize());
		List<GetAllInvoiceResponse> responseList = invoicePage.getData().stream()
				.map(invoice -> modelMapper.forResponse().map(invoice, GetAllInvoiceResponse.class)).toList();
		return new PageResponse<>(invoicePage.getCount(), responseList);
	}

	@PostMapping("/search")
	public PageResponse<GetAllInvoiceResponse> searchInvoice(@RequestBody SearchRequest request) {
		PageResponse<Invoice> invoicePage = invoiceService.searchItem(request.getKeyword());
		List<GetAllInvoiceResponse> responseList = invoicePage.getData().stream()
				.map(invoice -> modelMapper.forResponse().map(invoice, GetAllInvoiceResponse.class)).toList();
		return new PageResponse<GetAllInvoiceResponse>(invoicePage.getCount(), responseList);
	}

}
