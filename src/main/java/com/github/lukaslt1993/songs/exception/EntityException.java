package com.github.lukaslt1993.songs.exception;

public abstract class EntityException extends RuntimeException {

    public EntityException(String message, Object id) {
        super(String.format("%s; id = %s", message, id));
    }
}
