package org.example.users.entity;

import java.util.List;
import java.util.Set;

public class UserFactory {

    public User createUser(String firstName, String lastName, String mail,
                           Set<String> phoneNumbers, Set<Role> roles) {
        return new User (firstName, lastName, mail, phoneNumbers, roles);
    }

    public User createUser(String firstName, String lastName, String mail) {
        return new User (firstName, lastName, mail);
    }


}
