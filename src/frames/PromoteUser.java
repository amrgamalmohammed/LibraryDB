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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;

import main.Response;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PromoteUser extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nameText;
	private Container cntPane;

	/**
	 * Create the panel.
	 */
	public PromoteUser(UserFrame frame) {
		setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0)), "Promote customer to have manager credentials", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(null);
		cntPane = frame.getContentPane();
		JPanel panel = this;
		panel.setBounds(15, 15, 530, 300);
		
		JLabel nameLabel = new JLabel("Username");
		nameLabel.setForeground(Color.BLUE);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		nameLabel.setEnabled(true);
		nameLabel.setBounds(29, 58, 110, 37);
		add(nameLabel);
		
		nameText = new JTextField();
		nameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nameText.setBounds(259, 59, 211, 37);
		add(nameText);
		nameText.setColumns(10);
		
		JButton confirmBtn = new JButton("Confirm");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				Response response = frame.getDBA().promoteUser(name);
				if (response.isCorrect()) {
					cntPane.removeAll();
					cntPane.revalidate();
					cntPane.repaint();
					JOptionPane.showMessageDialog(null, "Operation completed successfully", "INFO", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, response.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		BufferedImage abImg = null;
		try {
		    abImg = ImageIO.read(new File("images\\add.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image aImg = abImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon aImgIcon = new ImageIcon(aImg);
		confirmBtn.setIcon(aImgIcon);
		confirmBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		confirmBtn.setBounds(29, 240, 172, 44);
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
		Image cImg = cbImg.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		ImageIcon cImgIcon = new ImageIcon(cImg);
		cancelBtn.setIcon(cImgIcon);
		cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		cancelBtn.setBounds(237, 240, 162, 44);
		add(cancelBtn);

	}
}
