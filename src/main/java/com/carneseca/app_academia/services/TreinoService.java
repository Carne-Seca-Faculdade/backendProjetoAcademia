package com.carneseca.app_academia.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carneseca.app_academia.entities.TreinoEntity;
import com.carneseca.app_academia.repositories.TreinoRepository;

@Service
public class TreinoService {

    @Autowired
    private TreinoRepository treinoRepository;

    public List<TreinoEntity> findAll() {
        return treinoRepository.findAll();
    }

    public Optional<TreinoEntity> findById(UUID id) {
        return treinoRepository.findById(id);
    }

    public TreinoEntity save(TreinoEntity treino) {
        return treinoRepository.save(treino);
    }

    public void deleteById(UUID id) {
        treinoRepository.deleteById(id);
    }
}
