package com.teste.pautateste.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewVotingDto {
    @NotNull(message = "Stave is mandatory")
    private Integer staveID;
    private Integer duration = 60;
}