package main;

import java.util.ArrayList;

public interface ICart {
	
	void addBook (Book book);
	
	ArrayList<Book> getBooks ();
	
	void clearCart ();
	
	void removeBook (String ISBN);
	
	void removeItems ();

}
