package frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import main.ITable;

import org.jdesktop.swingx.JXTable;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;

public class Statistics extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cntPane;
	private boolean b1 = false, b2 = false, b3 = false;
	private String[] columns1, columns2, columns3;
	private String[][] data1, data2, data3;
	private JXTable table, table1, table2;

	/**
	 * Create the panel.
	 */
	public Statistics(UserFrame frame) {
		setBorder(null);
		setLayout(null);
		setBounds(15, 15, 900, 809);
		cntPane = frame.getContentPane();
		
		JPanel topFiveCustomerPanel = new JPanel();
		topFiveCustomerPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Top five customers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		topFiveCustomerPanel.setBounds(0, 0, 900, 243);
		add(topFiveCustomerPanel);
		topFiveCustomerPanel.setLayout(null);
		
		JButton s1Btn = new JButton("Show");
		s1Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!b1) {
					b1 = true;
					ITable tab = frame.getDBA().getTopCustomers();
					data1 = tab.getData();
					columns1 = tab.getAttributes();
					table = new JXTable(data1, columns1);
					table.setEditable(false);
					table.setModel(new DefaultTableModel(
						data1, columns1));
					table.setFont(new Font("Tahoma", Font.PLAIN, 18));
					
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table.packAll();
					
					if (table.getPreferredSize().getWidth() < 870) {
						table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					}
					
					JScrollPane scrollPane = new JScrollPane(table);
					scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
					scrollPane.revalidate();
					scrollPane.setBounds(15, 78, 870, 149);
					topFiveCustomerPanel.add(scrollPane);
				}
			}
		});
		s1Btn.setIcon(new ImageIcon("images\\stock_slide-show.png"));
		s1Btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		s1Btn.setBounds(15, 29, 140, 37);
		topFiveCustomerPanel.add(s1Btn);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (b1) {
					MessageFormat header = new MessageFormat("Top five customers");
					MessageFormat footer = new MessageFormat("{1,number,integer}");
					
					try {
						table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
					} catch (PrinterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPrint.setBounds(200, 29, 140, 37);
		topFiveCustomerPanel.add(btnPrint);
		
		JPanel topTenBookPanel = new JPanel();
		topTenBookPanel.setLayout(null);
		topTenBookPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Top ten selling books", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		topTenBookPanel.setBounds(0, 242, 900, 243);
		add(topTenBookPanel);
		topTenBookPanel.setLayout(null);
		
		JButton s2Btn = new JButton("Show");
		s2Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!b2) {
					b2 = true;
					ITable tab = frame.getDBA().getTopBooks();
					data2 = tab.getData();
					columns2 = tab.getAttributes();
					table1 = new JXTable(data2, columns2);
					table1.setEditable(false);
					table1.setModel(new DefaultTableModel(
						data2, columns2));
					table1.setFont(new Font("Tahoma", Font.PLAIN, 18));
					
					table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table1.packAll();
					
					if (table1.getPreferredSize().getWidth() < 870) {
						table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					}
					
					JScrollPane scrollPane_1 = new JScrollPane(table1);
					scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPane_1.setBounds(15, 78, 870, 149);
					topTenBookPanel.add(scrollPane_1);
				}
			}
		});
		s2Btn.setIcon(new ImageIcon("images\\stock_slide-show.png"));
		s2Btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		s2Btn.setBounds(15, 25, 140, 37);
		topTenBookPanel.add(s2Btn);
		
		JButton btnPrint_1 = new JButton("Print");
		btnPrint_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (b2) {
					MessageFormat header = new MessageFormat("Top ten selling books");
					MessageFormat footer = new MessageFormat("{1,number,integer}");
					
					try {
						table1.print(JTable.PrintMode.FIT_WIDTH, header, footer);
					} catch (PrinterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnPrint_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPrint_1.setBounds(208, 25, 140, 37);
		topTenBookPanel.add(btnPrint_1);
		
		JPanel booksPanel = new JPanel();
		booksPanel.setLayout(null);
		booksPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "Total sales for books in previous month", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		booksPanel.setBounds(0, 485, 900, 273);
		add(booksPanel);
		booksPanel.setLayout(null);
		
		JButton s3Btn = new JButton("Show");
		s3Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!b3) {
					b3 = true;
					ITable tab = frame.getDBA().getTotalSales();
					data3 = tab.getData();
					columns3 = tab.getAttributes();
					table2 = new JXTable(data3, columns3);
					table2.setEditable(false);
					table2.setModel(new DefaultTableModel(
						data3, columns3));
					table2.setFont(new Font("Tahoma", Font.PLAIN, 18));
					
					table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table2.packAll();
					
					if (table2.getPreferredSize().getWidth() < 870) {
						table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					}
					
					JScrollPane scrollPane_2 = new JScrollPane(table2);
					scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPane_2.setBounds(15, 82, 870, 149);
					booksPanel.add(scrollPane_2);
				}
			}
		});
		
		
		s3Btn.setIcon(new ImageIcon("images\\stock_slide-show.png"));
		s3Btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		s3Btn.setBounds(15, 29, 140, 37);
		booksPanel.add(s3Btn);
		
		JButton btnPrint_2 = new JButton("Print");
		btnPrint_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (b3) {
					MessageFormat header = new MessageFormat("total sales for books");
					MessageFormat footer = new MessageFormat("{1,number,integer}");
					
					try {
						table2.print(JTable.PrintMode.FIT_WIDTH, header, footer);
					} catch (PrinterException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
			}
		});
		btnPrint_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPrint_2.setBounds(202, 29, 140, 37);
		booksPanel.add(btnPrint_2);
		
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
		Image cImg = cbImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon cImgIcon = new ImageIcon(cImg);
		btnCancel.setIcon(cImgIcon);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCancel.setBounds(15, 762, 140, 38);
		add(btnCancel);
	}
}
