package main;
public interface DBAccessInterface {
	
	/*
	 * Add new User to the Database with the sent attributes
	 */
	Response addUser (String[] userAttributes);
	
	/*
	 * Confirm that the sent attributes are found in the Database
	 */
	Response login (String userName, String password);
	
	/*
	 * Returns all the user data of the user with the sent username
	 */
	// return all data of user
	String[] getUserData (String userName);
	
	/*
	 * Update the user with the sent username with the new attributes
	 */
	Response updateUser (String userName, String[] userAttributes);
	
	/*
	 * Insert new tuple to Publisher Table with the sent attributes
	 */
	Response addPublisher (String name, String address, String phone);
	
	/*
	 * Insert new tuple to Category table with the sent attributes
	 */
	Response addCategory (String name, String id);
	
	/*
	 * Search the database by the sent attributes and their values
	 */
	// without author, copies, threshold
	ITable searchBook (String[] attributes, String[] values);
	
	/*
	 * Returns all the Books found in the Database
	 */
	// in GUI for user do not show publisher, threshold, copies
	ITable getAllBooks ();

	/*
	 * Computes the total price of the books found in the sent cart and
	 * insert the price of the sold books to the Sales Table
	 */
	Response checkOut (ICart cart, String username, String[] credit, String year, String month, String day);
	
	/*
	 * Insert new book to the Database with the sent Attributes
	 */
	Response addNewBook (String[] bookAttributes, String[] authors);
	
	/*
	 * Update the value of the book with the sent ISBN with the new attributes
	 */
	Response modifyBook (String ISBN, String[] bookAttributes, String[] authors);
	
	/*
	 * Place new Book order in the Database
	 */
	// accepted will be always true
	Response placeOrder (String ISBN, String copies);
	
	/*
	 * Update the order with the sent ISBN to have copies equals to the sent copies
	 */
	Response updateOrder (String ISBN, String copies);
	
	/*
	 * Get Orders from Order Table that have Accepted = true
	 * Join with the Book Table to get all attributes of the book not the ISBN only
	 */
	// without author, copies, threshold
	ITable getConfimedOrders ();
	
	/*
	 * Get Orders from Order Table that have Accepted = false
	 * Join with the Book Table to get all attributes of the book not the ISBN only
	 */
	// without author, copies, threshold
	/* "ISBN", "Title", "Publisher", "Publishing Year", "Price", "Category" */
	ITable  getUnConfirmedOrders ();
	
	/*
	 * Delete Order with the sent ISBN from table
	 */
	Response deleteOrder (String ISBN);
	
	/*
	 * Update the Order table tuble which has the sent ISBN to have accepted boolean = true
	 */
	// make accepted = true
	Response confirmOrder (String ISBN);
	
	/*
	 * Promote normal user with the sent username to Manager
	 * Change isManager boolean to true
	 */
	Response promoteUser (String userName);
	
	/*
	 * From Sales table, For each book, get all its info and the total sold prices
	 * (Group by ISBN and SUM(Price)) then Join with Book, Book_Authors & Publisher Tables to get the required info
	 */
	// for last month only
	// book info + number of sales
	ITable getTotalSales ();
	
	/*
	 * From Sales table, Get the top 5 usernames with highest paid prices then get all their info
	 * (Group by username and SUM(Price)) then Join with Library_User Table to get the required info
	 */
	// user info (fName, lName, userName, email, phone, address) + total price
	// top five in descending order
	ITable getTopCustomers ();
	
	/*
	 * From Sales table, Get the top 10 books with the highest sold num of copies then get all their info
	 * (Group by ISBN and SUM(Copies)) then Join with Book, Book_Authors & Publisher the Book Table to get the required info
	 */
	// top ten selling books
	// book info + number of sales
	ITable getTopBooks ();
	
	/*
	 * Returns the isManager boolean from Library User Table for the sent username
	 */
	boolean isManager (String userName);
	
	/*
	 * Returns all the Authors Names from the Authors Table (Eliminate Duplicates)
	 */
	String[] getAuthors ();
	
	/*
	 * Returns all the Publisher Names from the Publisher Table (No Duplicates)
	 */
	String[] getPublishers ();
	
	/*
	 * Returns all the Categories Names from the Category Table (No Duplicates)
	 */
	String[] getCategories ();
}
