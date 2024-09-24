package com.carneseca.app_academia.entities;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class DiaTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private Date dataInicio;
    private Date dataFim;

    //Um treino pode ter vários dias
    @ManyToOne
    @JoinColumn(name = "id_treino")
    private Treino treino;

    //Muitos dias de treino pode ter uma única serie
    @ManyToOne
    @JoinColumn(name = "id_serie")
    private Serie serie;

    //Um usuário pode ter muitos dias de treino
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
