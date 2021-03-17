package org.example.users.dom;

import org.example.users.entity.User;
import org.example.users.exceptions.UserDOMException;

import java.util.Set;

public interface UserDOM {

    public void save(User user) throws UserDOMException;

    public void delete(User user) throws UserDOMException;

    public User open(int id) throws UserDOMException;

    public Set<User> findAll() throws UserDOMException;

    public void saveAll (Set<User> set) throws UserDOMException;
}
