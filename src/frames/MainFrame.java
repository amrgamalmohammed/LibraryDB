package frames;

import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import java.awt.Color;

import javax.swing.border.LineBorder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import main.DBAccessInterface;
import main.ICart;
import main.ITable;
import main.Response;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameText;
	private JLabel passLabel;
	private JLabel nameImg;
	private JLabel passImg;
	private JPasswordField passText;
	private JButton signBtn;
	private MainFrame mainFrame;
	private DBAccessInterface dba;
	private ICart cart;
    private ITable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(null, null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame(DBAccessInterface dba, ICart cart, ITable table) {
		
		mainFrame = this;
		this.cart = cart;
		this.dba = dba;
		this.table = table;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 370);
		contentPane = new JPanel();
		contentPane.setForeground(Color.RED);
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setForeground(Color.PINK);
		loginPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Log In", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 255)));
		loginPanel.setBounds(15, 16, 528, 277);
		contentPane.add(loginPanel);
		loginPanel.setLayout(null);
		
		
		nameImg = new JLabel("");
		nameImg.setBounds(398, 47, 41, 37);
		loginPanel.add(nameImg);
		nameImg.setHorizontalAlignment(SwingConstants.CENTER);
		BufferedImage bnImg = null;
		try {
		    bnImg = ImageIO.read(new File("images\\User.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image nImg = bnImg.getScaledInstance(nameImg.getWidth(), nameImg.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon nImgIcon = new ImageIcon(nImg);
		nameImg.setIcon(nImgIcon);
		
		passText = new JPasswordField();
		passText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passText.setBounds(169, 115, 200, 37);
		loginPanel.add(passText);
		
		
		passImg = new JLabel("");
		passImg.setHorizontalAlignment(SwingConstants.CENTER);
		passImg.setBounds(398, 110, 41, 42);
		loginPanel.add(passImg);
		BufferedImage bpImg = null;
		try {
		    bpImg = ImageIO.read(new File("images\\Lock_protected_safe_privacy_password_security-128.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image pImg = bpImg.getScaledInstance(nameImg.getWidth(), nameImg.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon pImgIcon = new ImageIcon(pImg);
		passImg.setIcon(pImgIcon);
		
		JLabel nameLabel = new JLabel("Username");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setForeground(Color.BLUE);
		nameLabel.setBackground(Color.WHITE);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		nameLabel.setBounds(31, 47, 99, 37);
		loginPanel.add(nameLabel);
		
		passLabel = new JLabel("Password");
		passLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passLabel.setForeground(Color.BLUE);
		passLabel.setBackground(Color.MAGENTA);
		passLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		passLabel.setBounds(31, 112, 99, 42);
		loginPanel.add(passLabel);
		
		nameText = new JTextField();
		nameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nameText.setBounds(169, 47, 200, 36);
		loginPanel.add(nameText);
		nameText.setColumns(10);
		
		JButton loginBtn = new JButton("Log In");
		loginBtn.setIcon(new ImageIcon("images\\login.png"));
		loginBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		loginBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				String username = nameText.getText();
				String password = passText.getText();
				
				Response response = dba.login(username, password);
				if (response.isCorrect()) {
					boolean manager = dba.isManager(username);
					System.out.println(manager);
					JFrame userFrame = new UserFrame(mainFrame, username, manager);
					nameText.setText("");
					passText.setText("");
					dispose();
					userFrame.show();
				}
				else {
					JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		loginBtn.setBounds(25, 219, 223, 42);
		loginPanel.add(loginBtn);
		
		signBtn = new JButton("Sign Up");
		signBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				JFrame signup = new SignupFrame(mainFrame);
				setVisible(false);
				signup.show();
			}
		});
		signBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		BufferedImage bsImg = null;
		try {
		    bsImg = ImageIO.read(new File("images\\Add-Male-User.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image sImg = bsImg.getScaledInstance(nameImg.getWidth(), nameImg.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon sImgIcon = new ImageIcon(sImg);
		signBtn.setIcon(sImgIcon);
		signBtn.setBounds(279, 219, 223, 42);
		loginPanel.add(signBtn);
	}
	
	public DBAccessInterface getDBA () {
		return dba;
	}
	
	public ICart getCart () {
		return cart;
	}
	
	public ITable getTable () {
		return table;
	}
}
