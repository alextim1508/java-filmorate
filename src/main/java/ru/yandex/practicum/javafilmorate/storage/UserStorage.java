package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User save(User user);

    User findById(int id);

    Collection<User> findAll();

    void deleteById(int id);
}
