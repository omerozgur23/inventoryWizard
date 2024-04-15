package com.tobeto.entities.concretes;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shelf")
public class Shelf {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "count")
	private int count;

	@Column(name = "capacity")
	private int capacity;

	@ManyToOne
	private Product product;
}
