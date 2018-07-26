package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import by.htp.library.dao.BookDao;
import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.scanner.ConsoleScanner;

public class BookDaoForDB implements BookDao {

	private static final String SQL_SELECT_BOOK = "select*from list_of_books  where id = ?";
	private static final String SQL_SEARCH_USER = "select*from employees where card_id = ?";
	private static final String SQL_VIEW_CATALOG = "select*from list_of_books";
	private static final String SQL_SEARCH_DEBT = "select id_book from book_in_use where days_of_usage > 30 ";
	private static final String SQL_ADD_USER = "INSERT INTO employees (card_id, user_name, password) VALUES (?, ?, ? )";
	private static final String SQL_REMOVE_BOOK = "delete from book_in_use where id_book = ?";
	private static final String SQL_ADD_BOOK = "INSERT INTO list_of_books (id, title, author) VALUES ( ?, ? , ? )";
	private static final String SQL_MOVE_BOOK = "INSERT INTO book_in_use (id_book, librarian_card_id) VALUES (?, ?)";

	private Connection connect() {

		ResourceBundle rb = ResourceBundle.getBundle("db_config");
		String driver = rb.getString("db.driver");
		String url = rb.getString("db.url");
		String login = rb.getString("db.login");
		String pass = rb.getString("db.pass");
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, login, pass);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public User authorize(int id, String pass) {

		if (id == 12345 && pass.equals("librarian")) {
			System.out.println("\nLibrarian is authorized...");
			ConsoleScanner s = new ConsoleScanner();
			s.showMenuForLibrarian();
			return null;
		}

		Connection con = connect();
		User user = null;
		try {
			PreparedStatement ps = con.prepareStatement(SQL_SEARCH_USER);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setCardId(rs.getInt("card_id"));
				user.setName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user = checkUser(user, id, pass);
			}
			checkDebtExistance();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
		return user;
	}

	private User checkUser(User user, int id, String pass) {
		if (id == user.getCardId() && pass.equals(user.getPassword())) {
			System.out.println("User is authorized");
		} else {
			System.out.println("Wrong id or password");
			return null;
		}
		return user;
	}

	
	@Override
	public void checkDebtExistance() {
		Connection con = connect();
		Book book = null;
		try {
			PreparedStatement ps = con.prepareStatement(SQL_SEARCH_DEBT);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id_book");
				book = findBook(id);
				System.out.println("\nBOOK EXPIRATION TIME WAS EXCEEDED FOR: " + book.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}

	}

	@Override
	public List<Book> viewCatalog() {

		List<Book> bookList = new ArrayList<>();
		Connection con = connect();
		Book book = null;
		try {
			PreparedStatement st = con.prepareStatement(SQL_VIEW_CATALOG);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				bookList.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
		return bookList;
	}

	@Override
	public Book findBook(int id) {
		Connection con = connect();
		Book book = null;
		try {
			PreparedStatement ps = con.prepareStatement(SQL_SELECT_BOOK);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				book = new Book();

				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
		return book;
	}

	
	
	@Override
	public void addUser(int id, String name, String password) {

		Connection con = connect();
		User user = new User(id, name, password);
		try {

			PreparedStatement ps = con.prepareStatement(SQL_ADD_USER);
			ps.setInt(1, user.getCardId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
	}

	
	public void addBook(Book book) {
		Connection con = connect();
		try {
			PreparedStatement ps = con.prepareStatement(SQL_ADD_BOOK);
			ps.setInt(1, book.getId());
			ps.setString(2, book.getTitle());
			ps.setString(3, book.getAuthor());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
	}

	
	@Override
	public void giveBook(int id, int cardID) {
		Connection con = connect();
		try {
			PreparedStatement ps = con.prepareStatement(SQL_SELECT_BOOK);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				System.out.println("Sorry, This book is already in use!");
		
			} else {
			ps = con.prepareStatement(SQL_MOVE_BOOK);
				ps.setInt(1, id);
				ps.setInt(2, cardID);

				ps.executeUpdate();
				System.out.println("Build Success!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
	}

	@Override
	public void fixBookReturn(int id) {
		Connection con = connect();
		Book book = null;
		try {
			PreparedStatement ps = con.prepareStatement(SQL_REMOVE_BOOK);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}		
	}

	
	
	private void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
