package frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import main.Response;

import org.jdesktop.swingx.JXTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class UpdateBook extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cntPane;
	private JTextField isbnText;
	private JTextField titleText;
	private JTextField yearText;
	private JTextField priceText;
	private JTextField copiesText;
	private JTextField thresholdText;
	private JTextField authorText;

	/**
	 * Create the panel.
	 */
	public UpdateBook(UserFrame frame, String[] attributes, String[] authors) {
		final String oldISBN = attributes[0];
		cntPane = frame.getContentPane();
		setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0)), "Modify Book", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		setLayout(null);
		setBounds(15, 15, 750, 549);
		
		String[][] data = new String[authors.length][1];
		for (int i = 0; i < authors.length; i++) {
			data[i][0] = authors[i];
		}
		
		JXTable table = new JXTable(data, new String[] {"Author name"});
		table.setModel(new DefaultTableModel(
			data, new String[] {"Author name"}));
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setEditable(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.packAll();
		
		if (table.getPreferredSize().getWidth() < 142) {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(447, 79, 172, 218);
		
		add(scrollPane);
		
		JLabel isbnLabel = new JLabel("ISBN");
		isbnLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		isbnLabel.setForeground(Color.BLUE);
		isbnLabel.setBounds(15, 46, 146, 37);
		add(isbnLabel);
		
		JLabel titleLabel = new JLabel("Title");
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		titleLabel.setBounds(15, 99, 146, 37);
		add(titleLabel);
		
		JLabel publisherLabel = new JLabel("Publisher");
		publisherLabel.setForeground(Color.BLUE);
		publisherLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		publisherLabel.setBounds(15, 152, 146, 37);
		add(publisherLabel);
		
		JLabel yearLabel = new JLabel("Publishing year");
		yearLabel.setForeground(Color.BLUE);
		yearLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		yearLabel.setBounds(15, 205, 146, 37);
		add(yearLabel);
		
		JLabel priceLabel = new JLabel("Price");
		priceLabel.setForeground(Color.BLUE);
		priceLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		priceLabel.setBounds(15, 258, 146, 37);
		add(priceLabel);
		
		JLabel copiesLabel = new JLabel("No of copies");
		copiesLabel.setForeground(Color.BLUE);
		copiesLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		copiesLabel.setBounds(15, 364, 146, 37);
		add(copiesLabel);
		
		JLabel thresholdLabel = new JLabel("Threshold");
		thresholdLabel.setForeground(Color.BLUE);
		thresholdLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		thresholdLabel.setBounds(15, 417, 146, 37);
		add(thresholdLabel);
		
		isbnText = new JTextField();
		isbnText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		isbnText.setBounds(186, 46, 180, 37);
		isbnText.setText(attributes[0]);
		add(isbnText);
		isbnText.setColumns(10);
		
		titleText = new JTextField();
		titleText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleText.setColumns(10);
		titleText.setText(attributes[1]);
		titleText.setBounds(186, 97, 180, 37);
		add(titleText);
		
		yearText = new JTextField();
		yearText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		yearText.setColumns(10);
		yearText.setText(attributes[3]);
		yearText.setBounds(186, 203, 180, 37);
		add(yearText);

		
		priceText = new JTextField();
		priceText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		priceText.setColumns(10);
		priceText.setText(attributes[4]);
		priceText.setBounds(186, 256, 180, 37);
		add(priceText);
		
		copiesText = new JTextField();
		copiesText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		copiesText.setColumns(10);
		copiesText.setText(attributes[6]);
		copiesText.setBounds(186, 362, 180, 37);
		add(copiesText);
		
		thresholdText = new JTextField();
		thresholdText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		thresholdText.setColumns(10);
		thresholdText.setText(attributes[7]);
		thresholdText.setBounds(186, 415, 180, 37);
		add(thresholdText);
		
		String[] publishers = frame.getDBA().getPublishers();
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		DefaultComboBoxModel model = new DefaultComboBoxModel(publishers);
		@SuppressWarnings("unchecked")
		JComboBox<String> pubList = new JComboBox<String>(model);
		int pubSelected = model.getIndexOf(attributes[2]);
		pubList.setSelectedIndex(pubSelected);
		pubList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pubList.setBounds(186, 150, 180, 37);
		add(pubList);
		
		String[] categories = frame.getDBA().getCategories();
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		DefaultComboBoxModel model1 = new DefaultComboBoxModel(categories);
		@SuppressWarnings("unchecked")
		JComboBox<String> catList = new JComboBox<String>(model1);
		int catSelected = model1.getIndexOf(attributes[5]);
		catList.setSelectedIndex(catSelected);
		catList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		catList.setBounds(186, 309, 180, 37);
		add(catList);
		
		JButton confirmBtn = new JButton("Update");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] attributes = new String[] {
						isbnText.getText(), titleText.getText(),
						(String) pubList.getSelectedItem(), yearText.getText(),
						priceText.getText(), (String) catList.getSelectedItem(),
						copiesText.getText(), thresholdText.getText()
				};
				DefaultTableModel model = (DefaultTableModel)table.getModel();
		        @SuppressWarnings("unchecked")
				Vector<Vector<String>> authorsVector = model.getDataVector();
		        String[] authors = new String[authorsVector.size()];
		        int i = 0;
		        for (Vector<String> name : authorsVector) {
		        	authors[i++] = name.firstElement();
		        }
		        if (isbnText.getText().equals("") || titleText.getText().equals("") ||
						priceText.getText().equals("") || copiesText.getText().equals("") ||
						thresholdText.getText().equals("") || authors.length == 0) {
					JOptionPane.showMessageDialog(null, "please complete required data ", "Warning", JOptionPane.WARNING_MESSAGE);
				}
		        else {
		        	Response response = frame.getDBA().modifyBook(oldISBN, attributes, authors);
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
		confirmBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		BufferedImage abImg = null;
		try {
		    abImg = ImageIO.read(new File("images\\sign-check.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image aImg = abImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon aImgIcon = new ImageIcon(aImg);
		confirmBtn.setIcon(aImgIcon);
		confirmBtn.setBounds(15, 480, 146, 53);
		add(confirmBtn);
		
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
		cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		cancelBtn.setBounds(220, 480, 146, 53);
		add(cancelBtn);
		
		JLabel authorLabel = new JLabel("Authors");
		authorLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		authorLabel.setForeground(Color.BLUE);
		authorLabel.setBounds(452, 46, 96, 37);
		add(authorLabel);
		
		authorText = new JTextField();
		authorText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		authorText.setBounds(452, 362, 146, 37);
		add(authorText);
		authorText.setColumns(10);
		
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = authorText.getText();
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				if (name.length() > 0 && !existsInTable(table, new String[] {name})) {
					model.addRow(new String[] {name});
					authorText.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "empty or duplicate author name", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAdd.setBounds(620, 364, 115, 37);
		add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int itemIndex = table.getSelectedRow();
			    if(itemIndex != -1) {
			        int modelIndex = table.convertRowIndexToModel(itemIndex); // converts the row index in the view to the appropriate index in the model
			        DefaultTableModel model = (DefaultTableModel)table.getModel();
			        model.removeRow(modelIndex);
			    }
			}
		});
		btnDelete.setBounds(452, 311, 115, 37);
		add(btnDelete);
		
		JLabel catLabel = new JLabel("Category");
		catLabel.setForeground(Color.BLUE);
		catLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		catLabel.setBounds(15, 311, 146, 37);
		add(catLabel);
		
	}
	
	public boolean existsInTable(JTable table, Object[] entry) {

	    // Get row and column count
	    int rowCount = table.getRowCount();
	    int colCount = table.getColumnCount();

	    // Get Current Table Entry
	    String curEntry = "";
	    for (Object o : entry) {
	        String e = o.toString();
	        curEntry = curEntry + " " + e;
	    }

	    // Check against all entries
	    for (int i = 0; i < rowCount; i++) {
	        String rowEntry = "";
	        for (int j = 0; j < colCount; j++)
	            rowEntry = rowEntry + " " + table.getValueAt(i, j).toString();
	        if (rowEntry.equalsIgnoreCase(curEntry)) {
	            return true;
	        }
	    }
	    return false;
	}
}
