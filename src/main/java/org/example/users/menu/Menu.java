package org.example.users.menu;

import org.example.users.controller.ICommand;
import org.example.users.controller.IController;

public class Menu {
	
	private IController controller;
	private ICommand[] commands = new ICommand[10];
	private String name;
	
	public Menu(String name) {
		this.name = name;
	}
	
	public ICommand getCommand(int i) {
		if (i <= 0 || i >= 10) {
			return null;
		}
		return commands[i];
	} 
	
	public ICommand[] getCommands() {
		return commands;
	}
	
	public void setCommand(ICommand command, int i) {
		if (i <= 0 || i >= 10) {
			return;
		}
		commands[i] = command;
	}
}
