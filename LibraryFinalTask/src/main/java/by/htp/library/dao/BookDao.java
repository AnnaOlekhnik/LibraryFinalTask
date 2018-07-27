package by.htp.library.dao;

import java.util.List;

import by.htp.library.entity.Book;
import by.htp.library.entity.User;

public interface BookDao {
	
	User authorize(int id, String pass);
	
	List<Book> viewCatalog();
	
	Book findBook(int id);
	
	void addUser(int id, String name, String password);
	
	void addBook(Book book);
	
	void fixBookReturn(int id);
	
	void giveBook(int id, int cardID);

	
}
