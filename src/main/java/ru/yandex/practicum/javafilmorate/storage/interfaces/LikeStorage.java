package ru.yandex.practicum.javafilmorate.storage.interfaces;

public interface LikeStorage {

    void addLike(int id, int userId);

    boolean removeLike(int id, int userId);

    boolean removeAllLikesByFilmId(int id);
}
