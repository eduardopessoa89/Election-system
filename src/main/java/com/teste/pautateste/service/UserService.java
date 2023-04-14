package com.teste.pautateste.service;

import com.teste.pautateste.model.User;
import com.teste.pautateste.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    public User insert(User user) {
        return repository.save(user);
    }

    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
