package org.example.users.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private String firstName;
    private String lastName;
    private String mail;
    private final Set<String> phoneNumbers;
    private final Set<Role> roles;

    User (String firstName, String lastName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumbers = new HashSet<>();
        this.roles = new HashSet<>();
    }

     User (String firstName, String lastName, String mail, Set<String> phoneNumbers, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumbers = phoneNumbers;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
