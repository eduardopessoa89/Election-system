package com.teste.pautateste.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;

    @NotEmpty(message = "User name is mandatory")
    private String name;
    @Size(min = 11, max = 11, message = "CPF must contain 11 characters")
    private String cpf;
}
