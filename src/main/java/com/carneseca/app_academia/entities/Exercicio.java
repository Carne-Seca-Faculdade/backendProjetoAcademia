package com.carneseca.app_academia.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String nomeExercicio;
    private String tipoExercicio;
    private String descricao;
    private String equipamento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
