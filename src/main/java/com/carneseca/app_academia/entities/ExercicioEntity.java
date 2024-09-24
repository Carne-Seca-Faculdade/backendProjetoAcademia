package com.carneseca.app_academia.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ExercicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String nomeExercicio;
    private String tipoExercicio;
    private String descricao;
    private String equipamento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;
}
