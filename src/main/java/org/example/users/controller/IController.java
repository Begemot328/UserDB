package org.example.users.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

public interface IController {
	
	public Object getParameter(Class classname, String name);

	public InputStream getReader();

	public OutputStream getWriter();

	public void run();
}
