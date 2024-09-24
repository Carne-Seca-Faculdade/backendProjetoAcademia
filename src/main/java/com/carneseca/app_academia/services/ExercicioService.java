package com.carneseca.app_academia.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carneseca.app_academia.entities.ExercicioEntity;
import com.carneseca.app_academia.repositories.ExercicioRepository;

@Service
public class ExercicioService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    public List<ExercicioEntity> findAll() {
        return exercicioRepository.findAll();
    }

    public Optional<ExercicioEntity> findById(UUID id) {
        return exercicioRepository.findById(id);
    }

    public ExercicioEntity save(ExercicioEntity exercicio) {
        return exercicioRepository.save(exercicio);
    }

    public void deleteById(UUID id) {
        exercicioRepository.deleteById(id);
    }
}
