package UI;

import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Color;

import javax.swing.UIManager;

import java.awt.Dimension;

import javax.swing.border.LineBorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JComboBox;

import java.awt.Font;

import javax.swing.SwingConstants;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.SystemColor;
public class Visual {
	public JFrame frame;
	private JPanel panel;
	private ArrayList<Integer> pageRefString;
	private int frameNo=0;
	private int instructionIndex=0;
	private JComboBox comboBox;
	private JPanel panel_2;
	private JPanel panel_1;
	private int pageFaults=0;
	private int noOfFrames;
	private int noOfPages;
	private int referenceIndex=0;
	private Home home;
	ArrayList<Integer> pageSet;
	ArrayList<JPanel> physicalFrames;
	Queue<Integer> index;
	HashMap<Integer,Integer> hashIndex;
	private String[] replacementAlgos = { "FIFO", "LRU", "OPT" };
	private String selectedAlgo=null;
	JLabel lblPageFaults;
	private int lruValue=0;
	JLabel lblNewLabel;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JPanel physicalPanel;
	private JPanel mappingPanel;
	private JPanel panel_10;
	private JPanel logicalPanel;
	private JPanel panel_7;
	private JLabel label;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_5;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visual window = new Visual();
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
	public Visual(Home h) {
		home=h;
		
		pageRefString = new ArrayList<Integer>();
		index = new LinkedList<Integer>() ;
		hashIndex=new HashMap<Integer,Integer>();
		noOfFrames=home.getRamSize()/home.getFrameSize();
		pageSet = new ArrayList<Integer>(noOfFrames);
		physicalFrames= new ArrayList<JPanel>(noOfFrames);
		int progValue=Integer.parseInt(home.lblProgSize.getText());
		noOfPages=(progValue/home.getFrameSize()+1);
		initialize();

	}
	public Visual() {
		initialize();

	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.getContentPane().setBackground(new Color(0, 51, 51));
		frame.setBounds(100, 100, 1122, 714);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(111, 231, 777, 398);
		frame.getContentPane().add(scrollPane);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		
		JButton btnEnterNextInstruction = new JButton("Enter Next Instruction");
		btnEnterNextInstruction.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEnterNextInstruction.setBackground(UIManager.getColor("Button.darkShadow"));
		btnEnterNextInstruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(instructionIndex < home.instructions.size()){
					myPanel  j = home.instructions.get(instructionIndex);
					int pc;
					int startPage = Integer.parseInt(j.textSPage.getText());
					int destPage = Integer.parseInt(j.textDPage.getText());
					String type= (String)j.comboBox.getSelectedItem();
					
					pageRefString.add(startPage);
					pageRefString.add(destPage);
					if(type.equals("Read/Write")){
						createMap(j.textSPage.getText(),j.textDPage.getText(),(String)j.comboBox.getSelectedItem());
					}
					else{
						pc = instructionIndex+1;
						int destAdd = j.getDestinationAddress();
						
					}
					JPanel panel_6 = new JPanel();
					panel_6.setPreferredSize(new Dimension(100, 4));
					
					panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 2, false));
					panel_1.add(panel_6);
					
				}
			}
		});

		btnEnterNextInstruction.setBounds(898, 570, 198, 59);
		frame.getContentPane().add(btnEnterNextInstruction);
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setVisible(true);
		panel_2.setBounds(701, 96, 187, 124);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		frame.getContentPane().add(panel_2);

		lblNewLabel = new JLabel("");
		panel_2.add(lblNewLabel);
				
				JLabel lblNewLabel_8 = new JLabel("Statistics:");
				lblNewLabel_8.setFont(new Font("Calibri", Font.BOLD, 16));
				panel_2.add(lblNewLabel_8);
		
				lblPageFaults = new JLabel("No. Of Page Faults:");
				panel_2.add(lblPageFaults);
				lblPageFaults.setFont(new Font("Calibri", Font.BOLD, 15));
				
				lblNewLabel_9 = new JLabel("No. Of Page Hits:");
				lblNewLabel_9.setFont(new Font("Calibri", Font.BOLD, 15));
				panel_2.add(lblNewLabel_9);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Virtual Memory (Visualization)");
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesTitle.setFont(new Font("Calibri", Font.BOLD, 28));
		lblNewJgoodiesTitle.setForeground(new Color(253, 245, 230));
		lblNewJgoodiesTitle.setBounds(196, 11, 535, 59);
		frame.getContentPane().add(lblNewJgoodiesTitle);
		//int s=(home.getRamSize()/home.getFrameSize());
	
		mappingPanel = new JPanel();
		mappingPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		mappingPanel.setBounds(898, 328, 198, 231);
		frame.getContentPane().add(mappingPanel);
		mappingPanel.setLayout(new BoxLayout(mappingPanel, BoxLayout.X_AXIS));
		
		physicalPanel = new JPanel();
		mappingPanel.add(physicalPanel);
		physicalPanel.setLayout(new BoxLayout(physicalPanel, BoxLayout.Y_AXIS));
		
		logicalPanel = new JPanel();
		logicalPanel.setForeground(new Color(240, 255, 255));
		logicalPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		mappingPanel.add(Box.createHorizontalStrut(42));
		mappingPanel.add(logicalPanel);
		logicalPanel.setLayout(new BoxLayout(logicalPanel, BoxLayout.Y_AXIS));
		
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6.setBackground(SystemColor.control);
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_6.setBounds(111, 91, 580, 59);
		frame.getContentPane().add(panel_6);
		
		panel_3 = new JPanel();
		panel_6.add(panel_3);
		panel_3.setBackground(Color.GREEN);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 14));
		
		lblNewLabel_1 = new JLabel("Empty Frame");
		panel_6.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 14));
		panel_6.add(Box.createHorizontalStrut(100));
		
		panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setVgap(14);
		flowLayout_1.setHgap(10);
		panel_6.add(panel_4);
		panel_4.setBackground(Color.RED);
		
		JLabel lblNewLabel_2 = new JLabel("Occupied Frame");
		panel_6.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Calibri", Font.BOLD, 14));
		lblNewLabel_2.setForeground(Color.BLACK);
		panel_6.add(Box.createHorizontalStrut(100));
		
		panel_5 = new JPanel();
		panel_5.setBackground(Color.BLUE);
		FlowLayout flowLayout_2 = (FlowLayout) panel_5.getLayout();
		flowLayout_2.setVgap(14);
		flowLayout_2.setHgap(10);
		panel_6.add(panel_5);
		
		JLabel lblNewLabel_3 = new JLabel("Demand Page");
		panel_6.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(Color.BLACK);
		lblNewLabel_3.setFont(new Font("Calibri", Font.BOLD, 14));
		
		panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_7.setBackground(SystemColor.control);
		panel_7.setForeground(new Color(47, 79, 79));
		panel_7.setBounds(111, 161, 580, 59);
		
		
		frame.getContentPane().add(panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_4 = new JLabel("Total Number Of Frames :");
		panel_7.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_4.setForeground(Color.BLACK);
		
		label = new JLabel("");
		label.setText(Integer.toString(noOfFrames));
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_7.add(label);
		panel_7.add(Box.createHorizontalStrut(60));
		//lblNewLabel_4.add(Box.createHorizontalGlue( ));
		
		lblNewLabel_6 = new JLabel("Total Number Of Pages :");
		panel_7.add(lblNewLabel_6);
		lblNewLabel_6.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_6.setForeground(Color.BLACK);
		
		JLabel lblTotalPages = new JLabel("");
		panel_7.add(lblTotalPages);
		
		lblTotalPages.setText(Integer.toString(noOfPages));
		lblTotalPages.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTotalPages.setForeground(Color.BLACK);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(47, 79, 79));
		panel_8.setBounds(898, 231, 198, 97);
		frame.getContentPane().add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.X_AXIS));
		
		lblNewLabel_5 = new JLabel("<html>Memory Space<br>Physical Memory Space and Logical Memory Space</html>");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Calibri", Font.BOLD, 16));
		panel_8.add(lblNewLabel_5);
		for(int i=0;i<noOfFrames;i++){
			
			JPanel pan = new JPanel();
			pan.setBackground(Color.GREEN);
			pan.setBorder(new LineBorder(new Color(0, 0, 0)));
			physicalPanel.add(pan);
			pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));
			JLabel lbl = new JLabel("                         ");
			
			pan.add(lbl);
			physicalFrames.add(pan);
		}
	
		for(int i=noOfPages-1;i>=0;i--){
			
			JPanel pan1 = new JPanel();
			pan1.setBackground(Color.BLUE);
			pan1.setBorder(new LineBorder(new Color(0, 0, 0)));
			logicalPanel.add(pan1);
			pan1.setLayout(new BoxLayout(pan1, BoxLayout.X_AXIS));
			JLabel lbl1 = new JLabel("      Page  " + i+"     ");
			pan1.add(lbl1);
	}

	}

	void createMap(String start,String end,String insType){
		ArrayList<Frame> frames= new ArrayList<Frame>();
		ViPanel v = new ViPanel();
		v.lblStartPage.setText(start);
		v.lblType.setText(insType);	
		v.lblDestPage.setText(end);
		panel_1.add(v);
	
		// traverse the page reference  string 
		while(referenceIndex<pageRefString.size()){
			//if empty frames exist 
			if (pageSet.size() < noOfFrames){
				//if a given page in reference string does not exist in any frame
				if (!pageSet.contains(pageRefString.get(referenceIndex))){
					pageSet.add(pageRefString.get(referenceIndex));
					index.add(pageRefString.get(referenceIndex));
					hashIndex.put(pageRefString.get(referenceIndex), referenceIndex);
				}
			}

			else{
				//No room in frames and the referenced page does not exist in frames i.e page fault occurred 
				if (!pageSet.contains(pageRefString.get(referenceIndex))){
					v.pf.setVisible(true);
					if(selectedAlgo == null){
						selectedAlgo = (String)JOptionPane.showInputDialog(null, "Choose any Replacement Algorithm ", "Page Fault Occured",
								JOptionPane.INFORMATION_MESSAGE,null, replacementAlgos, replacementAlgos[0]);
					}
					if(selectedAlgo.equals("FIFO")){
						fifo(referenceIndex);
					}
					else if(selectedAlgo.equals("LRU")){
						lru(referenceIndex);

					}	
				}
				//No room in frames but the referenced page already present in some frame
				else
					hashIndex.put(pageRefString.get(referenceIndex),referenceIndex);
			}
			referenceIndex++;
		}

		int pageNo=0;
		String tempDesc="<html>";
		for(int i=0;i<noOfFrames;i++){
			Frame f = new Frame();
			if(pageNo<pageSet.size()){
				Line l = new Line();
				f.add(l);
				Page p = new Page(pageSet.get(pageNo++).toString());
				f.add(p);
				f.frame.setBackground(Color.RED);
				physicalFrames.get(noOfFrames-1-i).setBackground(Color.RED);
				((JLabel)physicalFrames.get(noOfFrames-1-i).getComponent(0)).setText("      Page  " + i +"     ");
				tempDesc = tempDesc +" Page No. " + (pageSet.get(pageNo-1).toString()) + " mappped to Frame No." + i+"<br>";
			}
			v.mappingPanel.add(f);
			
			
			frames.add(f);	
		}
		instructionIndex ++;
		tempDesc = tempDesc + "</html>";
		
		v.description.setText(tempDesc);
		panel_1.revalidate();	
	}
	void fifo(int i){	
		int val = index.poll();
		int ind=pageSet.indexOf(val);
		pageSet.remove(ind);
		pageSet.add(ind, pageRefString.get(i));
		index.add(pageRefString.get(i));
		pageFaults++;
		lblPageFaults.setText("No. Of Page Faults:" + Integer.toString(pageFaults));
		
		((JLabel)physicalFrames.get(noOfFrames-1-ind).getComponent(0)).setText("      " + pageRefString.get(i) +"     ");
		mappingPanel.revalidate();
		panel_1.revalidate();

	}
	void lru(int i){
		int l=Integer.MAX_VALUE; 
		int page=-1;
		Iterator<Integer> it = pageSet.iterator();
		while(it.hasNext()){
			int	p = it.next();
			if(hashIndex.get(p)<l){
				page =p;
				l=hashIndex.get(p);
			}	
		}
		int pageIndex=pageSet.indexOf(page);
		pageSet.remove(pageIndex);
		hashIndex.remove(page);
		pageSet.add(pageIndex,pageRefString.get(i));
		hashIndex.put(pageRefString.get(i), i);
		pageFaults++;
		lblPageFaults.setText("No. Of Page Faults:" + Integer.toString(pageFaults));
		lblNewLabel.setText(hashIndex.toString());
		((JLabel)physicalFrames.get(noOfFrames-1-pageIndex).getComponent(0)).setText("      Page  " + pageRefString.get(i) +"     ");
		mappingPanel.revalidate();
		panel_1.revalidate();	
	}

	private class Frame extends JPanel{
		private Frame panel;
		private JPanel frame;
		Frame(){
			super();
			panel = this;
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			frame = new JPanel();
			frame.setBackground(Color.GREEN);
			FlowLayout flowLayout = (FlowLayout) frame.getLayout();
			flowLayout.setVgap(24);
			flowLayout.setHgap(10);
			frame.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel.add(frame);	
		}

	}   
	private class Page extends JPanel{
		private JPanel page;
		private JLabel pageNo;
		Page(String p){
			super();  		
			page = this;
			FlowLayout flowLayout_1 = (FlowLayout) page.getLayout();
			flowLayout_1.setVgap(20);
			flowLayout_1.setHgap(10);
			page.setBorder(new LineBorder(new Color(0, 0, 0)));
			pageNo = new JLabel(p);
			pageNo.setMaximumSize(new Dimension(10, 20));
			page.add(pageNo);
		}
	} 
	private class Line extends JPanel{
		private JPanel line;
		Line(){
			super();
			line = this;   		
			line.setMaximumSize(new Dimension(2, 20));
			line.setBorder(new LineBorder(new Color(0, 0, 0), 3));
			line.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); 
		}
	}


	private class ViPanel extends JPanel {
		private JLabel lblInstructionType;
		private JLabel lblType;
		private JLabel lblInstructionSourcePageNo;
		private JLabel lblStartPage;
		private JLabel lblDestinationPageNo;
		private JLabel lblDestPage;
		ViPanel panel;
		private JPanel instructionPanel;
		private JPanel instructionTypePanel;
		private JPanel startPagePanel;
		private JPanel destinationPagePanel;
		private JPanel mappingPanel;
		public JLabel description;
		public JLabel pf;

		ViPanel() {
			super();
			panel = this;
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			instructionPanel = new JPanel();
			panel.add(instructionPanel);
			instructionPanel.setLayout(new BoxLayout(instructionPanel,BoxLayout.Y_AXIS));

			instructionTypePanel = new JPanel();
			instructionPanel.add(instructionTypePanel);

		
			lblInstructionType = new JLabel("Instruction Type: ");
			lblInstructionType.setFont(new Font("Tahoma", Font.BOLD, 12));
			instructionTypePanel.add(lblInstructionType);
			instructionTypePanel.add(Box.createHorizontalStrut(10));

			lblType = new JLabel("");
			instructionTypePanel.add(lblType);

			instructionPanel.add(Box.createVerticalStrut(20));
			

			startPagePanel = new JPanel();
			instructionPanel.add(startPagePanel);

			lblInstructionSourcePageNo = new JLabel("Source Page Number:          ");
			lblInstructionSourcePageNo.setFont(new Font("Tahoma", Font.BOLD, 12));
			startPagePanel.add(lblInstructionSourcePageNo);

			lblStartPage = new JLabel("");

			startPagePanel.add(lblStartPage);
			instructionPanel.add(Box.createVerticalStrut(20));

			destinationPagePanel = new JPanel();
			instructionPanel.add(destinationPagePanel);
			pf = new JLabel("PAGE FAULT OCCURED");
			pf.setForeground(Color.red);
			pf.setFont(new Font("Tahoma", Font.BOLD, 18));
			instructionPanel.add(Box.createVerticalStrut(30));
			instructionPanel.add(pf);
			pf.setVisible(false);

			lblDestinationPageNo = new JLabel("Destination Page Number:  ");
			lblDestinationPageNo.setFont(new Font("Tahoma", Font.BOLD, 12));
			destinationPagePanel.add(lblDestinationPageNo);

			lblDestPage = new JLabel("");
			destinationPagePanel.add(lblDestPage);
			instructionPanel.add(Box.createVerticalStrut(70));
			mappingPanel = new JPanel();
			panel.add(Box.createHorizontalStrut(100));
			panel.add(mappingPanel);
			panel.add(Box.createHorizontalStrut(30));
			description = new JLabel(" ");
			
			panel.add(description);

		}

	}
}
