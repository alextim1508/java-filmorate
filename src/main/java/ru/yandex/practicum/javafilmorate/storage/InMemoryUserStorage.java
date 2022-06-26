package ru.yandex.practicum.javafilmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> storage;
    private int id;

    public InMemoryUserStorage() {
        this.storage = new HashMap<>();
    }

    public InMemoryUserStorage(Map<Integer, User> storage) {
        this.storage = storage;
    }

    @Override
    public User save(User user) {

        if (user.getId() == 0) {
            user.setId(++id);
            storage.put(id, user);
        } else {
            storage.put(id, user);
        }

        return user;
    }

    @Override
    public User findById(int id) {
        return storage.get(id);
    }

    @Override
    public Collection<User> findAll() {
        return storage.values();
    }

    @Override
    public int update(User user) {
        if(storage.containsKey(user.getId())) {
            storage.put(user.getId(), user);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean deleteById(int id) {
        return storage.remove(id) != null;
    }
}
