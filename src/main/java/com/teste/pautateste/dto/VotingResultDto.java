package com.teste.pautateste.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VotingResultDto {
    private Integer inFavor = 0;
    private Integer against = 0;
    private Integer total;
    private StaveDto stave;
}