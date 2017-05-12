package frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.Response;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AddBook extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	
	private JTextField ISBNText;
	private JTextField priceText;
	private JTextField thresholdText;
	private JTextField titleText;
	private JTextField copiesText;
	private JTextField authorText;
	private Container cntPane;
	
	private Set<String> authors;
	private JTextField yearText;
	
	public AddBook(UserFrame frame) {
		JPanel panel = this;
		cntPane = frame.getContentPane();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Add Book", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel.setBounds(15, 15, 760, 500);
		panel.setLayout(null);
		setLayout(null);
		
		authors = new HashSet<>();
		
		ISBNText = new JTextField();
		ISBNText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ISBNText.setBounds(105, 31, 233, 37);
		panel.add(ISBNText);
		ISBNText.setColumns(10);
		
		priceText = new JTextField();
		priceText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		priceText.setColumns(10);
		priceText.setBounds(105, 171, 233, 37);
		panel.add(priceText);
		
		thresholdText = new JTextField();
		thresholdText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		thresholdText.setColumns(10);
		thresholdText.setBounds(105, 242, 233, 37);
		panel.add(thresholdText);
		
		JLabel ISBNLabel = new JLabel("ISBN");
		ISBNLabel.setForeground(Color.BLUE);
		ISBNLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		ISBNLabel.setBounds(15, 33, 89, 37);
		panel.add(ISBNLabel);
		
		JLabel publisherLabel = new JLabel("Publisher");
		publisherLabel.setForeground(Color.BLUE);
		publisherLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		publisherLabel.setBounds(15, 100, 89, 37);
		panel.add(publisherLabel);
		
		titleText = new JTextField();
		titleText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleText.setColumns(10);
		titleText.setBounds(501, 31, 233, 37);
		panel.add(titleText);
		
		JLabel yearLabel = new JLabel("Publication Year");
		yearLabel.setForeground(Color.BLUE);
		yearLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		yearLabel.setBounds(353, 102, 159, 37);
		panel.add(yearLabel);
		
		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setForeground(Color.BLUE);
		categoryLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		categoryLabel.setBounds(353, 173, 98, 37);
		panel.add(categoryLabel);
		
		String[] categories = frame.getDBA().getCategories();
		
		JComboBox<String> catList = new JComboBox<String>(categories);
		catList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		catList.setSelectedIndex(0);
		catList.setBounds(501, 173, 233, 37);
		panel.add(catList);
		
		JLabel titleLabel = new JLabel("Title");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		titleLabel.setBounds(353, 33, 98, 37);
		panel.add(titleLabel);
		
		JLabel priceLabel = new JLabel("Price");
		priceLabel.setForeground(Color.BLUE);
		priceLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		priceLabel.setBounds(15, 173, 94, 37);
		panel.add(priceLabel);
		
		JLabel thresholdLabel = new JLabel("Threshold");
		thresholdLabel.setForeground(Color.BLUE);
		thresholdLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		thresholdLabel.setBounds(15, 244, 98, 37);
		panel.add(thresholdLabel);
		
		String[] publishers = frame.getDBA().getPublishers();
		
		JComboBox<String> pubList = new JComboBox<String>(publishers);
		pubList.setSelectedIndex(0);
		pubList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pubList.setBounds(105, 100, 233, 37);
		add(pubList);
		
		JButton btnAdd = new JButton("Confirm");
		BufferedImage abImg = null;
		try {
		    abImg = ImageIO.read(new File("images\\sign-check.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image aImg = abImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon aImgIcon = new ImageIcon(aImg);
		btnAdd.setIcon(aImgIcon);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] _authors = new String[authors.size()];
				_authors = authors.toArray(_authors);
				String[] attributes = {
						ISBNText.getText(), titleText.getText(),
						(String) pubList.getSelectedItem(), yearText.getText(),
						priceText.getText(), (String) catList.getSelectedItem(),
						copiesText.getText(), thresholdText.getText()
				};
				if (ISBNText.getText().equals("") || titleText.getText().equals("") ||
						((String) pubList.getSelectedItem()).equals("") || priceText.getText().equals("") ||
						((String) catList.getSelectedItem()).equals("") || copiesText.getText().equals("") ||
						thresholdText.getText().equals("") || _authors.length == 0) {
					JOptionPane.showMessageDialog(null, "please complete required data ", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					Response response = frame.getDBA().addNewBook(attributes, _authors);
					if (response.isCorrect()) {
						cntPane.removeAll();
						cntPane.revalidate();
						cntPane.repaint();
						JOptionPane.showMessageDialog(null, "Operation completed successfully", "INFO", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAdd.setBounds(199, 430, 166, 49);
		panel.add(btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
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
		btnCancel.setIcon(cImgIcon);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnCancel.setBounds(475, 430, 166, 49);
		panel.add(btnCancel);
		
		JLabel copieslabel = new JLabel("No Of Copies");
		copieslabel.setForeground(Color.BLUE);
		copieslabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		copieslabel.setBounds(353, 244, 159, 37);
		add(copieslabel);
		
		copiesText = new JTextField();
		copiesText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		copiesText.setColumns(10);
		copiesText.setBounds(501, 242, 233, 37);
		add(copiesText);
		
		JLabel authorLabel = new JLabel("Author");
		authorLabel.setForeground(Color.BLUE);
		authorLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		authorLabel.setBounds(15, 325, 94, 37);
		add(authorLabel);
		
		authorText = new JTextField();
		authorText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		authorText.setColumns(10);
		authorText.setBounds(105, 323, 233, 37);
		add(authorText);
		
		JButton btnAuthor = new JButton("Add");
		btnAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!authorText.getText().equals("")) {
					authors.add(authorText.getText());
					authorText.setText("");
				}
			}
		});
		btnAuthor.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAuthor.setBounds(353, 324, 83, 37);
		add(btnAuthor);
		
		yearText = new JTextField();
		yearText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		yearText.setColumns(10);
		yearText.setBounds(501, 100, 233, 37);
		add(yearText);
		
	}
}
