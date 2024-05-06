package com.tobeto.entities.concretes;

import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaybillInvoice {

	@Id
	@GeneratedValue
	private UUID id;

	private Customer customer;

	private String waybillDate;

//	@OneToMany(mappedBy = "waybillInvoice")
//	private List<Order> orders;
}
