package org.example.users.command;

import org.example.users.exceptions.CommandException;

public interface ICommand {
	
	public String getText();
	
	public void execute() throws CommandException;
}
