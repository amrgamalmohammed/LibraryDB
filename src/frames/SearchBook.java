package frames;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import main.DBConstants;
import main.ITable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchBook extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cntPane;
	private JTextField isbnText;
	private JTextField titleText;
	private JTextField yearText;
	private JTextField priceText;
	private JButton searchBtn;
	private int checks;

	/**
	 * Create the panel.
	 */
	public SearchBook(UserFrame frame, boolean manager) {
		cntPane = frame.getContentPane();
		setLayout(null);
		setBounds(15, 15, 1070, 510);
		checks = 0;
		
		JPanel authorPanel = new JPanel();
		authorPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Search Book", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		authorPanel.setBounds(15, 16, 199, 421);
		add(authorPanel);
		authorPanel.setLayout(null);
		
		String[] authors = frame.getDBA().getAuthors();
		JComboBox<String> authorList = new JComboBox<String>(authors);
		authorList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		authorList.setSelectedIndex(0);
		authorList.setEnabled(false);
		authorList.setBounds(15, 83, 169, 37);
		authorPanel.add(authorList);
		
		JLabel authorImg = new JLabel("");
		BufferedImage authbImg = null;
		try {
		    authbImg = ImageIO.read(new File("images\\Text-Document.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image authImg = authbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon authImgIcon = new ImageIcon(authImg);
		authorImg.setIcon(authImgIcon);
		authorImg.setBounds(15, 30, 40, 40);
		authorPanel.add(authorImg);
		
		JCheckBox authorCheck = new JCheckBox("");
		authorCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (authorCheck.isSelected()) {
					checks += 1;
					authorList.setEnabled(true);
				}
				else {
					checks -= 1;
					authorList.setEnabled(false);
				}
				
				enableSearchButton ();
			}
		});
		authorCheck.setBounds(85, 30, 29, 29);
		authorPanel.add(authorCheck);
		
		JPanel publisherPanel = new JPanel();
		publisherPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0)), "Publisher", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		publisherPanel.setBounds(229, 16, 199, 421);
		add(publisherPanel);
		publisherPanel.setLayout(null);
		
		
		String[] publishers = frame.getDBA().getPublishers();
		JComboBox<String> publisherList = new JComboBox<String>(publishers);
		publisherList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		publisherList.setSelectedIndex(0);
		publisherList.setEnabled(false);
		publisherList.setBounds(15, 86, 169, 37);
		publisherPanel.add(publisherList);
		
		JLabel publisgerImg = new JLabel("");
		BufferedImage pubbImg = null;
		try {
		    pubbImg = ImageIO.read(new File("images\\Building_business_real_house_mall_store_company_state-128.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image pubImg = pubbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon pubImgIcon = new ImageIcon(pubImg);
		publisgerImg.setIcon(pubImgIcon);
		publisgerImg.setBounds(15, 32, 40, 40);
		publisherPanel.add(publisgerImg);
		
		JCheckBox publisherCheck = new JCheckBox("");
		publisherCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (publisherCheck.isSelected()) {
					checks += 1;
					publisherList.setEnabled(true);
				}
				else {
					checks -= 1;
					publisherList.setEnabled(false);
				}
				
				enableSearchButton ();
			}
		});
		publisherCheck.setBounds(83, 32, 29, 29);
		publisherPanel.add(publisherCheck);
		
		JPanel catPanel = new JPanel();
		catPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Category", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		catPanel.setBounds(443, 16, 199, 421);
		add(catPanel);
		catPanel.setLayout(null);
		
		
		String[] categories = frame.getDBA().getCategories();
		JComboBox<String> catList = new JComboBox<String>(categories);
		catList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		catList.setSelectedIndex(0);
		catList.setEnabled(false);
		catList.setBounds(15, 89, 169, 37);
		catPanel.add(catList);
		
		JLabel catImg = new JLabel("");
		catImg.setIcon(new ImageIcon("images\\category.png"));
		catImg.setBounds(15, 32, 40, 40);
		catPanel.add(catImg);
		
		JCheckBox catCheck = new JCheckBox("");
		catCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (catCheck.isSelected()) {
					checks += 1;
					catList.setEnabled(true);
				}
				else {
					checks -= 1;
					catList.setEnabled(false);
				}
				
				enableSearchButton ();
			}
		});
		catCheck.setBounds(80, 32, 29, 29);
		catPanel.add(catCheck);
		
		JPanel otherPanel = new JPanel();
		otherPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Other", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		otherPanel.setBounds(657, 16, 398, 355);
		add(otherPanel);
		otherPanel.setLayout(null);
		
		JLabel isbnLabel = new JLabel("ISBN");
		isbnLabel.setForeground(Color.BLUE);
		isbnLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		isbnLabel.setBounds(49, 47, 69, 37);
		otherPanel.add(isbnLabel);
		
		JLabel titleLabel = new JLabel("Title");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		titleLabel.setBounds(49, 132, 69, 37);
		otherPanel.add(titleLabel);
		
		JLabel yearLabel = new JLabel("Publishing Year");
		yearLabel.setForeground(Color.BLUE);
		yearLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		yearLabel.setBounds(52, 214, 136, 37);
		otherPanel.add(yearLabel);
		
		JLabel priceLabel = new JLabel("Price");
		priceLabel.setForeground(Color.BLUE);
		priceLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		priceLabel.setBounds(49, 293, 69, 37);
		otherPanel.add(priceLabel);
		
		isbnText = new JTextField();
		isbnText.setEnabled(false);
		isbnText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		isbnText.setBounds(203, 47, 180, 37);
		otherPanel.add(isbnText);
		isbnText.setColumns(10);
		
		titleText = new JTextField();
		titleText.setEnabled(false);
		titleText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleText.setColumns(10);
		titleText.setBounds(203, 132, 180, 37);
		otherPanel.add(titleText);
		
		yearText = new JTextField();
		yearText.setEnabled(false);
		yearText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		yearText.setColumns(10);
		yearText.setBounds(203, 214, 180, 37);
		otherPanel.add(yearText);
		
		priceText = new JTextField();
		priceText.setEnabled(false);
		priceText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		priceText.setColumns(10);
		priceText.setBounds(203, 293, 180, 37);
		otherPanel.add(priceText);
		
		JCheckBox isbnCheck = new JCheckBox("");
		isbnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isbnCheck.isSelected()) {
					checks += 1;
					isbnText.setEnabled(true);
				}
				else {
					checks -= 1;
					isbnText.setEnabled(false);
				}
				
				enableSearchButton ();
			}
		});
		isbnCheck.setBounds(9, 51, 29, 29);
		otherPanel.add(isbnCheck);
		
		JCheckBox titleCheck = new JCheckBox("");
		titleCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (titleCheck.isSelected()) {
					checks += 1;
					titleText.setEnabled(true);
				}
				else {
					checks -= 1;
					titleText.setEnabled(false);
				}
				
				enableSearchButton ();
			}
		});
		titleCheck.setBounds(9, 136, 29, 29);
		otherPanel.add(titleCheck);
		
		JCheckBox yearCheck = new JCheckBox("");
		yearCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (yearCheck.isSelected()) {
					checks += 1;
					yearText.setEnabled(true);
				}
				else {
					checks -= 1;
					yearText.setEnabled(false);
				}
				
				enableSearchButton ();
			}
		});
		yearCheck.setBounds(9, 218, 29, 29);
		otherPanel.add(yearCheck);
		
		JCheckBox priceCheck = new JCheckBox("");
		priceCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (priceCheck.isSelected()) {
					checks += 1;
					priceText.setEnabled(true);
				}
				else {
					checks -= 1;
					priceText.setEnabled(false);
				}
				
				enableSearchButton ();
			}
		});
		priceCheck.setBounds(9, 297, 29, 29);
		otherPanel.add(priceCheck);
		
		searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] keys = new String[checks];
				String[] values = new String[checks];
				int index = 0;
				if (authorCheck.isSelected()) {
					keys[index] = DBConstants.AUTHOR_NAME;
					values[index] = authors[authorList.getSelectedIndex()];
					index++;
				}
				if (publisherCheck.isSelected()) {
					keys[index] = DBConstants.PUB_NAME;
					values[index] = publishers[publisherList.getSelectedIndex()];
					index++;
				}
				if (catCheck.isSelected()) {
					keys[index] = DBConstants.CATEGORY_NAME;
					values[index] = categories[catList.getSelectedIndex()];
					index++;
				}
				if (isbnCheck.isSelected() && !isbnText.getText().equals("")) {
					keys[index] = DBConstants.ISBN;
					values[index] = isbnText.getText();
					index++;
				}
				if (titleCheck.isSelected() && !titleText.getText().equals("")) {
					keys[index] = DBConstants.TITLE;
					values[index] = titleText.getText();
					index++;
				}
				if (priceCheck.isSelected() && !priceText.getText().equals("")) {
					keys[index] = DBConstants.PRICE;
					values[index] = priceText.getText();
					index++;
				}
				if (yearCheck.isSelected() && !yearText.getText().equals("")) {
					keys[index] = DBConstants.YEAR;
					values[index] = yearText.getText();
					index++;
				}
				if (index > 0) {
					ITable table = frame.getDBA().searchBook(keys, values);
					cntPane.removeAll();
					JPanel panel = new SearchResult(frame, table, manager);
					frame.setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		cntPane.add(panel);
		    		cntPane.revalidate();
		    		cntPane.repaint();
				}
			}
		});
		searchBtn.setEnabled(false);
		BufferedImage sbImg = null;
		try {
		    sbImg = ImageIO.read(new File("images\\search-128.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image sImg = sbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon sImgIcon = new ImageIcon(sImg);
		searchBtn.setIcon(sImgIcon);
		searchBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		searchBtn.setBounds(657, 387, 171, 50);
		add(searchBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cntPane.removeAll();
				cntPane.revalidate();
				cntPane.repaint();
			}
		});
		BufferedImage cbImg = null;
		try {
		    cbImg = ImageIO.read(new File("images\\Cancel.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image cImg = cbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon cImgIcon = new ImageIcon(cImg);
		cancelBtn.setIcon(cImgIcon);
		cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		cancelBtn.setBounds(884, 387, 171, 50);
		add(cancelBtn);
		
		JButton btnGetAllBooks = new JButton("Get All Books");
		btnGetAllBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ITable table = frame.getDBA().getAllBooks();
				System.out.println(table);
				cntPane.removeAll();
				JPanel panel = new SearchResult(frame, table, manager);
				frame.setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
	    		panel.setBackground(Color.WHITE);
	    		cntPane.add(panel);
	    		cntPane.revalidate();
	    		cntPane.repaint();
			}
		});
		btnGetAllBooks.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnGetAllBooks.setBounds(657, 449, 398, 50);
		add(btnGetAllBooks);
		
	}
	
	private void enableSearchButton() {
		if (checks > 0) {
			searchBtn.setEnabled(true);
		}
		else {
			searchBtn.setEnabled(false);
		}
		
	}
}
