package com.teste.pautateste.controller;

import com.teste.pautateste.dto.StaveDto;
import com.teste.pautateste.model.Stave;
import com.teste.pautateste.service.StaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.teste.pautateste.utils.MapperUtil.convert;
import static com.teste.pautateste.utils.MapperUtil.convertList;

@RestController
@RequestMapping(value = "/stave")
@RequiredArgsConstructor
public class StaveController {

    private final StaveService staveService;

    @GetMapping
    public List<StaveDto> getAll() {
        return convertList(staveService.getAll(), StaveDto.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Stave create(@Valid @RequestBody StaveDto staveDto) {
        return staveService.insert(convert(staveDto, Stave.class));
    }
}
