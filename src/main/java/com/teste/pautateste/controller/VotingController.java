package com.teste.pautateste.controller;

import com.teste.pautateste.dto.NewVotingDto;
import com.teste.pautateste.dto.VotingDto;
import com.teste.pautateste.dto.VotingResultDto;
import com.teste.pautateste.model.Voting;
import com.teste.pautateste.service.VotingService;
import com.teste.pautateste.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.teste.pautateste.utils.MapperUtil.convert;

@RestController
@RequestMapping(value = "/voting")
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;

    @PostMapping("/start")
    public VotingDto startVoting(@Valid @RequestBody NewVotingDto newVotingDto) throws BusinessException {
        Voting voting = votingService.start(newVotingDto);
        return convert(voting, VotingDto.class);
    }

    @GetMapping("/{id}")
    public VotingResultDto getVotingById(@PathVariable Integer id) {
        return votingService.getVotingResult(id);
    }
}
