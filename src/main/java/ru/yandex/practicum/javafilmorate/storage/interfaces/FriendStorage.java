package ru.yandex.practicum.javafilmorate.storage.interfaces;

import java.util.Collection;

public interface FriendStorage {

    void addToFriends(int fromUserId, int toUserId);

    boolean removeFromFriends(int fromUserId, int toUserId);

    Collection<Integer> getFriendIds(int id);
}
