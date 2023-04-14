package com.teste.pautateste.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Voting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "stave_id")
    private Stave stave;

    @Builder.Default
    private Boolean finished = false;

    @Builder.Default
    private LocalDateTime startedAt = LocalDateTime.now();

    private LocalDateTime finishedAt;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "voting")
    private List<Vote> votes = new ArrayList<>();
}