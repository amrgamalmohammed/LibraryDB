package frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import main.Book;
import main.ITable;
import main.Response;

import javax.swing.JTextField;

public class SearchResult extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cntPane;
	private JTextField addText;
	private JTextField orderText;

	/**
	 * Create the panel.
	 */
	public SearchResult(UserFrame frame, ITable tab, boolean manager) {
		JPanel panel = this;
		cntPane = frame.getContentPane();
		setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Search Result", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		setLayout(null);
		panel.setBounds(15, 15, 1100, 450);
		
		final String[] columns = tab.getAttributes();
		
		String[][] data = tab.getData();
		
		JXTable table = new JXTable(data, columns);
		table.setModel(new DefaultTableModel(
			data, columns));
		table.setEditable(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.packAll();
		
		if (table.getPreferredSize().getWidth() < 1040) {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(15, 30, 1070, 270);
		scrollPane.revalidate();
		add(scrollPane);
		
		JButton btnOrder = new JButton("Place Order");
		btnOrder.setVisible(manager);
		btnOrder.setFont(new Font("Tahoma", Font.BOLD, 18));
		BufferedImage abImg = null;
		try {
		    abImg = ImageIO.read(new File("images\\sign-check.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image aImg = abImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon aImgIcon = new ImageIcon(aImg);
		btnOrder.setIcon(aImgIcon);
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int itemIndex = table.getSelectedRow();
			    if(itemIndex != -1) {
			    	int modelIndex = table.convertRowIndexToModel(itemIndex);
		    		String[] item = data[modelIndex];
		    		String isbn = item[0];
		    		String copies = orderText.getText();
		    		Response response = frame.getDBA().placeOrder(isbn, copies);
		    		if (response.isCorrect()) {
						JOptionPane.showMessageDialog(null, "Operation completed successfully", "INFO", JOptionPane.INFORMATION_MESSAGE);
						orderText.setText("");
		    		}
		    		else {
						JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		    		}
			    }
			}
		});
		btnOrder.setBounds(15, 316, 200, 50);
		add(btnOrder);
		
		JButton btnModify = new JButton("Modify");
		btnModify.setVisible(manager);
		BufferedImage cbImg = null;
		try {
		    cbImg = ImageIO.read(new File("images\\modify.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image cImg = cbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon cImgIcon = new ImageIcon(cImg);
		btnModify.setIcon(cImgIcon);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int itemIndex = table.getSelectedRow();
			    if(itemIndex != -1) {
			    	int modelIndex = table.convertRowIndexToModel(itemIndex);
			    	String[] item = data[modelIndex];
			    	String[] attributes = item;
			    	System.out.println(attributes.length);
			    	String authorList = item[item.length - 1];
			    	
			    	String[] authors = authorList.split(",");
			    	
			    	cntPane.removeAll();
					JPanel panel = new UpdateBook(frame, attributes, authors);
					frame.setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		cntPane.add(panel);
		    		cntPane.revalidate();
		    		cntPane.repaint();
			    }
			}
		});
		btnModify.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnModify.setBounds(15, 382, 200, 50);
		add(btnModify);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnBack.setBounds(885, 384, 200, 50);
		add(btnBack);
		
		JButton btnAddToCart = new JButton("Add to cart");
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int itemIndex = table.getSelectedRow();
			     if(itemIndex != -1 && !addText.getText().equals("")) {
			        int modelIndex = table.convertRowIndexToModel(itemIndex);
			        String[] item = data[modelIndex];
			        String isbn = item[0];
			        String title = item[1];
			        String price = item[4];
			        String copies = addText.getText();
			        Book book = new Book(isbn, title, price, copies);
			        frame.getCart().addBook(book);
			        JOptionPane.showMessageDialog(null, "Successful", "INFO", JOptionPane.INFORMATION_MESSAGE);
			        addText.setText("");
			    }
			}
		});
		btnAddToCart.setVisible(true);
		BufferedImage adbImg = null;
		try {
		    adbImg = ImageIO.read(new File("images\\add.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image adImg = adbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon adImgIcon = new ImageIcon(adImg);
		btnAddToCart.setIcon(adImgIcon);
		btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAddToCart.setBounds(885, 316, 200, 50);
		add(btnAddToCart);
		
		addText = new JTextField();
		addText.setVisible(true);
		addText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addText.setBounds(684, 316, 146, 50);
		add(addText);
		addText.setColumns(10);
		
		orderText = new JTextField();
		orderText.setVisible(manager);
		orderText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		orderText.setColumns(10);
		orderText.setBounds(256, 316, 146, 50);
		add(orderText);

	}

}
