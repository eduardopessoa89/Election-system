package com.teste.pautateste.service;

import com.teste.pautateste.model.Stave;
import com.teste.pautateste.repository.StaveRepository;
import com.teste.pautateste.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaveService {

    private final StaveRepository staveRepository;

    public List<Stave> getAll() {
        List<Stave> iterable = staveRepository.findAll();
        List<Stave> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public Stave insert(Stave stave) {
        return staveRepository.save(stave);
    }

    public Stave getById(Integer id) throws BusinessException {
        try {
            return staveRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new BusinessException("Stave Not Exists");
        }
    }
}
