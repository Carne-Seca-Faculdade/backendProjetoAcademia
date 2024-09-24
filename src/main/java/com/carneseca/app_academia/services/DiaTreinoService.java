package com.carneseca.app_academia.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carneseca.app_academia.entities.DiaTreinoEntity;
import com.carneseca.app_academia.repositories.DiaTreinoRepository;

@Service
public class DiaTreinoService {

    @Autowired
    private DiaTreinoRepository diaTreinoRepository;

    public List<DiaTreinoEntity> findAll() {
        return diaTreinoRepository.findAll();
    }

    public Optional<DiaTreinoEntity> findById(UUID id) {
        return diaTreinoRepository.findById(id);
    }

    public DiaTreinoEntity save(DiaTreinoEntity diaTreino) {
        return diaTreinoRepository.save(diaTreino);
    }

    public void deleteById(UUID id) {
        diaTreinoRepository.deleteById(id);
    }
}
