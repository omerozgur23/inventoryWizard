package com.tobeto.entities.concretes;

import java.util.List;

import com.tobeto.entities.abstracts.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
@EqualsAndHashCode(callSuper = true)
public class Supplier extends BaseEntity {

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "contact_email")
	private String contactEmail;

	@Column(name = "contact_phone")
	private String contactPhone;

	@Column(name = "tax_number")
	private String taxNumber;

	@Column(name = "address")
	private String address;

	@OneToMany(mappedBy = "supplier")
	private List<Product> products;
}
