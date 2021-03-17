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
                "Ivan", "Ivanov" , "adf@fsdf.sdf", numbers, roles);
        user.setId(user.hashCode());
        UserDOM dom =
        new UserDOMImpl();
        dom.save(user);
        dom.open(user.getId());

        System.out.println(dom.open(user.getId()));
        System.out.println(user.equals(dom.open(user.getId())));


    }
}