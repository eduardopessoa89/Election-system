package com.teste.pautateste.dto;

import com.teste.pautateste.model.Stave;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VotingDto {
    private Integer id;

    private Stave stave;

    private Boolean finished = false;

    private LocalDateTime startedAt = LocalDateTime.now();

    private LocalDateTime finishedAt;

    private List<VoteDto> votes;
}
