package org.example.users.service;

import org.example.users.dom.UserDOM;
import org.example.users.entity.User;
import org.example.users.exceptions.ServiceException;
import org.example.users.exceptions.UserDOMException;
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

    public static UserService getInstance() throws ServiceException {
        if (INSTANCE.dom == null) {
            throw new ServiceException("DOM not configured");
        }
        return INSTANCE;
    }

    public static UserService getInstance(UserDOM dom) {
        INSTANCE.setDom(dom);
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

    public void save(User user) throws ServiceException {
        try {
            dom.save(user);
        } catch (UserDOMException e) {
            throw new ServiceException(e);
        }
    }

    public void create(User user) throws ServiceException {
        try {
            user.setId(user.hashCode());
            dom.save(user);
        } catch (UserDOMException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(User user) throws ServiceException {
        try {
            dom.delete(user);
        } catch (UserDOMException e) {
            throw new ServiceException(e);
        }
    }

    public User open(int id) throws ServiceException {
        try {
            return dom.open(id);
        } catch (UserDOMException e) {
            throw new ServiceException(e);
        }
    }

    public Set<User> findAll() throws ServiceException {
        try {
            return dom.findAll();
        } catch (UserDOMException e) {
            throw new ServiceException(e);
        }
    }

    public void saveAll (Set<User> set) throws ServiceException {
        for (User user : set) {
            save(user);
        }
    }
}
