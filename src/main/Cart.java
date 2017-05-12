package main;
import java.util.ArrayList;

public class Cart implements ICart{

	ArrayList<Book> books = new ArrayList<>();
	
	@Override
	public void addBook(Book book) {
		// TODO Auto-generated method stub
		int copies = 0;
		int i = 0;
		for (Book b: books) {
			if (b.getISBN().equals(book.getISBN())) {
				copies = Integer.parseInt(b.getCopies());
				books.remove(i);
				break;
			}
			i++;
		}
		String newCopies = String.valueOf(copies + Integer.parseInt(book.getCopies()));
		Book newBook = new Book(book.getISBN(), book.getTitle(), book.getPrice(), newCopies);
		books.add(newBook);
	}

	@Override
	public ArrayList<Book> getBooks() {
		// TODO Auto-generated method stub
		return books;
	}

	@Override
	public void clearCart() {
		// TODO Auto-generated method stub
		books.clear();
	}

	@Override
	public void removeBook(String ISBN) {
		// TODO Auto-generated method stub
		int i = 0;
		for (Book b: books) {
			if (b.getISBN().equals(ISBN)) {
				books.remove(i);
				break;
			}
			i++;
		}
	}

	@Override
	public void removeItems() {
		// TODO Auto-generated method stub
		books.clear();
	}

}
