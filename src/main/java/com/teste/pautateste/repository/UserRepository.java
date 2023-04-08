package com.teste.pautateste.repository;

import com.teste.pautateste.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
