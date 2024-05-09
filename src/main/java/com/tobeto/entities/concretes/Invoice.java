package com.tobeto.entities.concretes;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(name = "total_amount")
	private double totalAmount;

	@Column(name = "waybill_date")
	private String waybillDate;

	@OneToMany(mappedBy = "invoice")
	private List<InvoiceItem> invoiceItems;

	@Column(name = "status")
	private boolean status = true;

}
