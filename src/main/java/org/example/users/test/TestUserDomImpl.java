package org.example.users.test;

import org.example.users.dom.UserDOM;
import org.example.users.dom.impl.UserDOMImpl;
import org.example.users.entity.Role;
import org.example.users.entity.User;
import org.example.users.entity.UserFactory;
import org.example.users.exceptions.UserDOMException;

import java.util.HashSet;
import java.util.Set;

public class TestUserDomImpl {
    public static void main(String[] args) throws UserDOMException {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.SUPER_ADMIN);
        Set<String> numbers = new HashSet<>();
        numbers.add("375333063326");
        User user = new UserFactory().createUser(
                "Ivan", "Ivanov", "adf@fsdf.sdf", numbers, roles);
        System.out.println(user.hashCode());
        User user1 = new UserFactory().createUser(
                "Ivan5", "Ivanov5", "adf@fsdf5.sdf", numbers, roles);
        System.out.println(user1.hashCode());

        user.setId(user.hashCode());
        user1.setId(user1.hashCode());
        UserDOM dom =
                new UserDOMImpl();
        dom.save(user);
        dom.save(user1);

        dom.delete(user1);


        for (User user2 : dom.findAll()) {
            System.out.println(user2);
        }


    }
}