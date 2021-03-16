package org.example.users.controller;

import java.io.IOException;
import java.util.Scanner;


public class Controller implements IController {

	private final static String STRING = String.class.getName();

	private final static String ANYKEY = "Press any key to continue...";
	private final static String EMPTY = "is empty";
	private final static String ENTER_CORRECT_DATA = "Enter valid data!";
	private final static String COMMAND_DESCRIPTION = "Enter COMMAND to DO_SOMETHING";
	private final static String COMMAND = "COMMAND";
	private final static String DO_SOMETHING = "DO_SOMETHING";
	private final static String NO_SUCH_COMMAND = "No such comand";
	private final static String DATA_DESCRIPTION = "Enter DATA";
	private final static String DATA = "DATA";
	private final static String NO_RESULTS = "No notes found";
	private final static String FILENAME = "filename";
	private final static String WRONG_DATA_TYPE = "Wrong data type!";
	private final static String WRONG_COMMAND = "Wrong command!";
	private final static String FILE_NOT_FOUND = "File not found!";
	private final static String DEFAULT_FILENAME = "default";
	private final static String LOGIN = "login";
	private final static String PASSWORD = "password";
	private final static String WELCOME = "Welcome, USER FIRSTNAME LASTNAME!";
	private final static String FIRSTNAME = "FIRSTNAME";
	private final static String LASTNAME = "LASTNAME";
	private static final String GO_TO_MAIN_MENU = "go to main menu";
	private static final String EXIT = "exit";
	public static final String OPEN_NOTEBOOK = "open default notebook";
	public static final String SAVE_NOTEBOOK = "save notebook to default";
	public static final String OPEN_NOTEBOOK_FROM_FILE = "open notebook from file";
	public static final String NOTEBOOK_MENU = "work with notebook";
	public static final String SAVE_NOTEBOOK_TO_FILE = "save notebook to file";
	public static final String GO_TO_SEARCH_MENU = "go to search menu";
	public static final String NULL_NOTEBOOK = "notebook not found";
	public static final String SEARCH_RESET = "start a new search";
	public static final String SEARCH_BY_THEME = "search a note by theme";
	public static final String THEME = "theme";
	public static final String SEARCH_BY_MAIL = "search a note by mail adress";
	public static final String TEXT = "text";
	public static final String SEARCH_BY_TEXT = "search a note by text";
	public static final String DATE = "date";
	public static final String SEARCH_BY_DATE = "search a note by date";
	public static final String MAIL = "mail adress";
	public static final String ADD_NOTE = "add new note";
	
	private Scanner scanner;
	private NoteBook notebook;
	private Menu currentMenu;
	private Menu mainMenu;
	private Menu notesMenu;
	private Menu noteBookMenu;
	private Menu searchMenu;
	private NoteSearch list;
	private NoteBookLogic logic;
	
	
	public Controller() {
		scanner = new Scanner(System.in);
		logic = new NoteBookLogic(notebook);
		
		mainMenu = new Menu("Main menu");
		mainMenu.setCommand(new OpenCommand(), 1);
		mainMenu.setCommand(new SaveCommand(), 2);
		mainMenu.setCommand(new OpenFromFileCommand(), 3);
		mainMenu.setCommand(new SaveToFileCommand(), 4);
		mainMenu.setCommand(new NoteBookMenuCommand(), 5);
		mainMenu.setCommand(new ExitCommand(), 6);
		
		noteBookMenu = new Menu("Notebook menu");
		noteBookMenu.setCommand(new AddNoteCommand(), 1);
		noteBookMenu.setCommand(new SearchMenuCommand(), 2);
		noteBookMenu.setCommand(new MainMenuCommand(), 3);
		noteBookMenu.setCommand(new ExitCommand(), 4);
		
		searchMenu = new Menu("Search menu");

		searchMenu.setCommand(new SearchResetCommand(), 1);
		searchMenu.setCommand(new SearchByTextCommand(), 2);
		searchMenu.setCommand(new SearchByThemeCommand(), 3);
		searchMenu.setCommand(new SearchByDateCommand(), 4);
		searchMenu.setCommand(new SearchByMailCommand(), 5);
		searchMenu.setCommand(new SearchMenuCommand(), 6);
		searchMenu.setCommand(new MainMenuCommand(), 7);
		searchMenu.setCommand(new ExitCommand(), 8);
		
		currentMenu = mainMenu;
	}
	
	public void run() {
		while (true) {
			readCommand();
		}
	}
	
	private void readCommand() {
		ICommand command = null;
		writeMenu(currentMenu);   
		int commandCode = 0;
		if (scanner.hasNext()) {
			try {
				commandCode = Integer.parseInt(scanner.next());
			} catch (Exception e) {
				System.out.println(WRONG_COMMAND);
			}
			command = currentMenu.getCommand(commandCode);
			if (command == null) {
				System.out.println(NO_SUCH_COMMAND);
				return;
			}
		}
		command.execute();
	}

	private void writeMenu(Menu menu) {
		ICommand[] commands = new ICommand[menu.getCommands().length];
		commands = menu.getCommands().clone();
		for (int i = 1; i < commands.length; i++) {
			if (commands[i] == null) {
				continue;
			}
			System.out.println(COMMAND_DESCRIPTION.replace(COMMAND, i + "").replace(
								DO_SOMETHING, commands[i].getText()));
		}
	}
	
	@Override
	public Object getParameter(Class type, String name) {
		return readParameter(type, name);
	}

	private Object readParameter(Class type, String data) {
		while (true) {
		System.out.println(DATA_DESCRIPTION.replace(DATA, data));	
		switch (type.getName()) {
			case STRING:
				if(scanner.hasNext()) {
					return scanner.next();
				}
			case DOUBLE:
				try {
					if(scanner.hasNext()) {
						return Double.parseDouble(scanner.next());
					}
					
				} catch (NumberFormatException e) {
					System.out.println(WRONG_DATA_TYPE);
				}
				
				if(scanner.hasNextDouble()) {
					return scanner.nextDouble();
				} else {
					System.out.println(WRONG_DATA_TYPE);
					scanner.next();
				}
			}
		}
	}
	
	public void writeSearchResult(NoteSearch list)  {
		if (list == null || list.getResult().size() == 0) {
			System.out.println(NO_RESULTS);
			list = logic.searchAll();
			return;
		}
		for (Note note: list.getResult()) {
			System.out.println(note);
		}
	}
	
	// Commands- inner classes of commands
	
	public class SearchResetCommand implements ICommand {
		
		@Override
		public String getText() {
			return SEARCH_RESET;
		}

		@Override
		public void execute() {
			list = logic.searchAll();	
			writeSearchResult(list);
		}
	}
	
	public class SearchByThemeCommand implements ICommand {
		SearchIndex index;
		String theme;
		
		public SearchByThemeCommand() {
		}		
		
		public SearchByThemeCommand(String theme) {
			this.theme = theme;
		}
		
		@Override
		public String getText() {
			return SEARCH_BY_THEME;
		}

		@Override
		public void execute() {
			theme = (String) Controller.this.readParameter(Type.STRING, THEME);
			list = list.search(theme, SearchIndex.THEME) ;	
			writeSearchResult(list);
		}
	}
	
	public class SearchByMailCommand implements ICommand {
		SearchIndex index;
		String mail;
		
		public SearchByMailCommand() {
		}		
		
		public SearchByMailCommand(String mail) {
			this.mail = mail;
		}
		
		@Override
		public String getText() {
			return SEARCH_BY_MAIL;
		}

		@Override
		public void execute() {
			mail = (String) Controller.this.readParameter(Type.STRING, MAIL);
			list = list.search(mail, SearchIndex.MAIL) ;	
			writeSearchResult(list);
		}
	}
	
	public class SearchByTextCommand implements ICommand {
		SearchIndex index;
		String text;
		
		public SearchByTextCommand() {
		}		
		
		public SearchByTextCommand(String theme) {
			this.text = theme;
		}
		
		@Override
		public String getText() {
			return SEARCH_BY_TEXT;
		}

		@Override
		public void execute() {
			text = (String) Controller.this.readParameter(Type.STRING, TEXT);
			list = list.search(text, SearchIndex.TEXT) ;	
			writeSearchResult(list);
		}
	}
	
	public class SearchByDateCommand implements ICommand {
		SearchIndex index;
		String dateString;
		
		public SearchByDateCommand() {
		}		
		
		public SearchByDateCommand(String theme) {
			this.dateString = theme;
		}
		
		@Override
		public String getText() {
			return SEARCH_BY_DATE;
		}

		@Override
		public void execute() {
			dateString = (String) Controller.this.readParameter(Type.STRING, DATE);
			list = list.search(dateString, SearchIndex.DATE) ;	
			writeSearchResult(list);
		}
	}
	
	public class MainMenuCommand implements ICommand {
		
		@Override
		public String getText() {
			return GO_TO_MAIN_MENU;
		}

		@Override
		public void execute() {
			Controller.this.currentMenu = mainMenu;	
		}
	}

	public class AddNoteCommand implements ICommand {
		String theme;
		String text;
		String mail;
		String dateString;
		
		@Override
		public String getText() {
			return ADD_NOTE;
		}

		@Override
		public void execute() {
			while (true) {
				theme = (String) Controller.this.readParameter(Type.STRING, THEME);
				mail = (String) Controller.this.readParameter(Type.STRING, MAIL);
				text = (String) Controller.this.readParameter(Type.STRING, TEXT);
				dateString = (String) Controller.this.readParameter(Type.STRING, DATE);
				try {
					logic.addNote(theme, mail, dateString, text);
					return;
				} catch (InputException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public class SearchMenuCommand implements ICommand {
		
		@Override
		public String getText() {
			return GO_TO_SEARCH_MENU;
		}

		@Override
		public void execute() {
			list = logic.searchAll();
			Controller.this.currentMenu = searchMenu;	
		}
	}
	
	public class NoteBookMenuCommand implements ICommand {
		
		@Override
		public String getText() {
			return NOTEBOOK_MENU;
		}

		@Override
		public void execute() {
			if(notebook == null) {
				System.out.println(NULL_NOTEBOOK);
				return;
			}
			logic.setNotebook(notebook);
			Controller.this.currentMenu = noteBookMenu;	
		}
	}
	
	public class SaveCommand implements ICommand {
		
		@Override
		public String getText() {
			return SAVE_NOTEBOOK;
		}

		@Override
		public void execute() {
			new XMLDOMWriter(notebook).createXML();
		}
	}
	
	public class SaveToFileCommand implements ICommand {
		String filename;
		
		public SaveToFileCommand(String filename) {
			this.filename = filename;
		}
		
		public SaveToFileCommand() {
			
		}
		
		@Override
		public String getText() {
			return SAVE_NOTEBOOK_TO_FILE;
		}

		@Override
		public void execute() {
			if (filename == null) {
				filename = (String) getParameter(Type.STRING, FILENAME);
			}
			new XMLDOMWriter(notebook).createXML(filename);
		}
	}
	
	public class OpenCommand implements ICommand {
		
		@Override
		public String getText() {
			return OPEN_NOTEBOOK;
		}

		@Override
		public void execute() {
			notebook = new NoteBookBuilder().getNoteBook();
		}
	}
	
	public class OpenFromFileCommand implements ICommand {
		String filename;
		
		public OpenFromFileCommand(String filename) {
			this.filename = filename;
		}
		
		public OpenFromFileCommand() {
			
		}
		
		@Override
		public String getText() {
			return OPEN_NOTEBOOK_FROM_FILE;
		}

		@Override
		public void execute() {
			if (filename == null) {
				filename = (String) getParameter(Type.STRING, FILENAME);
			}
			notebook = new NoteBookBuilder().getNoteBook(filename);
		}
	}
	
	public class ExitCommand implements ICommand {
		
		@Override
		public String getText() {
			return EXIT;
		}

		@Override
		public void execute() {
			System.exit(0);
		}
	}
}
