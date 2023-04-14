package com.teste.pautateste.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "vote")
@Table(name = "votes")
@Data
public class Vote {

    @EmbeddedId
    private VoteID id;

    @ManyToOne
    @MapsId("votingID")
    private Voting voting;

    private Boolean value;
}
