package com.tobeto.dataAccess;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tobeto.entities.concretes.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, UUID>, JpaSpecificationExecutor<InvoiceItem> {

	List<InvoiceItem> findByInvoiceId(UUID invoiceId, Pageable pageable);
}
