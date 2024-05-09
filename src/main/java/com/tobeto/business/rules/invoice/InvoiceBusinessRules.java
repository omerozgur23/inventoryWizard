package com.tobeto.business.rules.invoice;

import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.entities.concretes.Invoice;
import com.tobeto.entities.concretes.Order;

@Service
public class InvoiceBusinessRules {

	public void isStatusFalse(Invoice invoice) {
		if (!invoice.isStatus()) {
			throw new BusinessException(Messages.INVOICE_STATUS_ALREADY_FALSE);
		}
	}

	public void isOrderStatusFalse(Order order) {
		if (!order.isOrderStatus()) {
			throw new BusinessException(Messages.AN_INVOICE_CANNOT_BE_CRATED_FOR_CANCELED_ORDER);
		}
	}

	public void isInvoiceAlreadyExıst(Order order) {
		if (order.isInvoiceGenerated()) {
			throw new BusinessException(Messages.INVOICE_ALREADY_EXIST);
		}
	}
}
