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

import com.carneseca.app_academia.entities.DiaTreinoEntity;
import com.carneseca.app_academia.services.DiaTreinoService;

@RestController
@RequestMapping("/api/dia-treinos")
public class DiaTreinoController {

    @Autowired
    private DiaTreinoService diaTreinoService;

    @GetMapping
    public List<DiaTreinoEntity> getAllDiaTreinos() {
        return diaTreinoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaTreinoEntity> getDiaTreinoById(@PathVariable UUID id) {
        Optional<DiaTreinoEntity> diaTreino = diaTreinoService.findById(id);
        return diaTreino.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DiaTreinoEntity createDiaTreino(@RequestBody DiaTreinoEntity diaTreino) {
        return diaTreinoService.save(diaTreino);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaTreinoEntity> updateDiaTreino(@PathVariable UUID id, @RequestBody DiaTreinoEntity diaTreinoDetails) {
        Optional<DiaTreinoEntity> existingDiaTreino = diaTreinoService.findById(id);
        if (existingDiaTreino.isPresent()) {
            DiaTreinoEntity diaTreino = existingDiaTreino.get();
            diaTreino.setDataInicio(diaTreinoDetails.getDataInicio());
            diaTreino.setDataFim(diaTreinoDetails.getDataFim());
            diaTreino.setTreino(diaTreinoDetails.getTreino());
            diaTreino.setSerie(diaTreinoDetails.getSerie());
            diaTreino.setUsuario(diaTreinoDetails.getUsuario());
            DiaTreinoEntity updatedDiaTreino = diaTreinoService.save(diaTreino);
            return ResponseEntity.ok(updatedDiaTreino);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaTreino(@PathVariable UUID id) {
        if (diaTreinoService.findById(id).isPresent()) {
            diaTreinoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
