package main;

public class Response {
	
	private boolean isCorrect;
	
	private String message;
	
	public Response (boolean isCorrect, String message) {
		this.isCorrect = isCorrect;
		this.message = message;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public String getMessage() {
		return message;
	}
	
}
