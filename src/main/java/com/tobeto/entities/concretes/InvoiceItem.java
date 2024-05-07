package com.tobeto.entities.concretes;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "invoice_items")
public class InvoiceItem {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@NotNull
	@NotBlank
	@ManyToOne
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;

	@NotNull
	@NotBlank
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@NotNull
	@NotBlank
	@Column(name = "quantity")
	private int quantity;

	@NotNull
	@NotBlank
	@Column(name = "unit_price")
	private double unitPrice;

	@NotNull
	@NotBlank
	@Column(name = "total_amount")
	private double totalPrice;

}
