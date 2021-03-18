package org.example.users.command;

public class ExitCommand implements ICommand {
    private static final String DESCRIPTION = "exit";
    private static final String ALL_USERS = "All users:";
    private static final String NOT_FOUND = "Users not found!";

    @Override
    public String getText() {
        return DESCRIPTION;
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
