package com.teste.pautateste.dto;

import com.teste.pautateste.model.VoteID;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteDto {

    private VoteID id;

    @NotNull(message = "Vote is mandatory")
    private Boolean value;

}
