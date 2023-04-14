package com.teste.pautateste.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "stave")
@Data
public class Stave {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToOne
    @JoinColumn(name = "voting_id")
    private Voting voting;
}
