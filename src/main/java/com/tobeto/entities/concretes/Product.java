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

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "critical_count")
	private int criticalCount;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "purchase_price")
	private double purchasePrice;

	@Column(name = "unit_price")
	private double unitPrice;

	@OneToMany(mappedBy = "product")
	private List<OrderDetails> orderDetails;

	@OneToMany(mappedBy = "product")
	private List<Shelf> shelfs;

	@OneToMany(mappedBy = "product")
	private List<InvoiceItem> invoiceItems;
}
