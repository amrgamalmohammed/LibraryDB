package frames;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.DBAccessInterface;
import main.ICart;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UserFrame frame;
	private DBAccessInterface dba;
	private ICart cart;
	private String name;

	/**
	 * Create the frame.
	 */
	public UserFrame(MainFrame mFrame, String username, boolean manager) {
		frame = this;
		this.dba = mFrame.getDBA();
		this.cart = mFrame.getCart();
		this.name = username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JMenuBar bar = new JMenuBar();
		bar.setForeground(Color.BLACK);
		
		JMenu cstMenu = new JMenu("Customer");
		cstMenu.setFont(new Font("Tahoma", Font.PLAIN, 22));
		cstMenu.setForeground(Color.DARK_GRAY);
		
		JMenuItem updateInfoItem = new JMenuItem("Update Account");
		updateInfoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String[] attributes = dba.getUserData(name);
				
				contentPane.removeAll();
				JPanel panel = new UpdateAccount(frame, attributes, name);
				setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
	    		panel.setBackground(Color.WHITE);
	    		contentPane.add(panel);
	    		contentPane.revalidate();
	    		contentPane.repaint();
			}
		});
		updateInfoItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		updateInfoItem.setForeground(Color.DARK_GRAY);
		updateInfoItem.setSize(80, 80);
		cstMenu.add(updateInfoItem);
		cstMenu.addSeparator();
		
		JMenuItem searchBookItem = new JMenuItem("Search Book");
		searchBookItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.removeAll();
				JPanel panel = new SearchBook(frame, manager);
				setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
	    		panel.setBackground(Color.WHITE);
	    		contentPane.add(panel);
	    		contentPane.revalidate();
	    		contentPane.repaint();
			}
		});
		searchBookItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		searchBookItem.setForeground(Color.DARK_GRAY);
		searchBookItem.setSize(80, 80);
	    cstMenu.add(searchBookItem);
	    cstMenu.addSeparator();
	    
	    JMenuItem cartItem = new JMenuItem("Shoping Cart");
	    cartItem.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		contentPane.removeAll();
	    		JPanel panel = new ShopingCart(frame);
	    		setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
	    		panel.setBackground(Color.WHITE);
	    		contentPane.add(panel);
	    		contentPane.revalidate();
	    		contentPane.repaint();
	    	}
	    });
	    cartItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
	    cartItem.setForeground(Color.DARK_GRAY);
	    cartItem.setSize(80, 80);
	    cstMenu.add(cartItem);
	    cstMenu.addSeparator();
	    
	    JMenuItem checkOutItem = new JMenuItem("Check Out");
	    checkOutItem.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		if (!cart.getBooks().isEmpty()) {
	    			contentPane.removeAll();
		    		JPanel panel = new CheckOut(frame, cart, name);
		    		setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
	    		}
	    		else {
	    			JOptionPane.showMessageDialog(null, "Shopping Cart is empty !!", "Warning", JOptionPane.WARNING_MESSAGE);
	    		}
	    	}
	    });
	    checkOutItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
	    checkOutItem.setForeground(Color.DARK_GRAY);
	    checkOutItem.setSize(80, 80);
	    cstMenu.add(checkOutItem);
	    cstMenu.addSeparator();
	    
	    JMenuItem logOutItem = new JMenuItem("Log Out");
	    logOutItem.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		if (mFrame.getCart().getBooks().isEmpty()) {
	    			dispose();
	    			mFrame.setVisible(true);
	    		}
	    		else {
	    			int answer = showWarningMessage();
	                
	                switch (answer) {
	                    case JOptionPane.YES_OPTION:
	                    	mFrame.getCart().clearCart();
	                    	dispose();
	            			mFrame.setVisible(true);
	                        break;
	                         
	                    case JOptionPane.NO_OPTION:
	                        break;
	                }
	    			JOptionPane.showMessageDialog(null, "Shopping Cart is not empty !!", "Warning", JOptionPane.WARNING_MESSAGE);
	    		}
	    	}
	    });
	    logOutItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
	    logOutItem.setForeground(Color.DARK_GRAY);
	    logOutItem.setSize(80, 80);
	    cstMenu.add(logOutItem);
	    
	    bar.add(cstMenu);
	    
	    if (manager) {
	    	JMenu mgrMenu = new JMenu("Manager");
		    mgrMenu.setFont(new Font("Tahoma", Font.PLAIN, 22));
		    mgrMenu.setForeground(Color.DARK_GRAY);
			
			JMenuItem addBookItem = new JMenuItem("Add New Book");
			addBookItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
			addBookItem.setForeground(Color.DARK_GRAY);
			addBookItem.setSize(80, 80);
			addBookItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new AddBook(frame);
		    		panel.setBackground(Color.WHITE);
		    		setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
			mgrMenu.add(addBookItem);
			mgrMenu.addSeparator();
			
			JMenuItem publisherItem = new JMenuItem("Add Publisher");
			publisherItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
			publisherItem.setForeground(Color.DARK_GRAY);
			publisherItem.setSize(80, 80);
			publisherItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new AddPublisher(frame);
					setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
			mgrMenu.add(publisherItem);
			mgrMenu.addSeparator();
			
			JMenuItem categoryItem = new JMenuItem("Add Category");
			categoryItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
			categoryItem.setForeground(Color.DARK_GRAY);
			categoryItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new Category(frame);
					setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
			categoryItem.setSize(80, 80);
			mgrMenu.add(categoryItem);
			mgrMenu.addSeparator();
			
			JMenuItem modifyBookItem = new JMenuItem("Modify Book");
			modifyBookItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new SearchBook(frame, manager);
					setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
			modifyBookItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
			modifyBookItem.setForeground(Color.DARK_GRAY);
			modifyBookItem.setSize(80, 80);
			mgrMenu.add(modifyBookItem);
			mgrMenu.addSeparator();
		    
		    JMenuItem orderItem = new JMenuItem("Place Order For Book");
		    orderItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		    orderItem.setForeground(Color.DARK_GRAY);
		    orderItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new SearchBook(frame, manager);
					setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
		    orderItem.setSize(80, 80);
		    mgrMenu.add(orderItem);
		    mgrMenu.addSeparator();
		    
		    JMenuItem showOrderItem = new JMenuItem("Show Orders");
		    showOrderItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		    showOrderItem.setForeground(Color.DARK_GRAY);
		    showOrderItem.setSize(80, 80);
		    showOrderItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new Orders(frame);
					setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
		    mgrMenu.add(showOrderItem);
		    mgrMenu.addSeparator();
		    
		    JMenuItem confirmOrderItem = new JMenuItem("Show Confirmed Orders");
		    confirmOrderItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		    confirmOrderItem.setForeground(Color.DARK_GRAY);
		    confirmOrderItem.setSize(80, 80);
		    confirmOrderItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new ConfirmedOrders(frame);
					setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
		    mgrMenu.add(confirmOrderItem);
		    mgrMenu.addSeparator();
		    
		    JMenuItem promoteItem = new JMenuItem("Promote Customer");
		    promoteItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new PromoteUser(frame);
					setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
		    promoteItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		    promoteItem.setForeground(Color.DARK_GRAY);
		    promoteItem.setSize(80, 80);
		    mgrMenu.add(promoteItem);
		    mgrMenu.addSeparator();
		    
		    JMenuItem reportItem = new JMenuItem("View Sales Report");
		    reportItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		    reportItem.setForeground(Color.DARK_GRAY);
		    reportItem.setSize(80, 80);
		    reportItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
					JPanel panel = new Statistics(frame);
					setBounds(100, 100, panel.getWidth() + 50, panel.getHeight() + 120);
		    		panel.setBackground(Color.WHITE);
		    		contentPane.add(panel);
		    		contentPane.revalidate();
		    		contentPane.repaint();
				}
			});
		    mgrMenu.add(reportItem);
		    
		    bar.add(mgrMenu);
	    }
	    
	    setJMenuBar(bar);
	}
	
	public DBAccessInterface getDBA () {
		return this.dba;
	}
	
	public ICart getCart () {
		return this.cart;
	}
	
	private int showWarningMessage() {
        String[] buttonLabels = new String[] {"Yes", "No"};
        String defaultOption = buttonLabels[0];
        Icon icon = null;
         
        return JOptionPane.showOptionDialog(frame,"Your Cart is not empty !!\n" +
                "Are you sure you want to log out?","Log Out",
                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,
                icon, buttonLabels,defaultOption);    
    }
	
	public void setUserName (String name) {
		this.name = name;
	}
}
