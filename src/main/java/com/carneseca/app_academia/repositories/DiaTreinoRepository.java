package com.carneseca.app_academia.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carneseca.app_academia.entities.DiaTreinoEntity;

@Repository
public interface DiaTreinoRepository extends JpaRepository<DiaTreinoEntity, UUID> {
}
