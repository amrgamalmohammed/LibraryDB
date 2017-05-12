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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.Response;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UpdateAccount extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private Container cntPane;
	private JTextField fNameText;
	private JTextField userText;
	private JTextField phoneText;
	private JTextField emailText;
	private JTextField lNameText;
	private JTextField addressText;
	private JPasswordField passwordText;
	
	public UpdateAccount(UserFrame frame, String[] attributes, String oldName) {
		JPanel panel = this;
		cntPane = frame.getContentPane();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Update Account", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel.setBounds(15, 15, 760, 380);
		panel.setLayout(null);
		setLayout(null);
		
		fNameText = new JTextField();
		fNameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fNameText.setBounds(121, 33, 233, 37);
		panel.add(fNameText);
		fNameText.setText(attributes[0]);
		fNameText.setColumns(10);
		
		userText = new JTextField();
		userText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userText.setColumns(10);
		userText.setText(attributes[2]);
		userText.setBounds(121, 100, 233, 37);
		panel.add(userText);
		
		phoneText = new JTextField();
		phoneText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phoneText.setColumns(10);
		phoneText.setText(attributes[5]);
		phoneText.setBounds(121, 173, 233, 37);
		panel.add(phoneText);
		
		emailText = new JTextField();
		emailText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		emailText.setColumns(10);
		emailText.setText(attributes[4]);
		emailText.setBounds(121, 244, 355, 37);
		panel.add(emailText);
		
		JLabel fNameLabel = new JLabel("First name");
		fNameLabel.setForeground(Color.BLUE);
		fNameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		fNameLabel.setBounds(15, 33, 89, 37);
		panel.add(fNameLabel);
		
		JLabel userLabel = new JLabel("Username");
		userLabel.setForeground(Color.BLUE);
		userLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		userLabel.setBounds(15, 100, 89, 37);
		panel.add(userLabel);
		
		lNameText = new JTextField();
		lNameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lNameText.setColumns(10);
		lNameText.setText(attributes[1]);
		lNameText.setBounds(503, 33, 233, 37);
		panel.add(lNameText);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.BLUE);
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		passwordLabel.setBounds(390, 100, 98, 37);
		panel.add(passwordLabel);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setForeground(Color.BLUE);
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		addressLabel.setBounds(390, 173, 98, 37);
		panel.add(addressLabel);
		
		addressText = new JTextField();
		addressText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addressText.setColumns(10);
		addressText.setBounds(503, 173, 233, 37);
		addressText.setText(attributes[6]);
		panel.add(addressText);
		
		JLabel lNameLabel = new JLabel("Last name");
		lNameLabel.setForeground(Color.BLUE);
		lNameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lNameLabel.setBounds(390, 33, 98, 37);
		panel.add(lNameLabel);
		
		passwordText = new JPasswordField();
		passwordText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordText.setBounds(503, 100, 233, 37);
		passwordText.setText(attributes[3]);
		panel.add(passwordText);
		
		JLabel phoneLabel = new JLabel("Phone");
		phoneLabel.setForeground(Color.BLUE);
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		phoneLabel.setBounds(15, 173, 94, 37);
		panel.add(phoneLabel);
		
		JLabel emailLabel = new JLabel("E-mail");
		emailLabel.setForeground(Color.BLUE);
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		emailLabel.setBounds(15, 244, 98, 37);
		panel.add(emailLabel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean manager = frame.getDBA().isManager(oldName);
				String[] attributes = {
						fNameText.getText(), lNameText.getText(),
						userText.getText(), passwordText.getText(),
						emailText.getText(), phoneText.getText(),
						addressText.getText(), String.valueOf(manager)
				};
				
				if (fNameLabel.getText().equals("") || lNameLabel.getText().equals("") ||
						userLabel.getText().equals("") || passwordLabel.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "please complete required data ", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					Response response = frame.getDBA().updateUser(oldName, attributes);
					if (response.isCorrect()) {
						frame.setUserName(userText.getText());
						cntPane.removeAll();
						cntPane.revalidate();
						cntPane.repaint();
						JOptionPane.showMessageDialog(null, "Account updated successfully", "INFO", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		BufferedImage abImg = null;
		try {
		    abImg = ImageIO.read(new File("images\\sign-check.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image aImg = abImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon aImgIcon = new ImageIcon(aImg);
		btnUpdate.setIcon(aImgIcon);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnUpdate.setBounds(163, 313, 166, 48);
		panel.add(btnUpdate);
		
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
		btnCancel.setBounds(428, 313, 166, 48);
		panel.add(btnCancel);
	}

}
