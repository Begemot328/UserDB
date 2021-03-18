package org.example.users.command;

import org.example.users.dom.impl.UserDOMImpl;
import org.example.users.entity.Role;
import org.example.users.entity.User;
import org.example.users.exceptions.CommandException;
import org.example.users.exceptions.ServiceException;
import org.example.users.exceptions.UserDOMException;
import org.example.users.service.UserService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AddUserCommand implements ICommand {
    private static final String DESCRIPTION = "add user command";
    private static final String TYPE = "Type";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String MAIL = "mail address";
    private static final String PHONE_NUMBER = "phone number";
    private static final String QUANTITY = "quantity";
    private static final String SPACE = " ";

    private static final String NOT_FOUND = "Users not found!";
    private static final String ROLES = "ROLES";
    private static final String PHONES = "PHONES";

    private PrintStream outputStream;
    private InputStream inputStream;
    private Scanner scanner;

    public AddUserCommand(PrintStream outputStream, InputStream inputStream) {
        this.outputStream = outputStream;
        this.scanner = new Scanner(inputStream);
    }
    @Override
    public String getText() {
        return DESCRIPTION;
    }

    @Override
    public void execute() throws CommandException {
        String firstname = readStringParameter(FIRSTNAME);
        String lastname = readStringParameter(LASTNAME);
        String mail = readStringParameter(MAIL);
        Set<String> phones = new HashSet<>();
        Set<Role> roles = new HashSet<>();

        int quantity = readQuantity(PHONES);
        for (int i = 1; i < quantity; i++) {
            phones.add(readStringParameter(PHONE_NUMBER + " #" + i ));
        }

        quantity = readQuantity(ROLES);
        for (int i = 1; i < quantity; i++) {
            phones.add(readStringParameter(PHONE_NUMBER + " #" + i ));
        }

        try {

        } catch (UserDOMException | ServiceException e) {
            throw new CommandException(e);
        }
    }

    private String readStringParameter(String name) {
            outputStream.println(TYPE + SPACE + name);
            return scanner.next();
    }

    private int readQuantity(String name) {
        while (true) {
            outputStream.println(TYPE + SPACE + name + SPACE + QUANTITY);
            if(scanner.hasNextInt()) {
                int i = scanner.nextInt();
                if (i > 0 && i < 10) {
                    return i;
                }
            }
        }
    }
}
