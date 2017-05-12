package frames;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import main.ICart;
import main.Response;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingConstants;

public class CheckOut extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField card1Text;
	private JTextField yearText;
	private Container cntPane;
	private JTextField monText;
	private JTextField dayText;
	private JTextField card2Text;
	private JTextField card3Text;
	private JTextField card4Text;

	/**
	 * Create the panel.
	 */
	public CheckOut(UserFrame frame, ICart cart, String username) {
		cntPane = frame.getContentPane();
		setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0)), "Check Out", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(null);
		setBounds(15, 15, 629, 300);
		
		JLabel cardLabel = new JLabel("Credit Card Number");
		cardLabel.setForeground(Color.BLUE);
		cardLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		cardLabel.setBounds(23, 69, 174, 37);
		add(cardLabel);
		
		card1Text = new JTextField();
		card1Text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		card1Text.setBounds(210, 67, 87, 37);
		add(card1Text);
		card1Text.setColumns(10);
		
		JLabel dateLabel = new JLabel("Expiry Date");
		dateLabel.setForeground(Color.BLUE);
		dateLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		dateLabel.setBounds(35, 170, 123, 37);
		add(dateLabel);
		
		yearText = new JTextField();
		yearText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		yearText.setColumns(10);
		yearText.setBounds(211, 168, 68, 37);
		add(yearText);
		
		JButton confirmBtn = new JButton("Confirm");
		BufferedImage abImg = null;
		try {
		    abImg = ImageIO.read(new File("images\\sign-check.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image aImg = abImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon aImgIcon = new ImageIcon(aImg);
		confirmBtn.setIcon(aImgIcon);
		confirmBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] cardNo = {
						card1Text.getText(), card2Text.getText(),
						card3Text.getText(), card4Text.getText()
				};
				
				String year = yearText.getText();
				String month = monText.getText();
				String day = dayText.getText();
				Response response = frame.getDBA().checkOut(cart, username, cardNo, year, month, day);
				if (response.isCorrect()) {
					cart.removeItems();
					cntPane.removeAll();
					cntPane.revalidate();
					cntPane.repaint();
					JOptionPane.showMessageDialog(null, "Operation completed successfully", "INFO", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		confirmBtn.setBounds(118, 238, 162, 46);
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
		cancelBtn.setBounds(365, 238, 165, 46);
		add(cancelBtn);
		
		monText = new JTextField();
		monText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		monText.setColumns(10);
		monText.setBounds(294, 168, 68, 37);
		add(monText);
		
		dayText = new JTextField();
		dayText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		dayText.setColumns(10);
		dayText.setBounds(377, 168, 68, 37);
		add(dayText);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setBounds(210, 132, 69, 20);
		add(lblYear);
		
		JLabel lblMonth = new JLabel("Month");
		lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonth.setBounds(293, 132, 69, 20);
		add(lblMonth);
		
		JLabel lblDay = new JLabel("Day");
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setBounds(376, 132, 69, 20);
		add(lblDay);
		
		card2Text = new JTextField();
		card2Text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		card2Text.setColumns(10);
		card2Text.setBounds(312, 67, 87, 37);
		add(card2Text);
		
		card3Text = new JTextField();
		card3Text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		card3Text.setColumns(10);
		card3Text.setBounds(414, 67, 87, 37);
		add(card3Text);
		
		card4Text = new JTextField();
		card4Text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		card4Text.setColumns(10);
		card4Text.setBounds(516, 67, 87, 37);
		add(card4Text);

	}
}
