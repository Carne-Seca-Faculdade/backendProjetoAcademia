package com.carneseca.app_academia.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carneseca.app_academia.entities.TreinoEntity;

public interface TreinoRepository extends JpaRepository<TreinoEntity, UUID> {
    
}
