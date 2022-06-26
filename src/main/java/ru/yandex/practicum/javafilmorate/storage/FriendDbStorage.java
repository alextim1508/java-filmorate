package ru.yandex.practicum.javafilmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.interfaces.FriendStorage;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addToFriends(int fromUserId, int toUserId) {
        String sqlQuery = "insert into friends (user_from_id, user_to_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery, fromUserId, toUserId);
    }

    @Override
    public boolean removeFromFriends(int fromUserId, int toUserId) {
        String sqlQuery = "delete from friends where user_from_id = ? and user_to_id = ?";
        return jdbcTemplate.update(sqlQuery, fromUserId, toUserId) > 0;
    }

    @Override
    public Collection<Integer> getFriendIds(int id) {
        String sqlQuery = "select user_to_id from friends where user_from_id = ?";
        return jdbcTemplate.queryForList(sqlQuery, Integer.class, id);
    }
}
