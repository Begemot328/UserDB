package org.example.users.command;

import org.example.users.dom.impl.UserDOMImpl;
import org.example.users.entity.User;
import org.example.users.exceptions.CommandException;
import org.example.users.exceptions.ServiceException;
import org.example.users.exceptions.UserDOMException;
import org.example.users.service.UserService;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;

public class ReadAllUsersCommand implements ICommand {
    private static final String DESCRIPTION = "read all users";
    private static final String ALL_USERS = "All users:";
    private static final String NOT_FOUND = "Users not found!";

    private PrintStream outputStream;

    public ReadAllUsersCommand(PrintStream outputStream) {
        this.outputStream = outputStream;
    }
    @Override
    public String getText() {
        return DESCRIPTION;
    }

    @Override
    public void execute() throws CommandException {
        try {
            Set<User> result = UserService.getInstance(new UserDOMImpl()).findAll();

            if (result.isEmpty()) {
              outputStream.println(NOT_FOUND);
            }
            outputStream.println(ALL_USERS);
            int i = 0;
            for (User user: result) {
                outputStream.println(++i + " " + user.toString());
            }
        } catch (UserDOMException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
