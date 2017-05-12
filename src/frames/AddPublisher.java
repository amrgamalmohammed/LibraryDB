package frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

public class AddPublisher extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	
	private JTextField nameText;
	private JTextField addressText;
	private JTextField phoneText;
	private Container cntPane;
	
	public AddPublisher(UserFrame frame) {
		JPanel panel = this;
		cntPane = frame.getContentPane();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Add Publisher", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel.setBounds(15, 15, 450, 320);
		panel.setLayout(null);
		setLayout(null);
		
		nameText = new JTextField();
		nameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nameText.setBounds(170, 33, 233, 37);
		panel.add(nameText);
		nameText.setColumns(10);
		
		addressText = new JTextField();
		addressText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addressText.setColumns(10);
		addressText.setBounds(170, 100, 233, 37);
		panel.add(addressText);
		
		phoneText = new JTextField();
		phoneText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phoneText.setColumns(10);
		phoneText.setBounds(170, 173, 233, 37);
		panel.add(phoneText);
		
		JLabel nameLabel = new JLabel("Publisher Name");
		nameLabel.setForeground(Color.BLUE);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		nameLabel.setBounds(15, 33, 128, 37);
		panel.add(nameLabel);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setForeground(Color.BLUE);
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		addressLabel.setBounds(15, 100, 89, 37);
		panel.add(addressLabel);
		
		JLabel phoneLabel = new JLabel("Phone");
		phoneLabel.setForeground(Color.BLUE);
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		phoneLabel.setBounds(15, 173, 94, 37);
		panel.add(phoneLabel);
		
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
				String name = nameText.getText();
				String address = addressText.getText();
				String phone = phoneText.getText();
				if (name.equals("") || address.equals("")) {
					JOptionPane.showMessageDialog(null, "please complete required data ", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					Response response = frame.getDBA().addPublisher(name, address, phone);
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
		btnAdd.setBounds(34, 249, 166, 46);
		panel.add(btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		BufferedImage cbImg = null;
		try {
		    cbImg = ImageIO.read(new File("images\\Cancel.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image cImg = cbImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon cImgIcon = new ImageIcon(cImg);
		btnCancel.setIcon(cImgIcon);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cntPane.removeAll();
				cntPane.revalidate();
				cntPane.repaint();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnCancel.setBounds(237, 249, 166, 46);
		panel.add(btnCancel);
	}

}
