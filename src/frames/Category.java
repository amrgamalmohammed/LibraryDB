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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import main.Response;

public class Category extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cntPane;
	private JTextField nameText;

	/**
	 * Create the panel.
	 */
	public Category(UserFrame frame) {
		setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), " Add Category", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(null);
		JPanel panel = this;
		cntPane = frame.getContentPane();
		panel.setBounds(15, 15, 450, 310);
		
		JLabel nameLabel = new JLabel("Categoty Name");
		nameLabel.setForeground(Color.BLUE);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		nameLabel.setBounds(26, 70, 145, 37);
		add(nameLabel);
		
		nameText = new JTextField();
		nameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nameText.setBounds(186, 68, 233, 37);
		panel.add(nameText);
		nameText.setColumns(10);
		
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
				int id = frame.getDBA().getCategories().length;
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "please complete required data ", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					Response response = frame.getDBA().addCategory(name, String.valueOf(id + 1));
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
