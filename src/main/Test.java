package main;

import java.sql.*;

public class Test {

	public static void main(String[] args) {

		try {
			// connection to database
			Connection myConn = DriverManager.getConnection(DBConstants.DB_URL+DBConstants.DB_NAME,
					DBConstants.DB_USERNAME, DBConstants.DB_PASSWORD);
			// create statement
			Statement myStmt = myConn.createStatement();
			// execute sql query
			myStmt.execute("insert into " + DBConstants.PUBLISHER + " values ('7mada','saken fe 7ay l sayeda','000000000')");
			myStmt.execute("insert into " + DBConstants.CATEGORY + " values ('rooo3b',1)");
			myStmt.execute("insert into " + DBConstants.BOOK + " values ('1234','ta3weza','7mada','1995',20.5,1,5,10)");
			// results set
			ResultSet myRs = myStmt.executeQuery("select * from "+DBConstants.PUBLISHER);
			while (myRs.next()) {
				System.out.println(myRs.getString(DBConstants.PUB_NAME) + " , " + myRs.getString(DBConstants.ADDRESS) + " , "
						+ myRs.getString(DBConstants.PHONE));
			}
			myRs = myStmt.executeQuery("select * from "+DBConstants.BOOK);
			while (myRs.next()) {
				System.out.println(myRs.getString(DBConstants.ISBN) + " , " + myRs.getString(DBConstants.TITLE) + " , "
						+ myRs.getString(DBConstants.PUB_NAME)+ ", " + myRs.getString(DBConstants.YEAR) + ", "
						+ myRs.getString(DBConstants.PRICE) + ", " + myRs.getString(DBConstants.CATEGORY_ID) + ", "
						+ myRs.getString(DBConstants.COPIES) + ", " + myRs.getString(DBConstants.THRESHOLD));
			}
			myRs = myStmt.executeQuery("select * from " + DBConstants.CATEGORY);
			while (myRs.next()) {
				System.out.println(myRs.getString(DBConstants.CATEGORY_NAME) + " , " + myRs.getString(DBConstants.CATEGORY_ID));
			}
			
			myRs = myStmt.executeQuery("select * from " + DBConstants.CATEGORY + " WHERE Cat_id = 2");
			while (myRs.next()) {
				System.out.println(myRs.getString(DBConstants.CATEGORY_NAME) + " , " + myRs.getString(DBConstants.CATEGORY_ID));
			}
			
			myStmt.execute("update book set copies = -2 where isbn = '1234'");
			
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}

	}

}
