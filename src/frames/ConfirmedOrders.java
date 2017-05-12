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

import main.Response;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class ConfirmedOrders extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cntPane;
	private JTextField numText;
	private JXTable table;

	/**
	 * Create the panel.
	 */
	public ConfirmedOrders(UserFrame frame) {
		JPanel panel = this;
		cntPane = frame.getContentPane();
		setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Confirmed Orders", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(null);
		panel.setBounds(15, 15, 1000, 390);
		
		final String[] columns = frame.getDBA().getConfimedOrders().getAttributes();
		
		String[][] data = frame.getDBA().getConfimedOrders().getData();
		
		table = new JXTable(data, columns);
		table.setModel(new DefaultTableModel(
			data, columns));
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setEditable(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.packAll();
		
		if (table.getPreferredSize().getWidth() < 940) {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(15, 30, 970, 270);
		add(scrollPane);
		
		
		JButton btnRecieve = new JButton("Recieved");
		BufferedImage abImg = null;
		try {
		    abImg = ImageIO.read(new File("images\\sign-check.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image aImg = abImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon aImgIcon = new ImageIcon(aImg);
		btnRecieve.setIcon(aImgIcon);
		btnRecieve.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRecieve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int itemIndex = table.getSelectedRow();
			    if(itemIndex != -1) {
			    	Response response = frame.getDBA().deleteOrder(data[itemIndex][0]);
			    	if (response.isCorrect()) {
			    		int modelIndex = table.convertRowIndexToModel(itemIndex); // converts the row index in the view to the appropriate index in the model
				        DefaultTableModel model = (DefaultTableModel)table.getModel();
				        model.removeRow(modelIndex);
			    	}
			    	else {
			    		JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
			    	}
			    }
			}
		});
		btnRecieve.setBounds(25, 325, 200, 50);
		add(btnRecieve);
		
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
		btnBack.setBounds(286, 325, 200, 50);
		add(btnBack);
		
		JButton confirmBtn = new JButton("Confirm");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String num = numText.getText();
				int itemIndex = table.getSelectedRow();
			    if(itemIndex != -1 && !num.equals("")) {
			    	Response response = frame.getDBA().updateOrder(data[itemIndex][0], num);
			    	if (response.isCorrect()) {
			    		String[][] data = frame.getDBA().getConfimedOrders().getData();
			    		
						numText.setText("");
				
						table.setModel(new DefaultTableModel(
				    			data, columns));
			    		table.repaint();
			    		table.revalidate();
			    		
			    		JOptionPane.showMessageDialog(null, "Successful", "INFO", JOptionPane.INFORMATION_MESSAGE);
			    	}
			    	else {
			    		JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
			    	}
			    }
			}
		});
		BufferedImage cbImg = null;
		try {
		    cbImg = ImageIO.read(new File("images\\sign-check.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image cImg = cbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon cImgIcon = new ImageIcon(cImg);
		confirmBtn.setIcon(cImgIcon);
		confirmBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		confirmBtn.setBounds(806, 326, 179, 50);
		add(confirmBtn);
		
		numText = new JTextField();
		numText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		numText.setBounds(635, 338, 146, 37);
		add(numText);
		numText.setColumns(10);
		
		JLabel numLabel = new JLabel("New Quantity");
		numLabel.setBounds(635, 316, 146, 20);
		add(numLabel);
	}
}
