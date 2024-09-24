package com.carneseca.app_academia.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_Usuario")
public class Usuario {

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
    private List<Exercicio> exercicios = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Treino> treinos = new ArrayList<>();

}
