package com.carneseca.app_academia.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private Integer repeticoes;
    private Integer carga;
    private float descansoSerie;

    @ManyToOne
    @JoinColumn(name = "id_treino")
    private Treino treino;

}
