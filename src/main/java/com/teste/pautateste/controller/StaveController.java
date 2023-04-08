package com.teste.pautateste.controller;

import com.teste.pautateste.dto.StaveDTO;
import com.teste.pautateste.model.Stave;
import com.teste.pautateste.service.StaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.teste.pautateste.utils.MapperUtil.convert;
import static com.teste.pautateste.utils.MapperUtil.convertList;

@RestController
@RequestMapping(value = "stave")
public class StaveController {

    private final StaveService staveService;

    @Autowired
    public StaveController(StaveService staveService) {
        this.staveService = staveService;
    }

    @GetMapping("/{id}")
    public StaveDTO getById(@PathVariable Integer id) {
        return convert(staveService.getById(id), StaveDTO.class);
    }

    @GetMapping("/")
    public List<StaveDTO> getAll() {
        return convertList(staveService.getAll(), StaveDTO.class);
    }

    @PostMapping("/create")
    public Stave create(@RequestBody StaveDTO staveDto) {
        return staveService.insert(convert(staveDto, Stave.class));
    }
}
