package frames;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import main.Response;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignupFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fNameText;
	private JTextField userText;
	private JTextField phoneText;
	private JTextField emailText;
	private JTextField lNameText;
	private JTextField addressText;
	private JPasswordField passwordText;

	/**
	 * Create the frame.
	 */
	public SignupFrame(MainFrame frame) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Sign Up", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel.setBounds(15, 16, 776, 346);
		contentPane.add(panel);
		panel.setLayout(null);
		
		fNameText = new JTextField();
		fNameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fNameText.setBounds(121, 33, 233, 37);
		panel.add(fNameText);
		fNameText.setColumns(10);
		
		userText = new JTextField();
		userText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userText.setColumns(10);
		userText.setBounds(121, 100, 233, 37);
		panel.add(userText);
		
		phoneText = new JTextField();
		phoneText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phoneText.setColumns(10);
		phoneText.setBounds(121, 173, 233, 37);
		panel.add(phoneText);
		
		emailText = new JTextField();
		emailText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		emailText.setColumns(10);
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
		panel.add(addressText);
		
		JLabel lNameLabel = new JLabel("Last name");
		lNameLabel.setForeground(Color.BLUE);
		lNameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lNameLabel.setBounds(390, 33, 98, 37);
		panel.add(lNameLabel);
		
		passwordText = new JPasswordField();
		passwordText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordText.setBounds(503, 100, 233, 37);
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
		
		JButton btnSignUp = new JButton("Sign up");
		BufferedImage sbImg = null;
		try {
		    sbImg = ImageIO.read(new File("images\\sign-check.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image sImg = sbImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon sImgIcon = new ImageIcon(sImg);
		btnSignUp.setIcon(sImgIcon);
		btnSignUp.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				String[] attributes = {
						fNameText.getText(), lNameText.getText(),
						userText.getText(), passwordText.getText(),
						emailText.getText(), phoneText.getText(),
						addressText.getText(), "false"
				};
				
				if (fNameLabel.getText().equals("") || lNameLabel.getText().equals("") ||
						userLabel.getText().equals("") || passwordLabel.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "please complete required data ", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					Response response = frame.getDBA().addUser(attributes);
					if (response.isCorrect()) {
						JFrame userFrame = new UserFrame(frame, userLabel.getText(), false);
						dispose();
						userFrame.show();
					}
					else {
						JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSignUp.setBounds(570, 244, 166, 37);
		panel.add(btnSignUp);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				frame.setVisible(true);
			}
		});
		BufferedImage bbImg = null;
		try {
		    bbImg = ImageIO.read(new File("images\\Ago_arrow_arrow_left_back_previous_direction_left-128.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image bImg = bbImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon bImgIcon = new ImageIcon(bImg);
		btnBack.setIcon(bImgIcon);
		btnBack.setBounds(570, 293, 166, 37);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(btnBack);
	}
}
