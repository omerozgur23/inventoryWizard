package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dto.product.ProductItemDTO;
import com.tobeto.entities.concretes.Invoice;

public interface InvoiceService extends BaseService<Invoice> {

	default Invoice create(Invoice entity) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	};

	default Invoice update(Invoice entity) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	};

	default void delete(UUID id) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	};

	Invoice create(UUID id, List<ProductItemDTO> productId);

	void invoiceCancellation(UUID id);

	Invoice getInvoice(UUID invoiceId);
}
