package by.htp.library.dao;

import java.util.List;

import by.htp.library.entity.Book;
import by.htp.library.entity.User;

public interface BookDao {
	
	User authorize(int id, String pass);
	
	void checkAuthorization(User user) ;
	
	List<Book> viewCatalog();
	
	Book findBook(int id);

	void checkDebtExistance();
	
	void addUser(int id, String name, String password);
	
	void addBook(Book book);

	
	
	
	
	void registrationOfTakenBooks();
	
	void fixBookReturn();
	
	void addUser();

	void giveBook(int id, int cardID);

	
}
