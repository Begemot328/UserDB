package org.example.users.validator.impl;

import org.example.users.entity.Role;
import org.example.users.entity.User;
import org.example.users.exceptions.ValidationException;
import org.example.users.validator.UserValidator;
import java.util.regex.Pattern;


public class UserValidatorImpl implements UserValidator {
    private static final String MAIL_PATTERN = "\\S+@\\S+\\.\\S+";
    private static final String PHONE_PATTERN = "375\\s*([0-9]){2}\\s*([0-9]){5}";

    private static Pattern mailPattern = Pattern.compile(MAIL_PATTERN);
    private static Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    @Override
    public void validate(User user) throws ValidationException {
        if (isEmpty(user.getFirstName())) {
            throw new ValidationException("invalid first name");
        }
        if (isEmpty(user.getLastName())) {
            throw new ValidationException("invalid last name");
        }
        if (isEmpty(user.getMail()) || !mailPattern.matcher(user.getMail()).matches()) {
            throw new ValidationException("invalid mail address " + user.getMail());
        }
        validateRoles(user);
        validatePhones(user);
    }

    private void validatePhones(User user) throws ValidationException {
        if (user.getPhoneNumbers() == null || user.getPhoneNumbers().isEmpty()) {
            throw new ValidationException("empty phone list");
        }
        if (user.getPhoneNumbers().size() < 1 || user.getPhoneNumbers().size() > 3) {
            throw new ValidationException("phone numbers quantity must be from 1 to 3");
        }
        for (String number : user.getPhoneNumbers()) {
            if (isEmpty(number) || !phonePattern.matcher(number).matches()) {
                throw new ValidationException("invalid phone number " + number);
            }
        }

    }

    private void validateRoles(User user) throws ValidationException {
        int[] quantity = new int[Role.values().length];

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new ValidationException("empty role");
        }
        if (user.getRoles().contains(Role.SUPER_ADMIN) && user.getRoles().size() > 1) {
            throw new ValidationException("super-admin must be the only role");
        }

        for (Role role: user.getRoles()) {
            quantity[role.getLevel()] += 1;
        }
        for (int i : quantity) {
            if (i > 1) {
                throw new ValidationException("multiple roles of level " + i);
            }
        }
    }

    private boolean isEmpty(String line) {
        return line == null || line.isEmpty();
    }
}
