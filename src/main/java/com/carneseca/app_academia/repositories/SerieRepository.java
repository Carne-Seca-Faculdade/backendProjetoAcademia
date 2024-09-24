package com.carneseca.app_academia.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carneseca.app_academia.entities.SerieEntity;

public interface SerieRepository extends JpaRepository<SerieEntity, UUID> {
    
}
