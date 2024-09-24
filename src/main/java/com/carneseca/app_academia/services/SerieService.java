package com.carneseca.app_academia.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carneseca.app_academia.entities.SerieEntity;
import com.carneseca.app_academia.repositories.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieEntity> findAll() {
        return serieRepository.findAll();
    }

    public Optional<SerieEntity> findById(UUID id) {
        return serieRepository.findById(id);
    }

    public SerieEntity save(SerieEntity serie) {
        return serieRepository.save(serie);
    }

    public void deleteById(UUID id) {
        serieRepository.deleteById(id);
    }
}
