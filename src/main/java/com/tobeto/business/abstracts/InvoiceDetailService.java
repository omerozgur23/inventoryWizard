package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.InvoiceItem;

public interface InvoiceDetailService extends BaseService<InvoiceItem> {

	default InvoiceItem create(InvoiceItem entity) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default InvoiceItem update(InvoiceItem entity) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default void delete(UUID id) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default PageResponse<InvoiceItem> searchItem(String keyword) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	default PageResponse<InvoiceItem> getAllByPage(int pageNo, int pageSize) {
		throw new BusinessException(Messages.THIS_METHOD_DOES_NOT_WORK);
	}

	PageResponse<InvoiceItem> getByInvoiceId(UUID invoiceId, int pageNo, int pageSize);
}
