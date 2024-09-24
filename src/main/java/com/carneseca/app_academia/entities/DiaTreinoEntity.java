package com.carneseca.app_academia.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Dia_treino")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DiaTreinoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "Data_início")
    private Date dataInicio;

    @Column(name = "Data_fim")
    private Date dataFim;

    //Um treino pode ter vários dias
    @ManyToOne
    @JoinColumn(name = "id_treino")
    private TreinoEntity treino;

    //Muitos dias de treino pode ter uma única serie
    @ManyToOne
    @JoinColumn(name = "id_serie")
    private SerieEntity serie;

    //Um usuário pode ter muitos dias de treino
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;
}
