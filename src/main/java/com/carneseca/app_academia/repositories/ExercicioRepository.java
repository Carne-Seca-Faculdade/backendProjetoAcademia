package com.carneseca.app_academia.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carneseca.app_academia.entities.ExercicioEntity;

public interface ExercicioRepository extends JpaRepository<ExercicioEntity, UUID> {
    
}
