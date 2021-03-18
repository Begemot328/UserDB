package org.example.users.entity;

import java.util.*;

public class User {

    private int id;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) && lastName.equals(user.lastName) && mail.equals(user.mail) && phoneNumbers.equals(user.phoneNumbers) && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, mail, phoneNumbers, roles);
    }

    public User clone() {
        return new User(firstName, lastName, mail, phoneNumbers, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", roles=" + roles +
                '}';
    }
}
