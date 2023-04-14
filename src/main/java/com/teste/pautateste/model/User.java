package com.teste.pautateste.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity(name = "user")
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String cpf;
}
