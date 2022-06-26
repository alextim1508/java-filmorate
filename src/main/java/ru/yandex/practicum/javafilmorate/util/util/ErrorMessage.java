package ru.yandex.practicum.javafilmorate.util.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class ErrorMessage {

    public static final String SAVED_USER_STATUS_ERROR = "The user ID must be zero";
    public static final String UPDATED_USER_STATUS_ERROR = "The user ID must not be zero";

    public static final String USER_CREATION_ERROR = "The user creation error";
    public static final String USER_UPDATE_ERROR = "The user update error";
    public static final String USER_DELETION_ERROR = "The user deletion error";

    public static final String SAVED_FILM_STATUS_ERROR = "The film ID must be zero";
    public static final String UPDATED_FILM_STATUS_ERROR = "The film ID must not be zero";

    public static final String FILM_CREATION_ERROR = "The film creation error";
    public static final String FILM_UPDATE_ERROR = "The film update error";
    public static final String FILM_DELETION_ERROR = "The film deletion error";
}
