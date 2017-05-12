package main;

import javax.swing.JFrame;

import frames.MainFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBAccessInterface dba = new DBAccess();
		ICart cart = new Cart();
		ITable table = new Table();
		JFrame mainFrame = new MainFrame(dba, cart, table);
		mainFrame.setVisible(true);
	}

}
