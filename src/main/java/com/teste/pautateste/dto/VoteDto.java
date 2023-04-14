package com.teste.pautateste.dto;

import com.teste.pautateste.model.VoteID;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VoteDto {

    private VoteID id;

    @NotEmpty(message = "Vote is mandatory")
    private Boolean value;

}
