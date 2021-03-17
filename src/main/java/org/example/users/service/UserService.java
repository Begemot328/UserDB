package org.example.users.service;

import org.example.users.dom.UserDOM;
import org.example.users.entity.User;
import org.example.users.validator.UserValidator;
import org.example.users.validator.impl.UserValidatorImpl;

import java.util.HashSet;
import java.util.Set;

public class UserService {

    private final static UserService INSTANCE = new UserService();
    private UserDOM dom;
    private Set<UserValidator> validators = new HashSet<>();

    private UserService() {
        addValidator(new UserValidatorImpl());
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public void setDom(UserDOM dom) {
        this.dom = dom;
    }

    public void addValidator(UserValidator validator) {
        validators.add(validator);
    }

    public void removeValidator(UserValidator validator) {
        validators.remove(validator);
    }

    public void save(User user) {}

    public void create(User user) {}

    public void delete(User user) {}

    public User open(int id) {
        return null;
    }

    public Set<User> findAll() {
        return new HashSet<User>();
    }

    public void saveAll (Set<User> set) {
        for (User user : set) {
            save(user);
        }
    }
}
