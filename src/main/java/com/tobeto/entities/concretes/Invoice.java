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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	@NotBlank
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@NotNull
	@NotBlank
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@NotNull
	@NotBlank
	@Column(name = "total_amount")
	private double totalAmount;

	@NotNull
	@NotBlank
	@Column(name = "waybill_date")
	private String waybillDate;

	@OneToMany(mappedBy = "invoice")
	private List<InvoiceItem> invoiceItems;

}
