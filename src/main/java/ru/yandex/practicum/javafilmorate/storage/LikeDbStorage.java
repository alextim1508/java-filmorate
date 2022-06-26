package ru.yandex.practicum.javafilmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.interfaces.LikeStorage;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(int id, int userId) {
        jdbcTemplate.update("insert into likes(user_id, film_id) values (?, ?)", userId, id);
    }

    @Override
    public boolean removeLike(int id, int userId) {
        return jdbcTemplate.update("delete from likes where user_id = ? and film_id = ?", userId, id) > 0;
    }

    @Override
    public boolean removeAllLikesByFilmId(int id) {
        return jdbcTemplate.update("delete from likes where user_id = ?", id) > 0;
    }
}
