package com.teste.pautateste.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StaveDto {
    private Integer id;

    @NotEmpty(message = "Stave name is mandatory")
    private String name;
}