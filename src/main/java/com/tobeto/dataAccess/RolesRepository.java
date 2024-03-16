package com.tobeto.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entities.concretes.Roles;

public interface RolesRepository extends JpaRepository<Roles, UUID> {

}
