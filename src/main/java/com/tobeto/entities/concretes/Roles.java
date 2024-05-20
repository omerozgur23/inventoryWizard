package com.tobeto.entities.concretes;

import java.util.List;

import com.tobeto.entities.abstracts.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
public class Roles extends BaseEntity {

	@Column(name = "role")
	private String role;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;
}
