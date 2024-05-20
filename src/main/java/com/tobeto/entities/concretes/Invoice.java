package com.tobeto.entities.concretes;

import java.util.List;

import com.tobeto.entities.abstracts.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
@EqualsAndHashCode(callSuper = true)
public class Invoice extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(name = "total_amount")
	private double totalAmount;

	@OneToMany(mappedBy = "invoice")
	private List<InvoiceItem> invoiceItems;
}
