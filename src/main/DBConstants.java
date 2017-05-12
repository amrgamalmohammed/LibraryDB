package main;

import java.util.HashMap;

public class DBConstants {

	//Connection information
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "1234";
	public static final String DB_URL = "jdbc:mysql://localhost:3307/";
	public static final String DB_NAME = "Book_Store";
	
	//Tables names'
	public static final String BOOK = "Book";
	public static final String BOOK_AUTHORS = "Book_Authors";
	public static final String PUBLISHER = "Publisher";
	public static final String ORDER = "Book_Order";
	public static final String USER = "Library_User";
	public static final String CATEGORY = "Category";
	public static final String SALES = "Sales";
	
	//Book attributes
	public static final String ISBN = "ISBN";
	public static final String TITLE = "Title";
	public static final String PUB_NAME = "Pub_Name";
	public static final String YEAR = "Pub_Year";
	public static final String PRICE = "Price";
	public static final String CATEGORY_ID = "Cat_Id";
	public static final String COPIES = "Copies";
	public static final String THRESHOLD = "Threshold";
	
	//Book_Authors attributes
	public static final String AUTHOR_NAME = "Author_Name";
	
	//Publisher attributes
	public static final String ADDRESS = "Address";
	public static final String PHONE = "Phone";
	
	//Order attributes
	public static final String ACCEPTED = "Accepted";
	
	//User attributes
	public static final String FIRST_NAME = "F_Name";
	public static final String LAST_NAME = "L_Name";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "User_Password";
	public static final String EMAIL = "Email";
	public static final String IS_MANAGER = "Is_Manager";
	
	//Category attributes
	public static final String CATEGORY_NAME = "Cat_Name";
	
	//Sales attributes
	public static final String SELL_DATE = "Sell_Date";
	
	private static HashMap<String, Boolean> quotesMap;
	
	//Fill Map with attributes and true if a quotes is needed to be added and false otherwise
	public static void fillQuotesMap () {
		
		quotesMap = new HashMap<String, Boolean>();
		quotesMap.put(ISBN, true);
		quotesMap.put(TITLE, true);
		quotesMap.put(PUB_NAME, true);
		quotesMap.put(YEAR, true);
		quotesMap.put(PRICE, false);
		quotesMap.put(CATEGORY_ID, false);
		quotesMap.put(COPIES, false);
		quotesMap.put(THRESHOLD, false);
		quotesMap.put(AUTHOR_NAME, true);
		quotesMap.put(ADDRESS, true);
		quotesMap.put(PHONE, true);
		quotesMap.put(ACCEPTED, false);
		quotesMap.put(FIRST_NAME, true);
		quotesMap.put(LAST_NAME, true);
		quotesMap.put(USERNAME, true);
		quotesMap.put(PASSWORD, true);
		quotesMap.put(EMAIL, true);
		quotesMap.put(IS_MANAGER, false);
		quotesMap.put(CATEGORY_NAME, true);
		quotesMap.put(SELL_DATE, true);

	}
	
	public static boolean needQuotes(String attribute) {
		return quotesMap.get(attribute);
	}
	
}
