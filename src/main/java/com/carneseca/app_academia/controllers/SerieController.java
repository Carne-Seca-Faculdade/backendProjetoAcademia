package com.carneseca.app_academia.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carneseca.app_academia.entities.SerieEntity;
import com.carneseca.app_academia.services.SerieService;

@RestController
@RequestMapping("/api/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieEntity> getAllSeries() {
        return serieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SerieEntity> getSerieById(@PathVariable UUID id) {
        Optional<SerieEntity> serie = serieService.findById(id);
        return serie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SerieEntity createSerie(@RequestBody SerieEntity serie) {
        return serieService.save(serie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SerieEntity> updateSerie(@PathVariable UUID id, @RequestBody SerieEntity serieDetails) {
        Optional<SerieEntity> existingSerie = serieService.findById(id);
        if (existingSerie.isPresent()) {
            SerieEntity serie = existingSerie.get();
            serie.setRepeticoes(serieDetails.getRepeticoes());
            serie.setCarga(serieDetails.getCarga());
            serie.setDescansoSerie(serieDetails.getDescansoSerie());
            serie.setTreino(serieDetails.getTreino());
            SerieEntity updatedSerie = serieService.save(serie);
            return ResponseEntity.ok(updatedSerie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable UUID id) {
        if (serieService.findById(id).isPresent()) {
            serieService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
