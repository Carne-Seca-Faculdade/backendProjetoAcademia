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
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private Date dataNascimento;
    private String genero;
    private float peso;
    private float altura;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ExercicioEntity> exercicios = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<TreinoEntity> treinos = new ArrayList<>();

}
