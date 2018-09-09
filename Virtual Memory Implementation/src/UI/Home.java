package UI;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.UIManager;

public class Home {
	Home window;
	private JFrame frame;
	JPanel panel;
	private JPanel panel_1;
	public static ArrayList<JPanel> selectedRows;
	public  ArrayList<myPanel> instructions;
	private int maxValue=0;
	private JTextField textRam;
	private JTextField textFrame;
	//public static JTextField textPage;
	private static  JTextField textPage;
	private JButton btnAnalyse;
	private JLabel lblProgramSize;
	private JTextField textProg;
	private JLabel lblRamSize;
	private JTextField textRSize;
	private JButton btnExecute;
	private JLabel lblStart;
	private JLabel lblInstructionType;
	private JLabel lblDestinationAdress;
	private JLabel lblStartPage;
	private JLabel lblDestinationAdress_1;

	public static int getTextPage(){
		int page= Integer.parseInt(textPage.getText().trim());
		return page;
	}

	public int getRamSize(){
		int  size = Integer.parseInt(textRam.getText());
		return size;
	} 
	public int getFrameSize(){
		int  size = Integer.parseInt(textFrame.getText());
		return size;
	} 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
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
	public Home() {
		window = this;
		initialize();
		selectedRows = new ArrayList<JPanel>();
		instructions=new ArrayList<myPanel>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 51, 51));
		frame.setBounds(100, 100, 755, 469);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(UIManager.getColor("Button.darkShadow"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myPanel r = new myPanel();
				panel_1.add(r);
				if(instructions.size()!=0){

					int s = instructions.get(instructions.size()-1).getStartAddress();

					if(s != 0){

						int val = s;
						val++;	

						r.setStartAddress(Integer.toString(val));
					}
				}
				instructions.add(r);
				panel_1.revalidate();
			}
		});
		btnNewButton.setBounds(10, 130, 89, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("DELETE");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBackground(UIManager.getColor("Button.darkShadow"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(JPanel p:selectedRows){
					panel_1.remove(p);
					panel_1.revalidate();
					instructions.remove(p);

				}
				maxValue = 0;
			}

		});
		btnNewButton_1.setBounds(106, 130, 89, 23);
		frame.getContentPane().add(btnNewButton_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 192, 699, 208);
		frame.getContentPane().add(scrollPane_1);


		panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));


		JLabel lblRAMSize = new JLabel("RAM SIZE");
		lblRAMSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRAMSize.setForeground(Color.WHITE);
		lblRAMSize.setBounds(10, 45, 79, 14);
		frame.getContentPane().add(lblRAMSize);

		JLabel lblFrameSize = new JLabel("FRAME SIZE");
		lblFrameSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFrameSize.setForeground(Color.WHITE);
		lblFrameSize.setBounds(10, 70, 79, 14);
		frame.getContentPane().add(lblFrameSize);

		JLabel lblPageSize = new JLabel("PAGE SIZE");
		lblPageSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPageSize.setForeground(Color.WHITE);
		lblPageSize.setBounds(10, 105, 79, 14);
		frame.getContentPane().add(lblPageSize);

		textRam = new JTextField();
		textRam.setBounds(109, 42, 86, 20);
		frame.getContentPane().add(textRam);
		textRam.setColumns(10);

		textFrame = new JTextField();
		textFrame.setText("");
		textFrame.setBounds(109, 70, 86, 20);
		frame.getContentPane().add(textFrame);
		textFrame.setColumns(10);

		textPage = new JTextField();
		textPage.setText("");
		textPage.setBounds(109, 103, 86, 20);
		frame.getContentPane().add(textPage);
		textPage.setColumns(10);


		btnAnalyse = new JButton("ANALYSE");
		btnAnalyse.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnalyse.setBackground(UIManager.getColor("Button.darkShadow"));
		btnAnalyse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textRSize.setText(textRam.getText());
				for(myPanel p:instructions){
					//String s = p.textStart.getText().trim();
					int s = p.getStartAddress();
					//if(s.compareTo("") != 0){
					if(s!=0){
						//int val = Integer.parseInt(s);
						int val=s;
						if (maxValue < val){
							maxValue = val;
						}
					}	
					//String r = p.textDest.getText().trim();
					int r = p.getDestinationAddress();
					//if(r.compareTo("") != 0){
					if(r!= 0){
						//int val = Integer.parseInt(r);
						int val=r;
						if (maxValue < val){
							maxValue = val;
						}
					}
				}
				textProg.setText(Integer.toString(maxValue));

			}
		});
		btnAnalyse.setBounds(313, 41, 89, 23);
		frame.getContentPane().add(btnAnalyse);

		lblProgramSize = new JLabel("PROGRAM SIZE");
		lblProgramSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProgramSize.setForeground(Color.WHITE);
		lblProgramSize.setBounds(412, 44, 89, 17);
		frame.getContentPane().add(lblProgramSize);

		textProg = new JTextField();
		textProg.setBounds(511, 42, 86, 20);
		frame.getContentPane().add(textProg);
		textProg.setColumns(10);

		lblRamSize = new JLabel("RAM SIZE");
		lblRamSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRamSize.setForeground(Color.WHITE);
		lblRamSize.setBounds(412, 70, 89, 14);
		frame.getContentPane().add(lblRamSize);

		textRSize = new JTextField();
		textRSize.setBounds(511, 67, 86, 20);
		frame.getContentPane().add(textRSize);
		textRSize.setColumns(10);

		btnExecute = new JButton("EXECUTE");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try
				{
					Visual frame = new Visual(window);
					frame.frame.setVisible(true);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}



			}
		});
		btnExecute.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExecute.setBackground(UIManager.getColor("Button.darkShadow"));
		btnExecute.setBounds(511, 113, 89, 23);
		frame.getContentPane().add(btnExecute);

		JLabel lblVirtualMemoryConcept = new JLabel("VIRTUAL MEMORY CONCEPT");
		lblVirtualMemoryConcept.setForeground(Color.WHITE);
		lblVirtualMemoryConcept.setHorizontalAlignment(SwingConstants.CENTER);
		lblVirtualMemoryConcept.setFont(new Font("Cambria", Font.BOLD, 18));
		lblVirtualMemoryConcept.setBounds(145, 11, 330, 20);
		frame.getContentPane().add(lblVirtualMemoryConcept);

		lblStart = new JLabel("START_ADRESS");
		lblStart.setForeground(Color.WHITE);
		lblStart.setHorizontalAlignment(SwingConstants.LEFT);
		lblStart.setBounds(48, 167, 100, 14);
		frame.getContentPane().add(lblStart);

		lblInstructionType = new JLabel("INSTRUCTION_TYPE");
		lblInstructionType.setForeground(Color.WHITE);
		lblInstructionType.setHorizontalAlignment(SwingConstants.LEFT);
		lblInstructionType.setBounds(172, 167, 115, 14);
		frame.getContentPane().add(lblInstructionType);

		lblDestinationAdress = new JLabel("DESTINATION ADRESS");
		lblDestinationAdress.setForeground(Color.WHITE);
		lblDestinationAdress.setHorizontalAlignment(SwingConstants.LEFT);
		lblDestinationAdress.setBounds(290, 167, 126, 14);
		frame.getContentPane().add(lblDestinationAdress);

		lblStartPage = new JLabel("START PAGE");
		lblStartPage.setForeground(Color.WHITE);
		lblStartPage.setHorizontalAlignment(SwingConstants.LEFT);
		lblStartPage.setBounds(443, 167, 79, 14);
		frame.getContentPane().add(lblStartPage);

		lblDestinationAdress_1 = new JLabel("DESTINATION PAGE");
		lblDestinationAdress_1.setForeground(Color.WHITE);
		lblDestinationAdress_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDestinationAdress_1.setBounds(559, 167, 115, 14);
		frame.getContentPane().add(lblDestinationAdress_1);



	}

}
class myPanel extends JPanel{
	//public JTextField textStart;
	private JTextField textStart;
	//public JTextField textDest;
	private JTextField textDest;
	public JTextField textSPage;
	//private JTextField textSPage;
	public JTextField textDPage;
	private JCheckBox checkBox;
	public JComboBox comboBox;

	myPanel panel;


	public int getStartAddress(){
		int sadress= Integer.parseInt(textStart.getText().trim());
		return sadress;
	}
	public void setStartAddress(String s){
		textStart.setText(s);
	}

	public int getDestinationAddress(){
		int dAdress= Integer.parseInt(textDest.getText().trim());
		return dAdress;
	}
	public void setDestinationAddress(String r){
		textDest.setText(r);
	}

	/*public int getStartPage(){
	    int sPage= Integer.parseInt(textSPage.getText().trim());
		return sPage;
	}
	public void setStartPage(String sp){
	    textSPage.setText(sp);
	}*/

	myPanel(){
		super();
		panel=this;
		//1024instructionIndex++;
		checkBox = new JCheckBox();
		checkBox.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				int a = e.getStateChange();
				if(a == ItemEvent.SELECTED){
					Home.selectedRows.add(panel);
				}
				if(a==ItemEvent.DESELECTED){
					Home.selectedRows.remove(panel);
				}

			}
		});

		panel.add(checkBox);

		textStart = new JTextField();



		textStart.addFocusListener(new FocusListener(){


			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}


			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub



			}


		});
		panel.add(textStart);
		textStart.setColumns(10);
		panel.add(Box.createHorizontalStrut(10));

		comboBox = new JComboBox();
		comboBox.addItem("Read/Write");
		comboBox.addItem("GoTo");
		panel.add(comboBox);
		panel.add(Box.createHorizontalStrut(10));


		textDest = new JTextField();
		textDest.addFocusListener(new FocusListener(){


			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}


			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}



		});

		panel.add(textDest);
		textDest.setColumns(10);
		panel.add(Box.createHorizontalStrut(10));


		textSPage = new JTextField();
		panel.add(textSPage);
		textSPage.setColumns(10);
		panel.add(Box.createHorizontalStrut(10));

		textSPage.addFocusListener(new FocusListener(){


			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

				//String s = Home.textPage.getText().trim();
				int s= Home.getTextPage();
				String r = textStart.getText().trim();
				//if(s.compareTo("")!=0 && r.compareTo("")!=0){ 
				if(s !=0 && r.compareTo("")!=0){
					//int page=Integer.parseInt(s);
					int page = s ;
					int start= Integer.parseInt(r);

					int result=start/page;
					textSPage.setText(Integer.toString(result));
				}


			}

			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

		});



		textDPage = new JTextField();
		panel.add(textDPage);
		textDPage.setColumns(10);
		textDPage.addFocusListener(new FocusListener(){


			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				/*String s = Home.textPage.getText().trim();
				String r = textDest.getText().trim();
				if(s.compareTo("") !=0 && r.compareTo("")!=0){
					int page = Integer.parseInt(s) ;
					int start= Integer.parseInt(r);

					int result=start/page;
					textDPage.setText(Integer.toString(result));
				}*/

				int s= Home.getTextPage();
				String r = textDest.getText().trim();
				//if(s.compareTo("")!=0 && r.compareTo("")!=0){ 
				if(s !=0 && r.compareTo("")!=0){
					//int page=Integer.parseInt(s);
					int page = s ;
					int start= Integer.parseInt(r);

					int result=start/page;
					textDPage.setText(Integer.toString(result));
				}
			}


			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

}
