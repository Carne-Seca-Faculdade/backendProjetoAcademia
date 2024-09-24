package com.carneseca.app_academia.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class TreinoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String nomeTreino;
    private String descricao;
    private float duracao;
    private Date criadoEm;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL)
    private List<SerieEntity> series = new ArrayList<>();

    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL)
    private List<DiaTreinoEntity> diasTreino = new ArrayList<>();
}
