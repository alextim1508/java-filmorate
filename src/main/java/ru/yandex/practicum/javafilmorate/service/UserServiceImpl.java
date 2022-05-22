package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;
import ru.yandex.practicum.javafilmorate.util.exception.StorageOperationException;

import java.util.*;

import static ru.yandex.practicum.javafilmorate.util.util.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    private final Map<Integer, List<Integer>> friendsByUser = new HashMap<>();

    @Override
    public User create(User user) {
        if (user.getId() != 0)
            throw new StorageOperationException(SAVED_USER_STATUS_ERROR);

        try {
            userStorage.save(user);
        } catch (Exception e) {
            throw new StorageOperationException(USER_CREATION_ERROR);
        }

        friendsByUser.putIfAbsent(user.getId(), new ArrayList<>());

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
    public void addToFriends(int id, int friendId) {
        checkFriendsOperation(id, friendId);

        friendsByUser.get(id).add(friendId);
        friendsByUser.get(friendId).add(id);
    }

    @Override
    public void deleteFromFriends(int id, int friendId) {
        checkFriendsOperation(id, friendId);

        friendsByUser.get(id).remove(Integer.valueOf(friendId));
        friendsByUser.get(friendId).remove(Integer.valueOf(id));
    }

    private void checkFriendsOperation(int id, int friendId) {
        getById(id).orElseThrow();
        getById(friendId).orElseThrow();
    }

    @Override
    public Collection<User> getFriends(int id) {
        Collection<User> friends = new ArrayList<>();
        for (int friendId : this.friendsByUser.get(id)) {
            friends.add(getById(friendId).orElseThrow());
        }
        return friends;
    }

    @Override
    public Collection<User> getCommonFriends(int id, int otherId) {
        List<Integer> commonId = new ArrayList<>(friendsByUser.get(id));
        commonId.retainAll(friendsByUser.get(otherId));

        Collection<User> commonFriends = new ArrayList<>();
        for (int friendId : commonId) {
            commonFriends.add(getById(friendId).orElseThrow());
        }

        return commonFriends;
    }

    @Override
    public void update(User user) {
        if (user.getId() == 0)
            throw new StorageOperationException(UPDATED_USER_STATUS_ERROR);

        getById(user.getId()).orElseThrow();

        try {
            userStorage.save(user);
        } catch (Exception e) {
            throw new StorageOperationException(USER_UPDATE_ERROR);
        }
    }

    @Override
    public void delete(int id) {
        try {
            userStorage.deleteById(id);
        } catch (Exception e) {
            throw new StorageOperationException(USER_DELETION_ERROR);
        }

        friendsByUser.remove(id);
        friendsByUser.values().forEach(userFriends -> userFriends.remove(id));
    }
}
