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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

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

}
