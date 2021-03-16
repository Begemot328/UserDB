package org.example.users.controller;

import java.lang.reflect.Type;

public interface IController {
	
	public Object getParameter(Class classname, String name);

	public void run();
}
