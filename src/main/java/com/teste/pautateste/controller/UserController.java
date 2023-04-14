package com.teste.pautateste.controller;

import com.teste.pautateste.dto.UserDto;
import com.teste.pautateste.model.User;
import com.teste.pautateste.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.teste.pautateste.utils.MapperUtil.convert;
import static com.teste.pautateste.utils.MapperUtil.convertList;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDto> getAll() {
        return convertList(service.getAll(), UserDto.class);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        User user = service.insert(convert(userDto, User.class));
        return convert(user, UserDto.class);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
        User user = service.getUserById(id);
        return convert(user, UserDto.class);
    }

}
