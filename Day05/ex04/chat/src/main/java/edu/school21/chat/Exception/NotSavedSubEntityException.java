package edu.school21.chat.Exception;

public class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException()
    {
        super("Cant find target!");
    }
}
