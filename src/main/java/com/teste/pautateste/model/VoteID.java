package com.teste.pautateste.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class VoteID implements Serializable {

    private Integer userID;

    private Integer votingID;
}
