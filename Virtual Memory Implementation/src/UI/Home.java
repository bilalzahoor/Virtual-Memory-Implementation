package UI;
import java.awt.EventQueue;
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
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.SystemColor;

public class Home {
	Home window;
	JFrame frame;
	JPanel panel;
	JPanel panel_1;
	public static ArrayList<JPanel> selectedRows;
	public  ArrayList<MyPanel> instructions;
	private int maxValue=0;
	private JTextField textRam;
	public static JTextField textFrame;
	public static JButton btnAnalyse;
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
	private JButton btnLoadFile;
	JButton addInstButton;
	JLabel lblRam;
	JLabel lblProgSize;
	private JLabel lblDisplay;



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
					window.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
	 */
	public Home() {
		window = this;
		initialize();
		selectedRows = new ArrayList<JPanel>();
		instructions=new ArrayList<MyPanel>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 51, 51));
		frame.setBounds(100, 100, 1017, 794);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		addInstButton = new JButton("Add Instruction");
		addInstButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		addInstButton.setBackground(UIManager.getColor("Button.darkShadow"));
		addInstButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textRam.getText().equals("") || textFrame.getText().equals("")){
					lblDisplay.setText("Ram size and Frame size must not be empty");
					lblDisplay.setVisible(true);
				}
				else{
					Home.btnAnalyse.setEnabled(false);
					lblDisplay.setText("Program address space crosses RAM address space!");
					lblDisplay.setVisible(false);
					textRam.setEnabled(false);
					textFrame.setEnabled(false);
					MyPanel r = new MyPanel(window);
					panel_1.add(r);
					if(instructions.size()!=0){
	
						int s = instructions.get(instructions.size()-1).getStartAddress();
	
						if(s >= 0){
	
							int val = s;
							val++;	
	
							r.setStartAddress(Integer.toString(val));
						}
					}
					instructions.add(r);
					panel_1.revalidate();
				}
			}
		});
		addInstButton.setBounds(98, 214, 152, 29);
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
		btnNewButton_1.setBounds(98, 254, 152, 28);
		frame.getContentPane().add(btnNewButton_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(262, 218, 698, 348);
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
		lblRAMSize.setBounds(263, 70, 152, 14);
		frame.getContentPane().add(lblRAMSize);

		JLabel lblFrameSize = new JLabel("Frame/Page Size (Bytes)");
		lblFrameSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFrameSize.setForeground(Color.WHITE);
		lblFrameSize.setBounds(263, 107, 152, 14);
		frame.getContentPane().add(lblFrameSize);

		textRam = new JTextField();
		textRam.setBounds(441, 67, 86, 20);
		frame.getContentPane().add(textRam);
		textRam.setColumns(10);

		textFrame = new JTextField();
		textFrame.setText("");
		textFrame.setBounds(441, 104, 86, 20);
		frame.getContentPane().add(textFrame);
		textFrame.setColumns(10);


		btnAnalyse = new JButton("Analyse Program");
		btnAnalyse.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAnalyse.setBackground(UIManager.getColor("Button.darkShadow"));
		btnAnalyse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnExecute.setEnabled(true);
				lblProgramSize.setVisible(true);
				lblRamSize.setVisible(true);
				lblRam.setText(textRam.getText());
				for(MyPanel p:instructions){              
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
				int pr=Integer.parseInt(lblProgSize.getText());
				int ra=Integer.parseInt(lblRam.getText());
				if(pr>ra){                                 
					lblDisplay.setVisible(true);
				}
			}
		});
		btnAnalyse.setBounds(397, 592, 152, 28);
		frame.getContentPane().add(btnAnalyse);

		lblProgramSize = new JLabel("Program Size (Bytes)");
		lblProgramSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProgramSize.setForeground(Color.WHITE);
		lblProgramSize.setBounds(563, 592, 127, 17);
		lblProgramSize.setVisible(false);
		frame.getContentPane().add(lblProgramSize);

		lblRamSize = new JLabel("RAM Size (Bytes)");
		lblRamSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRamSize.setForeground(Color.WHITE);
		lblRamSize.setBounds(563, 620, 127, 14);
		lblRamSize.setVisible(false);
		frame.getContentPane().add(lblRamSize);

		btnExecute = new JButton("Execute & Visualize");
		btnExecute.setForeground(Color.WHITE);
		btnExecute.setEnabled(false);
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try
				{
					Visual frame = new Visual(window);
					frame.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.frame.setVisible(true);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}



			}
		});
		btnExecute.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExecute.setBackground(SystemColor.textHighlight);
		btnExecute.setBounds(397, 661, 152, 28);
		frame.getContentPane().add(btnExecute);

		lblStart = new JLabel("Instruction Address");
		lblStart.setForeground(Color.WHITE);
		lblStart.setHorizontalAlignment(SwingConstants.LEFT);
		lblStart.setBounds(303, 193, 112, 14);
		frame.getContentPane().add(lblStart);

		lblInstructionType = new JLabel("Instruction Type");
		lblInstructionType.setForeground(Color.WHITE);
		lblInstructionType.setHorizontalAlignment(SwingConstants.LEFT);
		lblInstructionType.setBounds(434, 193, 100, 14);
		frame.getContentPane().add(lblInstructionType);

		lblDestinationAdress = new JLabel("Accessed Address");
		lblDestinationAdress.setForeground(Color.WHITE);
		lblDestinationAdress.setHorizontalAlignment(SwingConstants.LEFT);
		lblDestinationAdress.setBounds(544, 193, 126, 14);
		frame.getContentPane().add(lblDestinationAdress);

		lblStartPage = new JLabel("Instruction Page");
		lblStartPage.setForeground(Color.WHITE);
		lblStartPage.setHorizontalAlignment(SwingConstants.LEFT);
		lblStartPage.setBounds(680, 193, 101, 14);
		frame.getContentPane().add(lblStartPage);

		lblDestinationAdress_1 = new JLabel("Accessed Page");
		lblDestinationAdress_1.setForeground(Color.WHITE);
		lblDestinationAdress_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDestinationAdress_1.setBounds(817, 193, 115, 14);
		frame.getContentPane().add(lblDestinationAdress_1);

		btnSave = new JButton("Save Program");
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
							line =line + " " + instructions.get(i).getStartPage();
							line = line + " " + instructions.get(i).getDestinationPage();

							f.println(line);		
						}
						f.close();
					}catch(IOException ex){}

				}
			}
		});
		btnSave.setBounds(258, 592, 129, 28);
		frame.getContentPane().add(btnSave);

		btnLoadFile = new JButton(" Load Program");
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
						panel_1.removeAll();
						panel_1.revalidate();
						maxValue = 0;
						while( (line = readLine.readLine() ) != null){
							String[] words= line.split(" ");
							if(words.length == 2){
								textRam.setText(words[0]);
								textFrame.setText(words[1]);
							}
							else{
								MyPanel myPanel = new MyPanel(window);
								panel_1.add(myPanel);
								myPanel.setStartAddress(words[0]);
								if(words[1].equals("0"))
									myPanel.comboBox.setSelectedItem("Read/Write");
								else
									myPanel.comboBox.setSelectedItem("Transfer");
								myPanel.setDestinationAddress(words[2]);
								myPanel.setStartPage((words[3]));
								myPanel.setDestinationPage(words[4]);
								instructions.add(myPanel);
								panel_1.revalidate();
								textRam.setEnabled(false);
								textFrame.setEnabled(false);
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
		btnLoadFile.setBounds(258, 132, 127, 28);
		frame.getContentPane().add(btnLoadFile);

		lblProgSize = new JLabel("");
		lblProgSize.setForeground(Color.WHITE);
		lblProgSize.setBounds(695, 592, 86, 14);
		frame.getContentPane().add(lblProgSize);

		lblRam = new JLabel("");
		lblRam.setForeground(Color.WHITE);
		lblRam.setBounds(692, 620, 89, 14);
		frame.getContentPane().add(lblRam);
		
		lblDisplay = new JLabel("Program address space crosses RAM address space!");
		lblDisplay.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDisplay.setForeground(Color.RED);
		lblDisplay.setBounds(563, 636, 397, 20);
		lblDisplay.setVisible(false);
		frame.getContentPane().add(lblDisplay);
	}
}
class MyPanel extends JPanel{

	private JTextField textStart;
	private JTextField textDest;
	private JCheckBox checkBox;
	public JComboBox<String> comboBox;
	private JTextField textSPage;
	private JTextField textDPage;
	MyPanel panel;
	Home home;
	private boolean instructionExists=false;

	public int getStartAddress(){
		return  Integer.parseInt(textStart.getText().trim());
	}
	public void setStartAddress(String s){
		textStart.setText(s);
	}

	public int getDestinationAddress(){
		return Integer.parseInt(textDest.getText().trim());
	}
	public void setDestinationAddress(String r){
		textDest.setText(r);
	}

	public int getStartPage(){
		return Integer.parseInt(textSPage.getText().trim());
	}
	public void setStartPage(String sp){
		textSPage.setText(sp);
	}
	public int getDestinationPage(){
		return Integer.parseInt(textDPage.getText().trim());
	}
	public void setDestinationPage(String sp){
		textDPage.setText(sp);
	}

	MyPanel(Home h){                     
		super();
		home=h;
		panel=this;
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
		textStart.setHorizontalAlignment(JTextField.CENTER);
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
		comboBox = new JComboBox<String>();
		comboBox.addItem("Read/Write");
		comboBox.addItem("Transfer");
		comboBox.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String s =textDest.getText().trim();
				if(panel.comboBox.getSelectedItem().equals("Transfer") && s.compareTo("")!=0){
					Home.btnAnalyse.setEnabled(false);
					for(MyPanel p:home.instructions){
						if(p.getStartAddress()==Integer.parseInt(s)){
							instructionExists=true;
							break;
						}
					}
					if(!instructionExists){
						MyPanel p = new MyPanel(home);
						home.panel_1.add(p);
						int s1 = Integer.parseInt(textDest.getText().trim());
						if(s1 != 0){
							int val = s1;
							//val++;	
							p.setStartAddress(Integer.toString(val));
						}
						home.instructions.add(p);
						home.panel_1.revalidate();
					}	
				}
			}

		});
		panel.add(comboBox);
		panel.add(Box.createHorizontalStrut(10));


		textDest = new JTextField();
		textDest.setHorizontalAlignment(JTextField.CENTER);
		textDest.getDocument().addDocumentListener(new DocumentListener(){

			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				onChange();
			}

			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				onChange();
			}

			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				onChange();
			}
			public void onChange(){
				int  s=Integer.parseInt(Home.textFrame.getText().trim());
				String r = textDest.getText().trim(); 
				if(s !=0 && r.compareTo("")!=0){
					int page = s ;
					int start= Integer.parseInt(r);
					int result=start/page;
					textDPage.setText(Integer.toString(result));
					Home.btnAnalyse.setEnabled(true);
				}

			}
		});
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
					//int result=start/page;
				//	textDPage.setText(Integer.toString(result));
					if(panel.comboBox.getSelectedItem().equals("Transfer")){
						for(MyPanel p:home.instructions){
							if(p.getStartAddress()==Integer.parseInt(r)){
								instructionExists=true;
								break;
							}
						}
						if(!instructionExists){
							MyPanel p = new MyPanel(home);
							home.panel_1.add(p);
							int s1 = Integer.parseInt(r);
							if(s1 != 0){
								int val = s1;
								//val++;	
								p.setStartAddress(Integer.toString(val));
							}
							home.instructions.add(p);
							home.panel_1.revalidate();
						}	
					}
				}

			
			}
		});


		panel.add(textDest);
		textDest.setColumns(10);
		panel.add(Box.createHorizontalStrut(10));
		textSPage= new JTextField();
		textSPage.setColumns(10);
		panel.add(textSPage);
		textSPage.setEditable(false);
		textSPage.setHorizontalAlignment(JTextField.CENTER);
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
		textDPage.setHorizontalAlignment(JTextField.CENTER);
		panel.add(textDPage);
		textDPage.setColumns(10);
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


