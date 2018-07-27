package by.htp.library.scanner;

import java.util.List;
import java.util.Scanner;

import by.htp.library.dao.BookDao;
import by.htp.library.dao.impl.BookDaoForLibrarian;
import by.htp.library.entity.Book;

public class Librarian {

	private int id;
	private int cardId;
	private BookDao dao = new BookDaoForLibrarian();
	private Scanner scan;
	private String title;
	private String author;

	public int enterCardId() {

		scan = new Scanner(System.in);
		System.out.println("Enter librarian card id number...");
		String userId = scan.nextLine();
		int id = Integer.valueOf(userId);
		return id;
	}

	public String enterPassword() {

		scan = new Scanner(System.in);
		System.out.println("Enter password...");
		String pass = scan.nextLine();
		return pass;
	}

	public void showMenuForUsers() {
		System.out.println("\nPlease, make your choice:" + "\n * Enter 1, if you want to view book catalog"
				+ "\n * Enter 2, if you want to view single book info");
		scan = new Scanner(System.in);
		System.out.println("Enter your number...");
		String userChoice = scan.nextLine();
		int j = Integer.valueOf(userChoice);

		switch (j) {

		case 1:
			List<Book> bookList = dao.viewCatalog();
			System.out.println("Books are available: " + bookList);
			break;

		case 2:
			System.out.println("Enter book ID you need to view...");
			id = Integer.valueOf(scan.nextInt());
			Book book = dao.findBook(id);
			System.out.println("Book you are looking for: " + book);

			break;
		}
	}

	public void showMenuForLibrarian() {
		
		System.out.println("\nPlease, make your choice:" + "\n * Enter 1, if you want to add new user"
				+ "\n * Enter 2, if you want to add new book" + "\n * Enter 3, if you want to give book for employee"
				+ "\n * Enter 4, if you want to fix return of book");
		scan = new Scanner(System.in);
		System.out.println("Enter your number...");
		String userChoice = scan.nextLine();
		int j = Integer.valueOf(userChoice);

		switch (j) {

		case 1:
			createUser();
			break;

		case 2:
			createBook();
			break;

		case 3:
			System.out.println("Enter book id...");
			id = Integer.valueOf(scan.nextLine());
			System.out.println("Enter card id...");
			cardId = Integer.valueOf(scan.nextLine());
			dao.giveBook(id, cardId);
			break;

		case 4:
			System.out.println("Enter ID of return book...");
			id = Integer.valueOf(scan.nextLine());
			dao.fixBookReturn(id);
			System.out.println("Build success!!!");
			break;
		default:
			System.out.println("Please, check the entered data, and try again");
			break;
		}
	}

	private void createBook() {

		System.out.println("Enter book ID...");
		id = Integer.valueOf(scan.nextLine());
		System.out.println("Enter book title...");
		title = scan.nextLine();
		System.out.println("Enter book author...");
		author = scan.nextLine();
		Book book = new Book(id, title, author);
		dao.addBook(book);
		System.out.println("Build success!!!");

	}

	private void createUser() {

		System.out.println("Enter card ID...");
		id = Integer.valueOf(scan.nextLine());
		System.out.println("Enter user name...");
		String name = scan.nextLine();
		System.out.println("Enter user password...");
		String password = scan.nextLine();
		dao.addUser(id, name, password);
		System.out.println("Build success!!!");

	}

}
