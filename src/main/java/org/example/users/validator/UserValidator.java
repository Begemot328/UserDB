package org.example.users.validator;

import org.example.users.entity.User;
import org.example.users.exceptions.ValidationException;

public interface UserValidator {

    void validate(User user) throws ValidationException;
}
