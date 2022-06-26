package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.interfaces.UserService;
import ru.yandex.practicum.javafilmorate.storage.interfaces.FriendStorage;
import ru.yandex.practicum.javafilmorate.storage.interfaces.UserStorage;
import ru.yandex.practicum.javafilmorate.util.exception.StorageOperationException;

import java.util.*;

import static ru.yandex.practicum.javafilmorate.util.util.ErrorMessage.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public UserServiceImpl(@Qualifier("userDbStorage") UserStorage userStorage,
                           FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    @Override
    public User create(User user) {
        try {
            userStorage.save(user);
        } catch (Exception e) {
            throw new StorageOperationException(USER_CREATION_ERROR, e);
        }

        return user;
    }

    @Override
    public Optional<User> getById(int id) {
        return Optional.ofNullable(userStorage.findById(id));
    }

    @Override
    public Collection<User> getAll() {
        return userStorage.findAll();
    }

    @Override
    public void update(User user) {
        if(userStorage.update(user) == 0)
            throw new EmptyResultDataAccessException(1);
    }

    @Override
    public void delete(int id) {
        if(!userStorage.deleteById(id)) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    @Override
    public void addToFriends(int id, int friendId) {
        friendStorage.addToFriends(id, friendId);
    }

    @Override
    public void deleteFromFriends(int id, int friendId) {
        friendStorage.removeFromFriends(id, friendId);
    }

    @Override
    public Collection<User> getFriends(int id) {
        Collection<User> friends = new ArrayList<>();
        for (int friendId : friendStorage.getFriendIds(id)) {
            friends.add(getById(friendId).orElseThrow());
        }
        return friends;
    }

    @Override
    public Collection<User> getCommonFriends(int id, int otherId) {
        List<Integer> commonId = new ArrayList<>(friendStorage.getFriendIds(id));
        commonId.retainAll(friendStorage.getFriendIds(otherId));

        Collection<User> commonFriends = new ArrayList<>();
        for (int friendId : commonId) {
            commonFriends.add(getById(friendId).orElseThrow());
        }

        return commonFriends;
    }
}
