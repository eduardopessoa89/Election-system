package com.teste.pautateste.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Table(name = "`user`")
@Data
@Getter
@Setter
@Entity(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public User(Integer id) {
        this.id = id;
    }

    public User() {
    }
}
