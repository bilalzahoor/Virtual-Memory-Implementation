package UI;
import java.awt.EventQueue;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JFileChooser;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.UIManager;

public class Home {
	Home window;
	public JFrame frame;
	JPanel panel;
	private JPanel panel_1;
	public static ArrayList<JPanel> selectedRows;
	public  ArrayList<myPanel> instructions;
	private int maxValue=0;
	private JTextField textRam;
	public static JTextField textFrame;
	private JButton btnAnalyse;
	private JLabel lblProgramSize;
	private JLabel lblRamSize;
	private JButton btnExecute;
	private JLabel lblStart;
	private JLabel lblInstructionType;
	private JLabel lblDestinationAdress;
	private JLabel lblStartPage;
	private JLabel lblDestinationAdress_1;
	private JButton btnSave;
	private PrintStream f=null;
	private BufferedReader readLine=null;
	private Introduction w;
	private JButton btnLoadFile;
	JButton addInstButton;
	JLabel lblRam;
	JLabel lblProgSize;
	private JTextField textField;
	private JTextField textField_1;
	private boolean LoadFile=false;
	
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
	 * @param window2 
	 * @wbp.parser.entryPoint
	 */
	public Home(Introduction window2) {
		w=window2;
		window = this;
		initialize();
		selectedRows = new ArrayList<JPanel>();
		instructions=new ArrayList<myPanel>();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public Home() {
		// TODO Auto-generated constructor stub
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 51, 51));
		frame.setBounds(100, 100, 954, 668);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		addInstButton = new JButton("Add Instruction");
		addInstButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		addInstButton.setBackground(UIManager.getColor("Button.darkShadow"));
		addInstButton.addActionListener(new ActionListener() {
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
		addInstButton.setBounds(167, 188, 138, 29);
		frame.getContentPane().add(addInstButton);

		JButton btnNewButton_1 = new JButton("Delete Instruction");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
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
		btnNewButton_1.setBounds(315, 189, 159, 28);
		frame.getContentPane().add(btnNewButton_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(167, 253, 699, 279);
		frame.getContentPane().add(scrollPane_1);


		panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));




		JLabel lblRAMSize = new JLabel(" RAM Size (Bytes)");
		lblRAMSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRAMSize.setForeground(Color.WHITE);
		lblRAMSize.setBounds(157, 76, 152, 14);
		frame.getContentPane().add(lblRAMSize);

		JLabel lblFrameSize = new JLabel("Frame/Page Size (Bytes)");
		lblFrameSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFrameSize.setForeground(Color.WHITE);
		lblFrameSize.setBounds(157, 107, 152, 14);
		frame.getContentPane().add(lblFrameSize);

		textRam = new JTextField();
		textRam.setBounds(319, 73, 86, 20);
		frame.getContentPane().add(textRam);
		textRam.setColumns(10);

		textFrame = new JTextField();
		textFrame.setText("");
		textFrame.setBounds(319, 104, 86, 20);
		frame.getContentPane().add(textFrame);
		textFrame.setColumns(10);


		btnAnalyse = new JButton("Analyse");
		btnAnalyse.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAnalyse.setBackground(UIManager.getColor("Button.darkShadow"));
		btnAnalyse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblRam.setText(textRam.getText());
				for(myPanel p:instructions){
					int s = p.getStartAddress();
					if(s!=0){
						int val=s;
						if (maxValue < val){
							maxValue = val;
						}
					}	
					int r = p.getDestinationAddress();
					if(r!= 0){
						int val=r;
						if (maxValue < val){
							maxValue = val;
						}
					}
				}
				lblProgSize.setText(Integer.toString(maxValue));

			}
		});
		btnAnalyse.setBounds(456, 68, 114, 28);
		frame.getContentPane().add(btnAnalyse);

		lblProgramSize = new JLabel("Pragram Size (Bytes)");
		lblProgramSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProgramSize.setForeground(Color.WHITE);
		lblProgramSize.setBounds(592, 69, 127, 17);
		frame.getContentPane().add(lblProgramSize);

		lblRamSize = new JLabel("RAM Size (Bytes)");
		lblRamSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRamSize.setForeground(Color.WHITE);
		lblRamSize.setBounds(592, 104, 127, 14);
		frame.getContentPane().add(lblRamSize);

		btnExecute = new JButton("Execute");
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
		btnExecute.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExecute.setBackground(UIManager.getColor("Button.darkShadow"));
		btnExecute.setBounds(560, 543, 114, 28);
		frame.getContentPane().add(btnExecute);

		JLabel lblVirtualMemoryConcept = new JLabel("Virtual Memory(Virtualization)");
		lblVirtualMemoryConcept.setForeground(new Color(253, 245, 230));
		lblVirtualMemoryConcept.setHorizontalAlignment(SwingConstants.CENTER);
		lblVirtualMemoryConcept.setFont(new Font("Calibri", Font.BOLD, 28));
		lblVirtualMemoryConcept.setBounds(145, 11, 529, 45);
		frame.getContentPane().add(lblVirtualMemoryConcept);

		lblStart = new JLabel("Start Adress");
		lblStart.setForeground(Color.WHITE);
		lblStart.setHorizontalAlignment(SwingConstants.LEFT);
		lblStart.setBounds(219, 228, 100, 14);
		frame.getContentPane().add(lblStart);

		lblInstructionType = new JLabel("Instruction Type");
		lblInstructionType.setForeground(Color.WHITE);
		lblInstructionType.setHorizontalAlignment(SwingConstants.LEFT);
		lblInstructionType.setBounds(319, 228, 115, 14);
		frame.getContentPane().add(lblInstructionType);

		lblDestinationAdress = new JLabel("Destination Adress");
		lblDestinationAdress.setForeground(Color.WHITE);
		lblDestinationAdress.setHorizontalAlignment(SwingConstants.LEFT);
		lblDestinationAdress.setBounds(456, 228, 126, 14);
		frame.getContentPane().add(lblDestinationAdress);

		lblStartPage = new JLabel("Start Page");
		lblStartPage.setForeground(Color.WHITE);
		lblStartPage.setHorizontalAlignment(SwingConstants.LEFT);
		lblStartPage.setBounds(604, 228, 79, 14);
		frame.getContentPane().add(lblStartPage);

		lblDestinationAdress_1 = new JLabel("Destination Page");
		lblDestinationAdress_1.setForeground(Color.WHITE);
		lblDestinationAdress_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDestinationAdress_1.setBounds(729, 228, 115, 14);
		frame.getContentPane().add(lblDestinationAdress_1);

		btnSave = new JButton("Save");
		btnSave.setBackground(UIManager.getColor("Button.darkShadow"));
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 12));

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(null)== JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					// save to file
					try{
						f = new PrintStream(file);
						f.println(textRam.getText()+" "+textFrame.getText());
						for(int i=0;i<instructions.size();i++){
							String line = Integer.toString(instructions.get(i).getStartAddress());
							if(instructions.get(i).comboBox.getSelectedItem().toString().equals("Read/Write"))
								line = line +" "+ "0";
							else
								line = line+ " " + "1";
							line = line+ " " + Integer.toString(instructions.get(i).getDestinationAddress());
							line =line + " " + instructions.get(i).textSPage.getText();
							line = line + " " + instructions.get(i).textDPage.getText();

							f.println(line);		
						}
						f.close();
					}catch(IOException ex){}

				}
			}
		});
		btnSave.setBounds(434, 543, 114, 28);
		frame.getContentPane().add(btnSave);

		btnLoadFile = new JButton(" Load File");
		btnLoadFile.setBackground(UIManager.getColor("Button.darkShadow"));
		btnLoadFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {

						readLine = new BufferedReader(new FileReader(file));
						String line;
						instructions.clear();
						while( (line = readLine.readLine() ) != null){
							String[] words= line.split(" ");
							if(words.length == 2){
								textRam.setText(words[0]);
								textFrame.setText(words[1]);
							}
							else{
								myPanel r = new myPanel();
								panel_1.add(r);
							
								r.setStartAddress(words[0]);
								if(words[1].equals("0"))
									r.comboBox.setSelectedItem("Read/Write");
								else
									r.comboBox.setSelectedItem("GOTO");

								r.setDestinationAddress(words[2]);
								r.textSPage.setText(words[3]);
								r.textDPage.setText(words[4]);
								instructions.add(r);
								panel_1.revalidate();


							}


						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			}
		});
		btnLoadFile.setBounds(167, 146, 114, 28);
		frame.getContentPane().add(btnLoadFile);

		lblProgSize = new JLabel("");
		lblProgSize.setForeground(Color.WHITE);
		lblProgSize.setBounds(729, 67, 86, 14);
		frame.getContentPane().add(lblProgSize);

		lblRam = new JLabel("");
		lblRam.setForeground(Color.WHITE);
		lblRam.setBounds(726, 107, 89, 14);
		frame.getContentPane().add(lblRam);
	}
}
class myPanel extends JPanel{

	private JTextField textStart;
	private JTextField textDest;
	public JTextField textSPage;
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
				int  s=Integer.parseInt(Home.textFrame.getText().trim());
				String r = textStart.getText().trim();
				
				if(s !=0 && r.compareTo("")!=0){
				
					int page = s ;
					int start= Integer.parseInt(r);

					int result=start/page;
					textSPage.setText(Integer.toString(result));


	}
			}


			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				int  s=Integer.parseInt(Home.textFrame.getText().trim());
				String r = textDest.getText().trim();
				
				if(s !=0 && r.compareTo("")!=0){
					
					int page = s ;
					int start= Integer.parseInt(r);

					int result=start/page;
					textDPage.setText(Integer.toString(result));
			
				}
			}



		});

		panel.add(textDest);
		textDest.setColumns(10);
		panel.add(Box.createHorizontalStrut(10));

		
		textSPage = new JTextField();
		textSPage.setEditable(false);
		textSPage.setColumns(10);
		panel.add(textSPage);
		panel.add(Box.createHorizontalStrut(10));

		textSPage.addFocusListener(new FocusListener(){

					public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub


			
					}
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

		});



		textDPage = new JTextField();
		textDPage.setEditable(false);
		textDPage.setColumns(10);
		panel.add(textDPage);
		textDPage.addFocusListener(new FocusListener(){


			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
			}


			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

}

