package com.tobeto.business.rules.invoice;

import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.entities.concretes.Invoice;

@Service
public class InvoiceBusinessRules {

	public void isStatusFalse(Invoice invoice) {
		if (!invoice.isStatus()) {
			throw new BusinessException(Messages.INVOICE_STATUS_ALREADY_FALSE);
		}
	}
}
