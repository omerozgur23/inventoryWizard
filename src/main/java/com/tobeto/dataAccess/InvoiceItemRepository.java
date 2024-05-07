package com.tobeto.dataAccess;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entities.concretes.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, UUID> {

	@Query("SELECT i FROM InvoiceItem i WHERE i.invoice.id = :invoiceId")
	List<InvoiceItem> getInvoiceDetailByInvoiceId(UUID invoiceId);
}
