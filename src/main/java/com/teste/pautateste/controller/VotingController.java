package com.teste.pautateste.controller;

import com.teste.pautateste.dto.NewVotingDto;
import com.teste.pautateste.dto.VotingDto;
import com.teste.pautateste.dto.VotingResultDto;
import com.teste.pautateste.model.Voting;
import com.teste.pautateste.service.VotingService;
import com.teste.pautateste.utils.BusinessException;
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
        try {
            Voting voting = votingService.start(newVotingDto);
            return convert(voting, VotingDto.class);
        } catch (Error e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public VotingResultDto getVotingById(@PathVariable Integer id) throws BusinessException {
        try {
            return votingService.getVotingResult(id);
        } catch (Error e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
