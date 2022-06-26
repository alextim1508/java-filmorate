package ru.yandex.practicum.javafilmorate.storage.interfaces;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User save(User user);

    User findById(int id);

    Collection<User> findAll();

    int update(User user);

    boolean deleteById(int id);
}
