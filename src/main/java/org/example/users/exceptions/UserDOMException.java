package org.example.users.exceptions;

public class UserDOMException extends ProjectException {

    public UserDOMException(String message) {
        super(message);
    }

    public UserDOMException(String message, Exception e) {
        super(message, e);
    }

    public UserDOMException(Exception e) {
        super(e);
    }
}
