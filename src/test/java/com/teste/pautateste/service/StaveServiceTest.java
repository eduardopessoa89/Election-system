package com.teste.pautateste.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teste.pautateste.model.Stave;
import com.teste.pautateste.model.Voting;
import com.teste.pautateste.repository.StaveRepository;
import com.teste.pautateste.exception.BusinessException;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StaveService.class})
@ExtendWith(SpringExtension.class)
class StaveServiceTest {
    @MockBean
    private StaveRepository staveRepository;

    @Autowired
    private StaveService staveService;

    @Test
    void testGetAll() {
        when(this.staveRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.staveService.getAll().isEmpty());
        verify(this.staveRepository).findAll();
    }

    @Test
    void testInsert() {
        Stave stave = new Stave();
        stave.setId(1);
        stave.setName("Name");
        stave.setVoting(new Voting());
        when(this.staveRepository.save((Stave) any())).thenReturn(stave);

        Stave stave1 = new Stave();
        stave1.setId(1);
        stave1.setName("Name");
        stave1.setVoting(new Voting());
        assertSame(stave, this.staveService.insert(stave1));
        verify(this.staveRepository).save((Stave) any());
    }

    @Test
    void testGetById() throws BusinessException {
        Stave stave = new Stave();
        stave.setId(1);
        stave.setName("Name");
        stave.setVoting(new Voting());
        Optional<Stave> ofResult = Optional.of(stave);
        when(this.staveRepository.findById(any())).thenReturn(ofResult);
        assertSame(stave, this.staveService.getById(1));
        verify(this.staveRepository).findById(any());
    }

    @Test
    void testGetById2() {
        when(this.staveRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> this.staveService.getById(1));
        verify(this.staveRepository).findById(any());
    }
}

