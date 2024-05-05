package com.tobeto.entities.concretes;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "role")
	private String role;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;
}
