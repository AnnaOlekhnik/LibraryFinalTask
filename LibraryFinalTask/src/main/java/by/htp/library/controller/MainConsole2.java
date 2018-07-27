package by.htp.library.controller;

import java.util.ArrayList;
import java.util.List;

import by.htp.library.dao.BookDao;
import by.htp.library.dao.impl.BookDaoForLibrarian;
import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.scanner.Librarian;

public class MainConsole2 {

	public static void main(String[] args) {

		BookDao dao = new BookDaoForLibrarian();
		Librarian librarian = new Librarian();

		int id = librarian.enterCardId();
		String pass = librarian.enterPassword();

		User user = dao.authorize(id, pass);

		if (user != null) {
			librarian.showMenuForUsers();
		}
	}

}
