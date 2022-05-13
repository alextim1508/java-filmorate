package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.util.exception.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int id;

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult result) {
        if(result.getErrorCount() != 0) {
            log.error("{}", result.getAllErrors());
            throw new ValidationException("ValidationException");
        }

        user.setId(id);
        users.put(id, user);
        log.info("User {} is created", user);
        id++;
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        users.put(user.getId(), user);
        log.info("User {} is updated", user);
        return user;
    }

}
