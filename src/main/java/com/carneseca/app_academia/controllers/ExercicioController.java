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

import com.carneseca.app_academia.entities.ExercicioEntity;
import com.carneseca.app_academia.services.ExercicioService;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioService exercicioService;

    @GetMapping
    public List<ExercicioEntity> getAllExercicios() {
        return exercicioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExercicioEntity> getExercicioById(@PathVariable UUID id) {
        Optional<ExercicioEntity> exercicio = exercicioService.findById(id);
        return exercicio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ExercicioEntity createExercicio(@RequestBody ExercicioEntity exercicio) {
        return exercicioService.save(exercicio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExercicioEntity> updateExercicio(@PathVariable UUID id, @RequestBody ExercicioEntity exercicioDetails) {
        Optional<ExercicioEntity> existingExercicio = exercicioService.findById(id);
        if (existingExercicio.isPresent()) {
            ExercicioEntity exercicio = existingExercicio.get();
            exercicio.setNomeExercicio(exercicioDetails.getNomeExercicio());
            exercicio.setTipoExercicio(exercicioDetails.getTipoExercicio());
            exercicio.setDescricao(exercicioDetails.getDescricao());
            exercicio.setEquipamento(exercicioDetails.getEquipamento());
            exercicio.setUsuario(exercicioDetails.getUsuario());
            ExercicioEntity updatedExercicio = exercicioService.save(exercicio);
            return ResponseEntity.ok(updatedExercicio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercicio(@PathVariable UUID id) {
        if (exercicioService.findById(id).isPresent()) {
            exercicioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
