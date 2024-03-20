package com.tobeto.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entities.concretes.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, UUID> {

}
