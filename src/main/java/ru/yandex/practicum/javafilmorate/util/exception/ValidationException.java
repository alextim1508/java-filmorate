package ru.yandex.practicum.javafilmorate.util.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
