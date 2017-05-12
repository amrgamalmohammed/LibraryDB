package frames;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import org.jdesktop.swingx.JXTable;

import main.Book;
import main.ICart;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShopingCart extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cntPane;
	private ICart cart;

	/**
	 * Create the panel.
	 */
	public ShopingCart(UserFrame frame) {
		setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "My Cart", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(null);
		setBounds(15, 15, 670, 395);
		cntPane = frame.getContentPane();
		cart = frame.getCart();
		
		final String[] columns = {"ISBN", "Title", "Price", "Number", "Total Price"};
		
		
		String[][] data = createCartData(cart);
		
		JXTable table = new JXTable(data, columns);
		table.setModel(new DefaultTableModel(
			data, columns));
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setEditable(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.packAll();
		
		if (table.getPreferredSize().getWidth() < 606) {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(15, 30, 636, 269);
		
		add(scrollPane);
		
		
		JButton btnDelete = new JButton("Delete Item");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 18));
		BufferedImage cbImg = null;
		try {
		    cbImg = ImageIO.read(new File("images\\Cancel.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image cImg = cbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon cImgIcon = new ImageIcon(cImg);
		btnDelete.setIcon(cImgIcon);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 int itemIndex = table.getSelectedRow();
			     if(itemIndex != -1) {
			        int modelIndex = table.convertRowIndexToModel(itemIndex); // converts the row index in the view to the appropriate index in the model
			        DefaultTableModel model = (DefaultTableModel)table.getModel();
			        model.removeRow(modelIndex);
			        
			        String isbn = data[itemIndex][0];
			        cart.removeBook(isbn);
			    }
			}
		});
		btnDelete.setBounds(36, 325, 200, 50);
		add(btnDelete);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cntPane.removeAll();
				cntPane.revalidate();
				cntPane.repaint();
			}
		});
		BufferedImage bbImg = null;
		try {
		    bbImg = ImageIO.read(new File("images\\Ago_arrow_arrow_left_back_previous_direction_left-128.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image bImg = bbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon bImgIcon = new ImageIcon(bImg);
		btnBack.setIcon(bImgIcon);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnBack.setBounds(421, 325, 200, 50);
		add(btnBack);

	}
	
	public String[][] createCartData(ICart cart) {
		ArrayList<Book> list = cart.getBooks();
		String[][] data = new String[list.size()][5];
		int i = 0;
		for (Book book : list) {
			String[] temp = {book.getISBN(), book.getTitle(), book.getPrice(), book.getCopies(), book.getTotal()};
			data[i++] = temp;
		}
		return data;
	}
}
