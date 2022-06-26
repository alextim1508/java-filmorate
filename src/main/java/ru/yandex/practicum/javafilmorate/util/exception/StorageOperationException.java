package ru.yandex.practicum.javafilmorate.util.exception;

public class StorageOperationException extends RuntimeException {

    public StorageOperationException(String message, Exception exception) {
        super(message, exception);
    }
}
