package main;

public interface ITable {
	
	String[] getAttributes() ;

	String[][] getData ();
	
	void setAttributes (String[] attributes);
	
	void setData (String[][] data);
}
