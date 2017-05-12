package main;
public class Book {
	
	private String ISBN, price, copies, title, total;
	
	public Book (String ISBN, String title, String price, String copies) {
		this.copies = copies;
		this.ISBN = ISBN;
		this.price = price;
		this.title = title;
		this.total = Double.toString(Double.parseDouble(price) * Double.parseDouble(copies));
	}

	public String getISBN() {
		return ISBN;
	}

	public String getPrice() {
		return price;
	}

	public String getCopies() {
		return copies;
	}

	public String getTitle() {
		return title;
	}

	public String getTotal() {
		return total;
	}

}
