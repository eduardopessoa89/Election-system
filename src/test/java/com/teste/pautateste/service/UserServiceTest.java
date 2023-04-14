package com.teste.pautateste.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teste.pautateste.model.User;
import com.teste.pautateste.repository.UserRepository;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testGetAll() {
        Iterable<User> iterable = (Iterable<User>) mock(Iterable.class);
        doNothing().when(iterable).forEach((java.util.function.Consumer<? super User>) any());
        when(this.userRepository.findAll()).thenReturn(iterable);
        assertTrue(this.userService.getAll().isEmpty());
        verify(this.userRepository).findAll();
        verify(iterable).forEach((java.util.function.Consumer<? super User>) any());
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setCpf("Cpf");
        user.setId(1);
        user.setName("Name");
        when(this.userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setCpf("Cpf");
        user1.setId(1);
        user1.setName("Name");
        assertSame(user, this.userService.insert(user1));
        verify(this.userRepository).save((User) any());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setCpf("Cpf");
        user.setId(1);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(user, this.userService.getUserById(1));
        verify(this.userRepository).findById((Integer) any());
    }
}

