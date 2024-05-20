package com.tobeto.entities.concretes;

import com.tobeto.entities.abstracts.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "invoice_items")
@EqualsAndHashCode(callSuper = true)
public class InvoiceItem extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "unit_price")
	private double unitPrice;

	@Column(name = "total_amount")
	private double totalPrice;
}
