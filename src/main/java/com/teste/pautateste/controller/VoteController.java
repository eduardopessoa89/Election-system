package com.teste.pautateste.controller;

import com.teste.pautateste.dto.VoteDto;
import com.teste.pautateste.model.Vote;
import com.teste.pautateste.service.VoteService;
import com.teste.pautateste.utils.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.teste.pautateste.utils.MapperUtil.convert;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService service;

    @PostMapping
    public HttpStatus vote(@Valid @RequestBody VoteDto voteDto) throws BusinessException {
        service.voteFor(convert(voteDto, Vote.class));
        return HttpStatus.CREATED;
    }
}
