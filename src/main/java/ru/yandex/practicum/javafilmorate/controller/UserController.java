package ru.yandex.practicum.javafilmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.interfaces.UserService;
import ru.yandex.practicum.javafilmorate.util.exception.ValidationException;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult result) {
        if(result.getErrorCount() != 0) {
            log.error("{}", result.getAllErrors());
            throw new ValidationException("ValidationException");
        }

        userService.create(user);
        log.info("User {} is created", user);

        return user;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        return userService.getById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @PutMapping("{id}/friends/{friendId}")
    public void addToFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.addToFriends(id, friendId);
        log.info("User with ID {} has been added as a friend to a user with ID {}", id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteToFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFromFriends(id, friendId);
        log.info("User with ID {} has been removed as a friend to a user with ID {}", id, friendId);
    }

    @GetMapping("{id}/friends")
    public Collection<User> getFriends(@PathVariable int id) {
        Collection<User> friends = userService.getFriends(id);
        log.info("Friends of user with id {} are {}", id, friends);
        return friends;
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user, BindingResult result) {
        if(result.getErrorCount() != 0) {
            log.error("{}", result.getAllErrors());
            throw new ValidationException("ValidationException");
        }

        userService.update(user);
        log.info("User {} is updated", user);

        return user;
    }
}
