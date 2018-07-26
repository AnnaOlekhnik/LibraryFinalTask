package by.htp.library.controller;



import by.htp.library.dao.BookDao;
import by.htp.library.dao.impl.BookDaoForDB;
import by.htp.library.entity.User;
import by.htp.library.scanner.ConsoleScanner;

public class MainConsole {

	private static ConsoleScanner scanner;
	private static User user;
	
	public static void main(String[] args) {
	
	scanner = new ConsoleScanner();	
	BookDao dao = new BookDaoForDB();
	
	
	int id = scanner.enterCardId();
	String pass = scanner.enterPassword();
	user = dao.authorize(id , pass);
	
	if(user!=null) {
		scanner.showMenuForUsers();
	}
	
	
	}
	
	
}
