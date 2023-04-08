package com.teste.pautateste.service;

import com.teste.pautateste.model.Stave;
import com.teste.pautateste.repository.StaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaveService {

    private final StaveRepository staveRepository;

    @Autowired
    public StaveService(StaveRepository staveRepository) {
        this.staveRepository = staveRepository;
    }

    public List<Stave> getAll() {
        Iterable<Stave> iterable = staveRepository.findAll();
        List<Stave> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public void validateInsert() {

    }

    public Stave insert(Stave stave) {
        this.validateInsert();
        return staveRepository.save(stave);
    }

    public Stave getById(Integer id) {
        return staveRepository.findById(id).orElseThrow();
    }
}
