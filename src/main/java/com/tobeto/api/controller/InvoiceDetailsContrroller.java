package com.tobeto.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.InvoiceDetailService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.invoiceDetail.GetAllInvoiceDetailsResponse;
import com.tobeto.entities.concretes.InvoiceItem;
import com.tobeto.entities.concretes.PageResponse;

@RestController
@RequestMapping("api/v1/invoiceDetail")
public class InvoiceDetailsContrroller {

	@Autowired
	private InvoiceDetailService invoiceDetailService;

	@Autowired
	private ModelMapperService modelMapper;

	@GetMapping("/getall")
	public PageResponse<GetAllInvoiceDetailsResponse> getAll() {
		PageResponse<InvoiceItem> invoiceDetailPage = invoiceDetailService.getAll();
		List<GetAllInvoiceDetailsResponse> responseList = invoiceDetailPage.getData().stream()
				.map(invoiceDetail -> modelMapper.forResponse().map(invoiceDetail, GetAllInvoiceDetailsResponse.class))
				.toList();
		return new PageResponse<>(invoiceDetailPage.getCount(), responseList);
	}

	@GetMapping("/getallByPage")
	public PageResponse<GetAllInvoiceDetailsResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<InvoiceItem> invoiceDetailPage = invoiceDetailService.getAllByPage(pageNo, pageSize);
		List<GetAllInvoiceDetailsResponse> responseList = invoiceDetailPage.getData().stream()
				.map(invoiceDetail -> modelMapper.forResponse().map(invoiceDetail, GetAllInvoiceDetailsResponse.class))
				.toList();

		return new PageResponse<>(invoiceDetailPage.getCount(), responseList);
	}

	@GetMapping("/getByInvoiceId")
	public PageResponse<GetAllInvoiceDetailsResponse> getByInvoiceId(@RequestParam UUID invoiceId,
			@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<InvoiceItem> invoiceDetailPage = invoiceDetailService.getByInvoiceId(invoiceId, pageNo, pageSize);
		List<GetAllInvoiceDetailsResponse> responseList = invoiceDetailPage.getData().stream()
				.map(invoiceDetail -> modelMapper.forResponse().map(invoiceDetail, GetAllInvoiceDetailsResponse.class))
				.toList();
		return new PageResponse<>(invoiceDetailPage.getCount(), responseList);
	}

//	@GetMapping("/getByInvoiceId")
//	public PageResponse<GetAllInvoiceDetailsResponse> getByInvoiceId(@RequestParam UUID invoiceId) {
//		PageResponse<InvoiceItem> invoiceDetailPage = invoiceDetailService.getByInvoiceId(invoiceId);
//		List<GetAllInvoiceDetailsResponse> responseList = invoiceDetailPage.getData().stream()
//				.map(invoiceDetail -> modelMapper.forResponse().map(invoiceDetail, GetAllInvoiceDetailsResponse.class))
//				.toList();
//		return new PageResponse<>(invoiceDetailPage.getCount(), responseList);
//	}

}
