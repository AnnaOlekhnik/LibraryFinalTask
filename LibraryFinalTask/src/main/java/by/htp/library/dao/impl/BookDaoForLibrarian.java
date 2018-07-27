package by.htp.library.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.library.dao.BookDao;
import by.htp.library.entity.Book;
import by.htp.library.entity.TakenBook;
import by.htp.library.entity.User;
import by.htp.library.scanner.ConsoleScanner;
import by.htp.library.scanner.Librarian;

public class BookDaoForLibrarian implements BookDao {

	private Book book;
	private User user;
	private TakenBook takenBook;
	private List<Book> catalog = new ArrayList<>();
	private List<User> users = new ArrayList<>();
	private List<TakenBook> booksInUse = new ArrayList<>();

	public List<Book> createCatalog() {

		book = new Book(1, "title1", "author1");
		catalog.add(book);
		book = new Book(2, "title2", "author2");
		catalog.add(book);
		book = new Book(3, "title3", "author3");
		catalog.add(book);
		book = new Book(4, "title4", "author4");
		catalog.add(book);

		return catalog;
	}

	public List<User> createUsers() {

		user = new User(1, "name1", "pass1");
		users.add(user);
		user = new User(2, "name2", "pass2");
		users.add(user);
		user = new User(3, "name3", "pass3");
		users.add(user);
		user = new User(4, "name4", "pass4");
		users.add(user);

		return users;
	}

	public List<TakenBook> createListOfBookInUse() {

		takenBook = new TakenBook(1, 1, 35);
		booksInUse.add(takenBook);
		takenBook = new TakenBook(1, 2, 12);
		booksInUse.add(takenBook);

		return booksInUse;
	}

	@Override
	public User authorize(int id, String pass) {

		if (id == 12345 && pass.equals("librarian")) {
			System.out.println("\nLibrarian is authorized...");
			Librarian librarian = new Librarian();
			librarian.showMenuForLibrarian();
			return null;
		}

		users = createUsers();
		for (User user : users)
			if (user.getCardId() == id && pass.equals(user.getPassword())) {
				System.out.println("User is authorized...");
				checkDebtExistance(id);
				break;
			} else {
				System.out.println("Wrong id or password");
				return null;
			}
		return user;
	}

	@Override
	public List<Book> viewCatalog() {
		catalog = createCatalog();
		return catalog;
	}

	@Override
	public Book findBook(int id) {
		catalog = createCatalog();

		for (Book book : catalog) {
			if (book.getId() == id) {
				return book;
			} else {
				System.out.println("No books are found");
			}
		}
		return null;
	}

	public void checkDebtExistance(int cardId) {

		catalog = createCatalog();
		booksInUse = createListOfBookInUse();
		for (TakenBook book : booksInUse) {
			if (book.getCardId() == cardId && book.getDaysOfuse() > 30) {
				int bookId = book.getBookId();
				for (Book b : catalog) {
					if (bookId == b.getId())
						System.out.println(b.toString());
				}
			}
		}
	}

	@Override
	public void addUser(int id, String name, String password) {

		user = new User(id, name, password);
		createUsers().add(user);
		System.out.println("Build success");
	}

	@Override
	public void addBook(Book book) {

		createCatalog().add(book);
		System.out.println("Build success");
		System.out.println(catalog);
	}

	@Override
	public void fixBookReturn(int id) {

		booksInUse = createListOfBookInUse();
		for (TakenBook t : booksInUse) {
			if (id == t.getBookId()) {
				booksInUse.remove(t);
				break;
			} else {
				System.out.println("Book is not in use");
				break;
			}
		}
	}

	@Override
	public void giveBook(int id, int cardID) {

		takenBook = new TakenBook();
		booksInUse = createListOfBookInUse();
		for (TakenBook t : booksInUse) {
			if (t.getBookId() == id) {
				System.out.println("Sorry, Book is already in use");
			}
		}
		takenBook.setBookId(id);
		takenBook.setCardId(cardID);
		takenBook.setDaysOfuse(0);
		createListOfBookInUse().add(takenBook);
		System.out.println("Build success!");

	}

}
