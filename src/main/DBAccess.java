package main;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class DBAccess implements DBAccessInterface {

	private Connection myConnection;
	private Statement myStatement;

	public DBAccess() {
		try {
			// connection to database
			myConnection = DriverManager.getConnection(DBConstants.DB_URL
					+ DBConstants.DB_NAME, DBConstants.DB_USERNAME,
					DBConstants.DB_PASSWORD);
			// create statement
			myStatement = myConnection.createStatement();
			myConnection.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConstants.fillQuotesMap();
	}

	@Override
	public Response addUser(String[] userAttributes) {

		// Constructing query
		String query = "INSERT INTO " + DBConstants.USER + " VALUES (";
		for (int i = 0; i < userAttributes.length - 1; i++) {
			query += ("'" + userAttributes[i] + "'" + ",");
		}
		query += userAttributes[userAttributes.length - 1];
		query += ")";

		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			myStatement.execute(query);
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public Response login(String userName, String password) {
		// Constructing query
		String query = "SELECT " + DBConstants.USERNAME + ","
				+ DBConstants.PASSWORD + " FROM " + DBConstants.USER
				+ " WHERE " + DBConstants.USERNAME + "='" + userName + "'";

		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);
			if (set.next()) {
				String testPassword = set.getString(DBConstants.PASSWORD);
				if (!testPassword.equals(password)) {
					isCorrect = false;
					message = "Invalid password !";
				}
			} else {
				isCorrect = false;
				message = "Invalid username !";
			}
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public String[] getUserData(String userName) {
		// Constructing query
		String query = "SELECT * FROM " + DBConstants.USER + " WHERE "
				+ DBConstants.USERNAME + "='" + userName + "'";

		// Executing query
		String[] result = new String[8];
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);
			if (set.next()) {
				for (int i = 1; i <= 8; i++) {
					result[i - 1] = set.getString(i);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Response updateUser(String userName, String[] userAttributes) {
		// Constructing query
		String query = "UPDATE " + DBConstants.USER + " SET "
				+ DBConstants.FIRST_NAME + "='" + userAttributes[0] + "',"
				+ DBConstants.LAST_NAME + "='" + userAttributes[1] + "',"
				+ DBConstants.USERNAME + "='" + userAttributes[2] + "',"
				+ DBConstants.PASSWORD + "='" + userAttributes[3] + "',"
				+ DBConstants.EMAIL + "='" + userAttributes[4] + "',"
				+ DBConstants.PHONE + "='" + userAttributes[5] + "',"
				+ DBConstants.ADDRESS + "='" + userAttributes[6] + "',"
				+ DBConstants.IS_MANAGER + "=" + userAttributes[7] + " WHERE "
				+ DBConstants.USERNAME + "='" + userName + "'";
		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			int affectedRows = myStatement.executeUpdate(query);
			if (affectedRows != 1) {
				message = "Invalid username !";
				isCorrect = false;
			}
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public ITable searchBook(String[] attributes, String[] values) {
		// Constructing query
		String query = "SELECT B." + DBConstants.ISBN + "," + DBConstants.TITLE
				+ "," + DBConstants.PUB_NAME + ", year(" + DBConstants.YEAR
				+ ")," + DBConstants.PRICE + "," + DBConstants.CATEGORY_NAME
				+ "," + DBConstants.COPIES + "," + DBConstants.THRESHOLD + ","
				+ DBConstants.AUTHOR_NAME + " FROM " + DBConstants.BOOK
				+ " AS B," + DBConstants.BOOK_AUTHORS + " AS A,"
				+ DBConstants.CATEGORY + " AS C WHERE B." + DBConstants.ISBN
				+ " = A." + DBConstants.ISBN + " AND B."
				+ DBConstants.CATEGORY_ID + " = C." + DBConstants.CATEGORY_ID
				+ " AND ";
		for (int i = 0; i < attributes.length; i++) {
			if (DBConstants.needQuotes(attributes[i])) {
				if (attributes[i].equals(DBConstants.ISBN)) {
					query += "B.";
				}
				query += (attributes[i] + " = '" + values[i] + "' AND ");
			} else {
				query += (attributes[i] + "=" + values[i] + " AND ");
			}
		}
		query = query.substring(0, query.length() - 5);

		query += " ORDER BY B." + DBConstants.ISBN;
		// Executing query
		ITable table = new Table();
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			String[] attrs = { DBConstants.ISBN, DBConstants.TITLE,
					DBConstants.PUB_NAME, DBConstants.YEAR, DBConstants.PRICE,
					DBConstants.CATEGORY_NAME, DBConstants.COPIES,
					DBConstants.THRESHOLD, DBConstants.AUTHOR_NAME };

			List<String[]> data = new ArrayList<String[]>();

			String ISBN = "";
			while (set.next()) {
				List<String> tuple = new ArrayList<String>();
				if (!set.getString(1).equals(ISBN)) {
					ISBN = set.getString(1);
					for (int i = 0; i < attrs.length; i++) {
						tuple.add(set.getString(i + 1));
					}
				} else {
					String[] lastTuple = data.get(data.size() - 1);
					String authors = lastTuple[attrs.length - 1];
					authors += "," + set.getString(DBConstants.AUTHOR_NAME);
					lastTuple[attrs.length - 1] = authors;
					data.remove(data.size() - 1);
					data.add(lastTuple);
					continue;
				}
				String[] tempTuple = new String[tuple.size()];
				tempTuple = tuple.toArray(tempTuple);
				data.add(tempTuple);
			}

			String[][] tableData = new String[data.size()][];
			tableData = data.toArray(tableData);

			table.setAttributes(attrs);
			table.setData(tableData);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return table;
	}

	@Override
	public ITable getAllBooks() {
		// Constructing query
		String query = "SELECT B." + DBConstants.ISBN + "," + DBConstants.TITLE
				+ "," + DBConstants.PUB_NAME + ", year(" + DBConstants.YEAR
				+ ")," + DBConstants.PRICE + "," + DBConstants.CATEGORY_NAME
				+ "," + DBConstants.COPIES + "," + DBConstants.THRESHOLD + ","
				+ DBConstants.AUTHOR_NAME + " FROM " + DBConstants.BOOK
				+ " AS B," + DBConstants.BOOK_AUTHORS + " AS A,"
				+ DBConstants.CATEGORY + " AS C WHERE B." + DBConstants.ISBN
				+ " = A." + DBConstants.ISBN + " AND B."
				+ DBConstants.CATEGORY_ID + " = C." + DBConstants.CATEGORY_ID;
		// Executing query
		ITable table = new Table();
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			String[] attrs = { DBConstants.ISBN, DBConstants.TITLE,
					DBConstants.PUB_NAME, DBConstants.YEAR, DBConstants.PRICE,
					DBConstants.CATEGORY_NAME, DBConstants.COPIES,
					DBConstants.THRESHOLD, DBConstants.AUTHOR_NAME };

			List<String[]> data = new ArrayList<String[]>();

			String ISBN = "";
			while (set.next()) {
				List<String> tuple = new ArrayList<String>();
				if (!set.getString(1).equals(ISBN)) {
					ISBN = set.getString(1);
					for (int i = 0; i < attrs.length; i++) {
						tuple.add(set.getString(i + 1));
					}
				} else {
					String[] lastTuple = data.get(data.size() - 1);
					String authors = lastTuple[attrs.length - 1];
					authors += "," + set.getString(DBConstants.AUTHOR_NAME);
					lastTuple[attrs.length - 1] = authors;
					data.remove(data.size() - 1);
					data.add(lastTuple);
					continue;
				}
				String[] tempTuple = new String[tuple.size()];
				tempTuple = tuple.toArray(tempTuple);
				data.add(tempTuple);
			}

			String[][] tableData = new String[data.size()][];
			tableData = data.toArray(tableData);

			table.setAttributes(attrs);
			table.setData(tableData);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return table;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Response checkOut(ICart cart, String username, String[] credit,
			String year, String month, String day) {
		// TODO Auto-generated method stub
		String message = "";
		boolean isCorrect = true;
		for (int i = 0; i < credit.length; i++) {
			if (credit[i].length() != 4 || !credit[i].matches("[0-9]+")) {
				message = "Wrong credit card format !";
				isCorrect = false;
				Response response = new Response(isCorrect, message);
				return response;
			}
		}
		Date today = new Date(System.currentTimeMillis());
		// String[] strDate = date.toString().split("-");
		int yearInt = Integer.parseInt(year) - 1900;
		int monthInt = Integer.parseInt(month) - 1;
		int dayInt = Integer.parseInt(day);
		if (!(yearInt >= 0 && monthInt >= 0 && monthInt < 12 && dayInt >= 0 && dayInt < 32)) {
			message = "Wrong date format !";
			isCorrect = false;
			Response response = new Response(isCorrect, message);
			return response;
		}
		Date date = new Date(yearInt, monthInt, dayInt);
		if (today.compareTo(date) >= 0) {
			message = "This credit card is expired !";
			isCorrect = false;
			Response response = new Response(isCorrect, message);
			return response;
		}

		try {
			myConnection.setAutoCommit(false);
			List<Book> books = cart.getBooks();
			String query = "";
			for (Book b : books) {
				query = "UPDATE " + DBConstants.BOOK + " SET "
						+ DBConstants.COPIES + "=" + DBConstants.COPIES + "-"
						+ b.getCopies() + " WHERE " + DBConstants.ISBN + "='"
						+ b.getISBN() + "'";
				myStatement.executeUpdate(query);

				query = "SELECT S." + DBConstants.ISBN + ", S."
						+ DBConstants.USERNAME + ", S." + DBConstants.SELL_DATE
						+ " FROM " + DBConstants.SALES + " AS S WHERE S."
						+ DBConstants.ISBN + "='" + b.getISBN() + "' AND S."
						+ DBConstants.USERNAME + "='" + username + "' AND S."
						+ DBConstants.SELL_DATE + "=CURDATE()";
				ResultSet set = myStatement.executeQuery(query);

				if (set.next()) {
					query = "UPDATE " + DBConstants.SALES + " SET "
							+ DBConstants.COPIES + " = " + DBConstants.COPIES
							+ " + " + b.getCopies() + ", "
							+ DBConstants.SELL_DATE + " = " + " CURDATE(), "
							+ DBConstants.PRICE + "=" + DBConstants.PRICE
							+ " + " + b.getTotal() + " WHERE "
							+ DBConstants.ISBN + "='" + b.getISBN() + "' AND "
							+ DBConstants.USERNAME + "='" + username + "' AND "
							+ DBConstants.SELL_DATE + "=CURDATE()";
					int affectedRows = myStatement.executeUpdate(query);
					if (affectedRows != 1) {
						isCorrect = false;
						message = "Something went wrong obviously !";
					}
				} else {
					query = "INSERT INTO " + DBConstants.SALES + " VALUES ('"
							+ username + "', '" + b.getISBN() + "', "
							+ b.getCopies() + ", CURDATE(), " + b.getTotal()
							+ ")";
					myStatement.execute(query);
				}
			}
			myConnection.commit();
		} catch (Exception e) {
			isCorrect = false;
			message = e.getMessage();
			try {
				myConnection.rollback();
			} catch (Exception e1) {
				isCorrect = false;
				message = e.getMessage();
			}
		} finally {
			try {
				myConnection.setAutoCommit(true);
			} catch (Exception e) {
				isCorrect = false;
				message = e.getMessage();
			}
		}
		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public Response addNewBook(String[] bookAttributes, String[] authors) {
		// Constructing query
		String query = "SELECT " + DBConstants.CATEGORY_ID + " FROM "
				+ DBConstants.CATEGORY + " WHERE " + DBConstants.CATEGORY_NAME
				+ " = '" + bookAttributes[5] + "'";

		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			if (set.next()) {
				String categoryId = set.getString(DBConstants.CATEGORY_ID);

				query = "INSERT INTO " + DBConstants.BOOK + " VALUES ( '"
						+ bookAttributes[0] + "', '" + bookAttributes[1]
						+ "', '" + bookAttributes[2] + "', '"
						+ bookAttributes[3] + "', " + bookAttributes[4] + ", "
						+ categoryId + ", " + bookAttributes[6] + ", "
						+ bookAttributes[7] + ")";

				myStatement.execute(query);

				for (int i = 0; i < authors.length; i++) {
					query = "INSERT INTO " + DBConstants.BOOK_AUTHORS
							+ " VALUES ( '" + bookAttributes[0] + "', '"
							+ authors[i] + "' )";
					myStatement.execute(query);
				}

			} else {
				isCorrect = false;
				message = "Category name was not found !";
			}
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public Response modifyBook(String ISBN, String[] bookAttributes,
			String[] authors) {
		// Constructing query
		String query = "SELECT " + DBConstants.CATEGORY_ID + " FROM "
				+ DBConstants.CATEGORY + " WHERE " + DBConstants.CATEGORY_NAME
				+ " = '" + bookAttributes[5] + "'";

		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			if (set.next()) {
				String categoryId = set.getString(DBConstants.CATEGORY_ID);

				query = "UPDATE " + DBConstants.BOOK + " SET "
						+ DBConstants.ISBN + " = '" + bookAttributes[0] + "', "
						+ DBConstants.TITLE + " = '" + bookAttributes[1]
						+ "', " + DBConstants.PUB_NAME + " = '"
						+ bookAttributes[2] + "', " + DBConstants.YEAR + " = '"
						+ bookAttributes[3] + "', " + DBConstants.PRICE + " = "
						+ bookAttributes[4] + ", " + DBConstants.CATEGORY_ID
						+ " = " + categoryId + ", " + DBConstants.COPIES
						+ " = " + bookAttributes[6] + ", "
						+ DBConstants.THRESHOLD + " = " + bookAttributes[7]
						+ " WHERE " + DBConstants.ISBN + " = '" + ISBN + "'";

				int affectedRows = myStatement.executeUpdate(query);
				if (affectedRows != 1) {
					isCorrect = false;
					message = "Something went wrong obviously !";
				}
				query = "DELETE FROM " + DBConstants.BOOK_AUTHORS + " WHERE "
						+ DBConstants.ISBN + " = '" + ISBN + "'";
				myStatement.execute(query);
				for (int i = 0; i < authors.length; i++) {
					query = "INSERT INTO " + DBConstants.BOOK_AUTHORS
							+ " VALUES ( '" + bookAttributes[0] + "', '"
							+ authors[i] + "' )";
					myStatement.execute(query);
				}

			} else {
				isCorrect = false;
				message = "Category name was not found !";
			}
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public Response placeOrder(String ISBN, String copies) {
		// Constructing query
		String query = "INSERT INTO " + DBConstants.ORDER + " VALUES ( '"
				+ ISBN + "', " + copies + ", True)";
		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			myStatement.execute(query);
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public Response updateOrder(String ISBN, String copies) {
		// Constructing query
		String query = "UPDATE " + DBConstants.ORDER + " SET "
				+ DBConstants.COPIES + " = " + copies + " WHERE "
				+ DBConstants.ISBN + " = '" + ISBN + "'";
		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			int affectedRows = myStatement.executeUpdate(query);
			if (affectedRows != 1) {
				isCorrect = false;
				message = "Something went wrong obviously !";
			}
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public ITable getConfimedOrders() {
		// Constructing query
		String query = "SELECT B." + DBConstants.ISBN + "," + DBConstants.TITLE
				+ "," + DBConstants.PUB_NAME + ", year(" + DBConstants.YEAR
				+ ")," + DBConstants.PRICE + "," + DBConstants.CATEGORY_NAME
				+ "," + "O." + DBConstants.COPIES + "," + DBConstants.THRESHOLD
				+ " FROM " + DBConstants.BOOK + " AS B, " + DBConstants.ORDER
				+ " AS O, " + DBConstants.CATEGORY + " AS C WHERE B."
				+ DBConstants.ISBN + " = O." + DBConstants.ISBN + " AND B."
				+ DBConstants.CATEGORY_ID + " = C." + DBConstants.CATEGORY_ID
				+ " AND " + DBConstants.ACCEPTED + " = True";

		// Executing query
		ITable table = new Table();
		try {

			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			String[] attrs = { DBConstants.ISBN, DBConstants.TITLE,
					DBConstants.PUB_NAME, DBConstants.YEAR, DBConstants.PRICE,
					DBConstants.CATEGORY_NAME, DBConstants.COPIES,
					DBConstants.THRESHOLD };

			List<String[]> data = new ArrayList<String[]>();

			while (set.next()) {
				List<String> tuple = new ArrayList<String>();
				for (int i = 0; i < attrs.length; i++) {
					tuple.add(set.getString(i + 1));
				}
				String[] tempTuple = new String[tuple.size()];
				tempTuple = tuple.toArray(tempTuple);
				data.add(tempTuple);
			}

			String[][] tableData = new String[data.size()][];
			tableData = data.toArray(tableData);

			table.setAttributes(attrs);
			table.setData(tableData);

		} catch (Exception ex) {

		}

		return table;
	}

	@Override
	public ITable getUnConfirmedOrders() {
		// Constructing query
		String query = "SELECT B." + DBConstants.ISBN + "," + DBConstants.TITLE
				+ "," + DBConstants.PUB_NAME + ", year(" + DBConstants.YEAR
				+ ")," + DBConstants.PRICE + "," + DBConstants.CATEGORY_NAME
				+ "," + "O." + DBConstants.COPIES + "," + DBConstants.THRESHOLD
				+ " FROM " + DBConstants.BOOK + " AS B, " + DBConstants.ORDER
				+ " AS O, " + DBConstants.CATEGORY + " AS C WHERE B."
				+ DBConstants.ISBN + " = O." + DBConstants.ISBN + " AND B."
				+ DBConstants.CATEGORY_ID + " = C." + DBConstants.CATEGORY_ID
				+ " AND " + DBConstants.ACCEPTED + " = FALSE";

		// Executing query
		ITable table = new Table();
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			String[] attrs = { DBConstants.ISBN, DBConstants.TITLE,
					DBConstants.PUB_NAME, DBConstants.YEAR, DBConstants.PRICE,
					DBConstants.CATEGORY_NAME, DBConstants.COPIES,
					DBConstants.THRESHOLD };
			List<String[]> data = new ArrayList<String[]>();

			while (set.next()) {
				List<String> tuple = new ArrayList<String>();
				for (int i = 0; i < attrs.length; i++) {
					tuple.add(set.getString(i + 1));
				}
				String[] tempTuple = new String[tuple.size()];
				tempTuple = tuple.toArray(tempTuple);
				data.add(tempTuple);
			}

			String[][] tableData = new String[data.size()][];
			tableData = data.toArray(tableData);

			table.setAttributes(attrs);
			table.setData(tableData);

		} catch (Exception ex) {

		}

		return table;
	}

	@Override
	public Response deleteOrder(String ISBN) {
		// Constructing query
		String query = "DELETE FROM " + DBConstants.ORDER + " WHERE "
				+ DBConstants.ISBN + " = '" + ISBN + "'";
		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			myStatement.execute(query);
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public Response promoteUser(String userName) {
		// Constructing query
		String query = "UPDATE " + DBConstants.USER + " SET "
				+ DBConstants.IS_MANAGER + " = True WHERE "
				+ DBConstants.USERNAME + " = '" + userName + "'";
		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			int affectedRows = myStatement.executeUpdate(query);
			if (affectedRows != 1) {
				isCorrect = false;
				message = "Something went wrong obviously !";
			}
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public ITable getTotalSales() {
		// Constructing query
		String query = "SELECT S." + DBConstants.ISBN + "," + DBConstants.TITLE
				+ "," + DBConstants.PUB_NAME + ", year(" + DBConstants.YEAR
				+ "),B." + DBConstants.PRICE + "," + DBConstants.CATEGORY_NAME
				+ ",B." + DBConstants.COPIES + "," + DBConstants.THRESHOLD
				+ ", SUM(S." + DBConstants.PRICE + ")" + " FROM "
				+ DBConstants.BOOK + " AS B," + DBConstants.CATEGORY + " AS C,"
				+ DBConstants.SALES + " AS S WHERE B." + DBConstants.ISBN
				+ " = S." + DBConstants.ISBN + " AND B."
				+ DBConstants.CATEGORY_ID + " = C." + DBConstants.CATEGORY_ID
				+ " AND S." + DBConstants.SELL_DATE + " <= CURDATE() AND S."
				+ DBConstants.SELL_DATE + " >= '" + getLastMonth() + "'"
				+ " GROUP BY S." + DBConstants.ISBN + " ORDER BY SUM(S."
				+ DBConstants.PRICE + ") DESC";

		// Executing query
		ITable table = new Table();
		try {

			// jasper
			myConnection.setAutoCommit(true);
			JasperReportBuilder report = DynamicReports.report();
			report.columns(

					Columns.column("book Id", DBConstants.ISBN,
							DataTypes.stringType()),
					Columns.column("Title", DBConstants.TITLE,
							DataTypes.stringType()),
					Columns.column("Publisher", DBConstants.PUB_NAME,
							DataTypes.stringType()),
					Columns.column("year", "year(Pub_Year)",
							DataTypes.stringType()),
					Columns.column("  Price", DBConstants.PRICE,
							DataTypes.doubleType()).setHorizontalTextAlignment(
							HorizontalTextAlignment.LEFT),
					Columns.column("Category", "Cat_Name",
							DataTypes.stringType()),
					Columns.column("Copies", DBConstants.COPIES,
							DataTypes.integerType())
							.setHorizontalTextAlignment(
									HorizontalTextAlignment.LEFT),
					Columns.column("Threshold", DBConstants.THRESHOLD,
							DataTypes.integerType())
							.setHorizontalTextAlignment(
									HorizontalTextAlignment.LEFT),
					Columns.column("Total price", "SUM(S.Price)",
							DataTypes.doubleType()).setHorizontalTextAlignment(
							HorizontalTextAlignment.LEFT))
					// missing the total
					.title(// title of the report
					Components.text("\t\t\t\t\t\t Total Sales\n"))
					.setDataSource(query, myConnection);

			FileOutputStream file = new FileOutputStream("total sales.pdf");
			try {
				// show the report

				// report.show();

				// export the report to a pdf file
				report.columnGrid();
				report.toPdf(file);

				file.close();
			} catch (DRException e) {
				e.printStackTrace();
			}

			// end jasper

			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			String[] attrs = { DBConstants.ISBN, DBConstants.TITLE,
					DBConstants.PUB_NAME, DBConstants.YEAR, DBConstants.PRICE,
					DBConstants.CATEGORY_NAME, DBConstants.COPIES,
					DBConstants.THRESHOLD, "Total sales" };

			List<String[]> data = new ArrayList<String[]>();

			while (set.next()) {
				List<String> tuple = new ArrayList<String>();
				for (int i = 0; i < attrs.length; i++) {
					tuple.add(set.getString(i + 1));
				}
				String[] tempTuple = new String[tuple.size()];
				tempTuple = tuple.toArray(tempTuple);
				data.add(tempTuple);
			}

			String[][] tableData = new String[data.size()][];
			tableData = data.toArray(tableData);

			table.setAttributes(attrs);
			table.setData(tableData);

		} catch (Exception ex) {

		}

		return table;
	}

	@Override
	public ITable getTopCustomers() {
		// Constructing query
		String query = "SELECT " + DBConstants.FIRST_NAME + ","
				+ DBConstants.LAST_NAME + ",S." + DBConstants.USERNAME + ","
				+ DBConstants.EMAIL + "," + DBConstants.PHONE + ","
				+ DBConstants.ADDRESS + ", SUM(S." + DBConstants.PRICE
				+ ") FROM " + DBConstants.SALES + " AS S, " + DBConstants.USER
				+ " AS U WHERE S." + DBConstants.USERNAME + " = U."
				+ DBConstants.USERNAME + " AND S." + DBConstants.SELL_DATE
				+ " <= CURDATE() AND S." + DBConstants.SELL_DATE + " >= '"
				+ getLastThreeMonth() + "'" + " GROUP BY S."
				+ DBConstants.USERNAME + " ORDER BY SUM(S." + DBConstants.PRICE
				+ ") DESC LIMIT 5";
		// Executing query
		ITable table = new Table();
		try {

			// jasper
			myConnection.setAutoCommit(true);
			JasperReportBuilder report = DynamicReports.report();
			report.columns(
					Columns.column("First Name", DBConstants.FIRST_NAME,
							DataTypes.stringType()),
					Columns.column("Last Name", DBConstants.LAST_NAME,
							DataTypes.stringType()),
					Columns.column("User Name", DBConstants.USERNAME,
							DataTypes.stringType()),
					Columns.column("E-mail", DBConstants.EMAIL,
							DataTypes.stringType()),
					Columns.column("Phone ", DBConstants.PHONE,
							DataTypes.stringType()),
					Columns.column("Address", DBConstants.ADDRESS,
							DataTypes.stringType()),
					Columns.column("Total prices",
							"SUM(S." + DBConstants.PRICE + ")",
							DataTypes.integerType())
							.setHorizontalTextAlignment(
									HorizontalTextAlignment.LEFT))
					// missing the total
					.title(// title of the report
					Components.text("\t\t\t\t\t Top Customers\n"))
					.setDataSource(query, myConnection);

			FileOutputStream file = new FileOutputStream(
					"top five customers.pdf");
			try {
				// show the report
				// report.show();

				// export the report to a pdf file
				report.toPdf(file);
				file.close();
			} catch (DRException e) {
				e.printStackTrace();
			}

			// end jasper

			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			String[] attrs = { DBConstants.FIRST_NAME, DBConstants.LAST_NAME,
					DBConstants.USERNAME, DBConstants.EMAIL, DBConstants.PHONE,
					DBConstants.ADDRESS, "Total sales" };

			List<String[]> data = new ArrayList<String[]>();

			while (set.next()) {
				List<String> tuple = new ArrayList<String>();
				for (int i = 0; i < attrs.length; i++) {
					tuple.add(set.getString(i + 1));
				}
				String[] tempTuple = new String[tuple.size()];
				tempTuple = tuple.toArray(tempTuple);
				data.add(tempTuple);
			}

			String[][] tableData = new String[data.size()][];
			tableData = data.toArray(tableData);

			table.setAttributes(attrs);
			table.setData(tableData);

		} catch (Exception ex) {

		}

		return table;
	}

	@Override
	public ITable getTopBooks() {
		// Constructing query
		String query = "SELECT S." + DBConstants.ISBN + "," + DBConstants.TITLE
				+ "," + DBConstants.PUB_NAME + ", year(" + DBConstants.YEAR
				+ "),B." + DBConstants.PRICE + "," + DBConstants.CATEGORY_NAME
				+ ",B." + DBConstants.COPIES + "," + DBConstants.THRESHOLD
				+ ", SUM(S." + DBConstants.COPIES + ")" + " FROM "
				+ DBConstants.SALES + " AS S, " + DBConstants.BOOK + " AS B, "
				+ DBConstants.CATEGORY + " AS C " + "WHERE B."
				+ DBConstants.ISBN + " = S." + DBConstants.ISBN + " AND B."
				+ DBConstants.CATEGORY_ID + " = C." + DBConstants.CATEGORY_ID
				+ " AND S." + DBConstants.SELL_DATE + " <= CURDATE() AND S."
				+ DBConstants.SELL_DATE + " >= '" + getLastThreeMonth() + "'"
				+ " GROUP BY S." + DBConstants.ISBN + " ORDER BY SUM(S."
				+ DBConstants.COPIES + ")" + " DESC LIMIT 10";

		// Executing query
		ITable table = new Table();
		try {
			// jasper
			myConnection.setAutoCommit(true);
			JasperReportBuilder report = DynamicReports.report();
			report.columns(
					Columns.column("book Id", DBConstants.ISBN,
							DataTypes.stringType()),
					Columns.column("Title", DBConstants.TITLE,
							DataTypes.stringType()),
					Columns.column("Publisher", DBConstants.PUB_NAME,
							DataTypes.stringType()),
					Columns.column("year", "year(Pub_Year)",
							DataTypes.stringType()),
					Columns.column("Price", DBConstants.PRICE,
							DataTypes.doubleType()).setHorizontalTextAlignment(
							HorizontalTextAlignment.LEFT),
					Columns.column("Category", "Cat_Name",
							DataTypes.stringType()),
					Columns.column("Copies", DBConstants.COPIES,
							DataTypes.integerType())
							.setHorizontalTextAlignment(
									HorizontalTextAlignment.LEFT),
					Columns.column("Threshold", DBConstants.THRESHOLD,
							DataTypes.integerType())
							.setHorizontalTextAlignment(
									HorizontalTextAlignment.LEFT),
					Columns.column("Total copies",
							"SUM(S." + DBConstants.COPIES + ")",
							DataTypes.integerType())
							.setHorizontalTextAlignment(
									HorizontalTextAlignment.LEFT))
			// missing the total
					.title(// title of the report
					Components.text("\t\t\t\t\t\t Top Books\n"))
					// .setHorizontalAlignment(HorizontalAlignment.CENTER))
					.setDataSource(query, myConnection);

			FileOutputStream file = new FileOutputStream("top books.pdf");
			try {
				// show the report
				// report.show();

				// export the report to a pdf file
				report.toPdf(file);
				file.close();
			} catch (DRException e) {
				e.printStackTrace();
			}

			// end jasper

			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			String[] attrs = { DBConstants.ISBN, DBConstants.TITLE,
					DBConstants.PUB_NAME, DBConstants.YEAR, DBConstants.PRICE,
					DBConstants.CATEGORY_NAME, DBConstants.COPIES,
					DBConstants.THRESHOLD, "Total copies" };

			List<String[]> data = new ArrayList<String[]>();

			while (set.next()) {
				List<String> tuple = new ArrayList<String>();
				for (int i = 0; i < attrs.length; i++) {
					tuple.add(set.getString(i + 1));
				}
				String[] tempTuple = new String[tuple.size()];
				tempTuple = tuple.toArray(tempTuple);
				data.add(tempTuple);
			}

			String[][] tableData = new String[data.size()][];
			tableData = data.toArray(tableData);

			table.setAttributes(attrs);
			table.setData(tableData);

		} catch (Exception ex) {

		}

		return table;
	}

	@Override
	public Response addPublisher(String name, String address, String phone) {

		// Constructing query
		String query = "INSERT INTO " + DBConstants.PUBLISHER + " VALUES ( '"
				+ name + "', '" + address + "', '" + phone + "')";

		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			myStatement.execute(query);
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public Response addCategory(String name, String id) {
		// Constructing query
		String query = "INSERT INTO " + DBConstants.CATEGORY + " VALUES ( '"
				+ name + "', " + id + " )";

		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			myStatement.execute(query);
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public Response confirmOrder(String ISBN) {
		// Constructing query
		String query = "UPDATE " + DBConstants.ORDER + " SET "
				+ DBConstants.ACCEPTED + " = True WHERE " + DBConstants.ISBN
				+ " = '" + ISBN + "'";
		// Executing query
		String message = "";
		boolean isCorrect = true;
		try {
			myConnection.setAutoCommit(true);
			int affectedRows = myStatement.executeUpdate(query);
			if (affectedRows != 1) {
				isCorrect = false;
				message = "Something went wrong obviously !";
			}
		} catch (Exception ex) {
			isCorrect = false;
			message = ex.getMessage();
		}

		Response response = new Response(isCorrect, message);
		return response;
	}

	@Override
	public boolean isManager(String userName) {
		// Constructing query
		String query = "SELECT " + DBConstants.IS_MANAGER + " FROM "
				+ DBConstants.USER + " WHERE " + DBConstants.USERNAME + "='"
				+ userName + "'";

		// Executing query
		boolean result = false;
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);
			if (set.next()) {
				String testResult = set.getString(1);
				if (testResult.equals("1")) {
					result = true;
				}
			}
		} catch (Exception ex) {

		}

		return result;
	}

	@Override
	public String[] getAuthors() {
		// Constructing query
		String query = "SELECT DISTINCT " + DBConstants.AUTHOR_NAME + " FROM "
				+ DBConstants.BOOK_AUTHORS;

		// Executing query
		List<String> result = new ArrayList<String>();
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			while (set.next()) {
				result.add(set.getString(1));
			}
		} catch (Exception ex) {

		}

		String[] resultArray = new String[result.size()];
		resultArray = result.toArray(resultArray);

		return resultArray;
	}

	@Override
	public String[] getPublishers() {
		// Constructing query
		String query = "SELECT DISTINCT " + DBConstants.PUB_NAME + " FROM "
				+ DBConstants.PUBLISHER;

		// Executing query
		List<String> result = new ArrayList<String>();
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			while (set.next()) {
				result.add(set.getString(1));
			}
		} catch (Exception ex) {

		}

		String[] resultArray = new String[result.size()];
		resultArray = result.toArray(resultArray);

		return resultArray;
	}

	@Override
	public String[] getCategories() {
		// Constructing query
		String query = "SELECT DISTINCT " + DBConstants.CATEGORY_NAME
				+ " FROM " + DBConstants.CATEGORY;

		// Executing query
		List<String> result = new ArrayList<String>();
		try {
			myConnection.setAutoCommit(true);
			ResultSet set = myStatement.executeQuery(query);

			while (set.next()) {
				result.add(set.getString(1));
			}
		} catch (Exception ex) {

		}

		String[] resultArray = new String[result.size()];
		resultArray = result.toArray(resultArray);

		return resultArray;
	}

	private String getLastMonth() {

		Date date = new Date(System.currentTimeMillis());
		String[] strDate = date.toString().split("-");
		String month = "";
		String year = strDate[0];
		if (strDate[1].equals("01")) {
			year = String.valueOf((Integer.parseInt(year) - 1));
			month = "12";
		} else {
			int test = Integer.parseInt(strDate[1]) - 1;
			if (test < 10) {
				month += "0";
			}
			month += String.valueOf(test);
		}

		String dateStr = year + "-" + month + "-" + strDate[2];
		return dateStr;
	}

	private String getLastThreeMonth() {

		Date date = new Date(System.currentTimeMillis());
		String[] strDate = date.toString().split("-");
		String month = "";
		String year = strDate[0];
		if (strDate[1].equals("01")) {
			year = String.valueOf((Integer.parseInt(year) - 1));
			month = "10";
		} else if (strDate[1].equals("02")) {
			year = String.valueOf((Integer.parseInt(year) - 1));
			month = "11";
		} else if (strDate[1].equals("03")) {
			year = String.valueOf((Integer.parseInt(year) - 1));
			month = "12";
		} else {
			int test = Integer.parseInt(strDate[1]) - 3;
			if (test < 10) {
				month += "0";
			}
			month += String.valueOf(test);
		}

		String dateStr = year + "-" + month + "-" + strDate[2];
		return dateStr;
	}
}
