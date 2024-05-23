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
@Entity
@Builder
@Table(name = "order_details")
@EqualsAndHashCode(callSuper = true)
public class OrderDetails extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "unit_price")
	private double unitPrice;

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "invoiced_quantity")
	private int invoicedQuantity;
}
