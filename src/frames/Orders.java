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

public class Orders extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cntPane;

	/**
	 * Create the panel.
	 */
	public Orders(UserFrame frame) {
		JPanel panel = this;
		cntPane = frame.getContentPane();
		setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Unwantched Orders", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(null);
		panel.setBounds(15, 15, 1000, 390);
		
		final String[] columns = frame.getDBA().getUnConfirmedOrders().getAttributes();
		
		String[][] data = frame.getDBA().getUnConfirmedOrders().getData();
		
		JXTable table = new JXTable(data, columns);
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
		
		JButton btnOrder = new JButton("Order");
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
			    	Response response = frame.getDBA().confirmOrder(data[itemIndex][0]);
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
		btnOrder.setBounds(15, 325, 200, 50);
		add(btnOrder);
		
		JButton btnReject = new JButton("Reject");
		BufferedImage cbImg = null;
		try {
		    cbImg = ImageIO.read(new File("images\\Cancel.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image cImg = cbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon cImgIcon = new ImageIcon(cImg);
		btnReject.setIcon(cImgIcon);
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int itemIndex = table.getSelectedRow();
			    if(itemIndex != -1) {
			    	Response response = frame.getDBA().deleteOrder(data[itemIndex][0]);
			    	if (response.isCorrect()) {
			    		int modelIndex = table.convertRowIndexToModel(itemIndex); // converts the row index in the view to the appropriate index in the model
				        DefaultTableModel model = (DefaultTableModel)table.getModel();
				        model.removeRow(modelIndex);
			    	}
			    }
			}
		});
		btnReject.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReject.setBounds(235, 325, 200, 50);
		add(btnReject);
		
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
		btnBack.setBounds(450, 325, 200, 50);
		add(btnBack);

	}
}
