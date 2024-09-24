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

import com.carneseca.app_academia.entities.TreinoEntity;
import com.carneseca.app_academia.services.TreinoService;

@RestController
@RequestMapping("/api/treinos")
public class TreinoController {

    @Autowired
    private TreinoService treinoService;

    @GetMapping
    public List<TreinoEntity> getAllTreinos() {
        return treinoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreinoEntity> getTreinoById(@PathVariable UUID id) {
        Optional<TreinoEntity> treino = treinoService.findById(id);
        return treino.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TreinoEntity createTreino(@RequestBody TreinoEntity treino) {
        return treinoService.save(treino);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreinoEntity> updateTreino(@PathVariable UUID id, @RequestBody TreinoEntity treinoDetails) {
        Optional<TreinoEntity> existingTreino = treinoService.findById(id);
        if (existingTreino.isPresent()) {
            TreinoEntity treino = existingTreino.get();
            treino.setNomeTreino(treinoDetails.getNomeTreino());
            treino.setDescricao(treinoDetails.getDescricao());
            treino.setDuracao(treinoDetails.getDuracao());
            treino.setCriadoEm(treinoDetails.getCriadoEm());
            treino.setUsuario(treinoDetails.getUsuario());
            treino.setSeries(treinoDetails.getSeries());
            treino.setDiasTreino(treinoDetails.getDiasTreino());
            TreinoEntity updatedTreino = treinoService.save(treino);
            return ResponseEntity.ok(updatedTreino);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreino(@PathVariable UUID id) {
        if (treinoService.findById(id).isPresent()) {
            treinoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
