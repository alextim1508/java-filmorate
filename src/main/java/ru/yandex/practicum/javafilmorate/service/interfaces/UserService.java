package ru.yandex.practicum.javafilmorate.service.interfaces;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<User> getById(int id);

    Collection<User> getAll();

    void update(User user);

    void delete(int id);

    void addToFriends(int id, int friendId);

    void deleteFromFriends(int id, int friendId);

    Collection<User> getFriends(int id);

    Collection<User> getCommonFriends(int id, int otherId);
}
