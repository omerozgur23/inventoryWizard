package com.tobeto.entities.concretes;

import java.util.List;

import com.tobeto.entities.abstracts.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private User employee;

	@Column(name = "order_date")
	private String orderDate;

	@Column(name = "order_price")
	private double orderPrice;

	@OneToMany(mappedBy = "order")
	private List<OrderDetails> orderDetails;

	@OneToMany(mappedBy = "order")
	private List<Invoice> invoice;

	@Column(name = "invoice_generated")
	private boolean invoiceGenerated = false;
}
