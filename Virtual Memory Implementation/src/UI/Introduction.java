//package UI;
package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;

//import com.jgoodies.forms.factories.DefaultComponentFactory;


import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.UIManager;

public class Introduction {
	Introduction window;

	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Introduction window = new Introduction();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Introduction() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 51, 51));
		frame.setBounds(100, 100, 1187, 844);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	/*	JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Introduction To Virtual Memory");
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesTitle.setForeground(new Color(253, 245, 230));
		lblNewJgoodiesTitle.setFont(new Font("Calibri", Font.BOLD, 28));
		lblNewJgoodiesTitle.setBounds(129, 21, 645, 73);
		frame.getContentPane().add(lblNewJgoodiesTitle);*/
		
		
		JLabel os = new JLabel("<html>An operating system (OS) is system software that manages computer hardware and software resources<br>It provides common services for computer programs.</html>");
		os.setForeground(Color.WHITE);
		os.setFont(new Font("Calibri", Font.PLAIN, 18));
		os.setBounds(257, 89, 900, 55);
		frame.getContentPane().add(os);
		
		
		JLabel RAM = new JLabel("<html>Alternatively referred to as main memory, primary memory, or system memory, Random Access Memory (RAM) is a hardware device that allows information to be stored and retrieved on a computer.<br> RAM is a volatile memory and requires power to keep the data accessible. <br>If the computer is turned off, all data contained in RAM is lost.</html>");
		RAM.setFont(new Font("Calibri", Font.PLAIN, 18));
		RAM.setForeground(Color.WHITE);
		RAM.setBounds(257, 157, 900, 92);
		frame.getContentPane().add(RAM);
		
	
		JLabel virtual = new JLabel("<html>Virtual memory is a memory management capability of an OS that uses hardware and software to allow a computer to compensate for physical memory shortages by temporarily transferring data from random access memory (RAM) to disk storage. <br></html>");
		virtual.setFont(new Font("Calibri", Font.PLAIN, 18));
		virtual.setForeground(Color.WHITE);
		virtual.setBounds(257, 260, 900, 84);
		frame.getContentPane().add(virtual);
		
		JLabel lblNewLabel = new JLabel("Operating System:");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel.setForeground(new Color(253, 245, 230));
		lblNewLabel.setBounds(98, 89, 149, 51);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("RAM:");
		lblNewLabel_1.setForeground(new Color(253, 245, 230));
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_1.setBounds(98, 157, 151, 92);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Virtual Memory:");
		lblNewLabel_2.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_2.setForeground(new Color(253, 245, 230));
		lblNewLabel_2.setBounds(98, 260, 149, 73);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("<html>Computers have a finite amount of RAM so memory can run out, especially when multiple programs run at the same time. A system using virtual memory can load larger programs or multiple programs running at the same time, allowing each one to operate as if it has infinite memory and without having to purchase more RAM.</html>");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(21, 386,1059 , 85);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("<html>Each page is stored on a disk and when the page is needed, the OS copies it from the disk to main memory and translates the virtual addresses into real addresses.</html>");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(98, 562, 1063, 55);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("<html>A page, memory page, or virtual page is a fixed-length contiguous block of virtual memory, described by a single entry in the page table. It is the smallest unit of data for memory management in a virtual memory operating system.<br>Similarly, a page frame is the smallest fixed-length contiguous block of physical memory into which memory pages are mapped by the operating system.</html>");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblNewLabel_5.setBounds(257, 451, 900, 89);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Pages and Frames:");
		lblNewLabel_6.setForeground(new Color(253, 245, 230));
		lblNewLabel_6.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_6.setBounds(98, 451, 149, 46);
		frame.getContentPane().add(lblNewLabel_6);
		
		JButton btnContinue = new JButton("Continue...");
		btnContinue.setBackground(UIManager.getColor("Button.darkShadow"));
		btnContinue.setFont(new Font("Calibri", Font.BOLD, 18));
		btnContinue.setBounds(461, 628, 134, 34);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try
				{
					Home frame=new Home(window);
					frame.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.frame.setVisible(true);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}



			}
		});
		frame.getContentPane().add(btnContinue);
		
		JLabel lblNewLabel_7 = new JLabel("VIRTUAL MEMORY");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setBounds(257, 11, 408, 48);
		frame.getContentPane().add(lblNewLabel_7);
	}
}
