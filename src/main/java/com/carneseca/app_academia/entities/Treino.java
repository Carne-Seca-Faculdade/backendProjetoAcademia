package com.carneseca.app_academia.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="tb_Treino")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String nomeTreino;
    private String descricao;
    private float duracao;
    private Date criadoEm;

    //Muitos treinos podem estar associados a um único usuário
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    //Um treino pode ter muitas séries
    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL)
    private List<Serie> series = new ArrayList<>();

    //Um treino pode ter muitos dias de treino
    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL)
    private List<DiaTreino> diasTreino = new ArrayList<>();
}
