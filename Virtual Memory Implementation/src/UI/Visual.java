package UI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
public class Visual {
	public JFrame frame;
	public JPanel panel;
	private ArrayList<Integer> referenceString;
	private int returnInstructionIndex=0;
	private boolean isJumpInstruction=false;
	private int pc=0;
	private JPanel parentPanel;
	private int pageFaults=0;
	private int noOfFrames;
	private int noOfPages;
	private int referenceIndex=0;
	private Home home;
	private ArrayList<Integer> pageSet;
	private ArrayList<JPanel> physicalFrames;
	private Queue<Integer> index;
	private HashMap<Integer,Integer> hashIndex;
	private String[] replacementAlgos = { "FIFO", "LRU", "OPT" };
	private String selectedAlgo=null;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_6;
	private JPanel physicalPanel;
	private JPanel physicalSpacePanel;
	private JPanel logicalPanel;
	private JPanel panel_7;
	private JLabel label;
	private JPanel referenceStringPanel;
	private ArrayList<JLabel> referenceStringList;
	private JPanel referenceStringParentPanel;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_5;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JPanel algoSelectionPanel;
	private JButton btnRun;
	private JComboBox<String> comboBoxAlgo; 
	private int noOfHits=0;
	DefaultCategoryDataset dataset;
	private JPanel panel_10;
	private JButton  buttonNextInst;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visual window = new Visual(null);
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
		referenceString= new ArrayList<Integer>();
		referenceStringList = new ArrayList<JLabel>();
		index = new LinkedList<Integer>() ;
		hashIndex=new HashMap<Integer,Integer>();
		noOfFrames=home.getRamSize()/home.getFrameSize();
		pageSet = new ArrayList<Integer>(noOfFrames);
		physicalFrames= new ArrayList<JPanel>(noOfFrames);
		int progValue=Integer.parseInt(home.lblProgSize.getText());
		noOfPages=(progValue/home.getFrameSize()+1);
		initialize();

	}
	/*public Visual() {
		initialize();

	}*/
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 1382, 762);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(109, 153, 877, 479);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			public void adjustmentValueChanged(AdjustmentEvent e) {  
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
			}
		});
		frame.getContentPane().add(scrollPane);
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		parentPanel = new JPanel();
		panel.add(parentPanel);

		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		buttonNextInst = new JButton("Execute Next Instruction");
		buttonNextInst.setForeground(Color.WHITE);
		buttonNextInst.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonNextInst.setBackground(SystemColor.textHighlight);
		buttonNextInst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!buttonNextInst.getText().equals("Save As PDF")){
					executeInstruction();
				}
				else{
					final java.awt.Image image = getImageFromPanel(parentPanel);
					String fileName =selectedAlgo +  "_test.pdf";
					printToPDF(image, fileName);
				}
			}
		});

		buttonNextInst.setBounds(1007, 581, 230, 42);
		frame.getContentPane().add(buttonNextInst);
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(1007, 330, 108, 240);
		frame.getContentPane().add(scrollPane_2);
		physicalSpacePanel = new JPanel();
		scrollPane_2.setViewportView(physicalSpacePanel);
		physicalSpacePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		physicalSpacePanel.setLayout(new BoxLayout(physicalSpacePanel, BoxLayout.X_AXIS));
		physicalPanel = new JPanel();
		physicalSpacePanel.add(physicalPanel);
		physicalPanel.setLayout(new BoxLayout(physicalPanel, BoxLayout.Y_AXIS));
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(1125, 330, 112, 240);
		frame.getContentPane().add(scrollPane_3);
		JPanel logicalSpacePanel = new JPanel();
		logicalSpacePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_3.setViewportView(logicalSpacePanel);
		logicalSpacePanel.setLayout(new BoxLayout(logicalSpacePanel, BoxLayout.X_AXIS));
		logicalPanel = new JPanel();
		logicalSpacePanel.add(logicalPanel);
		logicalPanel.setForeground(new Color(240, 255, 255));
		logicalPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		logicalPanel.setLayout(new BoxLayout(logicalPanel, BoxLayout.Y_AXIS));
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(null);
		panel_6.setBackground(new Color(47, 79, 79));
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_6.setBounds(110, 60, 499, 30);
		frame.getContentPane().add(panel_6);
		panel_3 = new JPanel();
		panel_6.add(panel_3);
		panel_3.setBackground(Color.GREEN);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 13));
		lblNewLabel_1 = new JLabel("Empty Frame");
		panel_6.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(240, 255, 255));
		lblNewLabel_1.setFont(new Font("Palatino Linotype", Font.BOLD, 14));
		panel_6.add(Box.createHorizontalStrut(20));
		panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setVgap(13);
		flowLayout_1.setHgap(10);
		panel_6.add(panel_4);
		panel_4.setBackground(Color.RED);
		JLabel lblNewLabel_2 = new JLabel("Occupied Frame");
		panel_6.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Palatino Linotype", Font.BOLD, 14));
		lblNewLabel_2.setForeground(new Color(240, 255, 255));
		panel_6.add(Box.createHorizontalStrut(20));
		panel_5 = new JPanel();
		panel_5.setBackground(Color.CYAN);
		FlowLayout flowLayout_2 = (FlowLayout) panel_5.getLayout();
		flowLayout_2.setVgap(13);
		flowLayout_2.setHgap(10);
		panel_6.add(panel_5);
		JLabel lblNewLabel_3 = new JLabel("Demand Page");
		panel_6.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(new Color(240, 255, 255));
		lblNewLabel_3.setFont(new Font("Palatino Linotype", Font.BOLD, 14));
		panel_7 = new JPanel();
		panel_7.setBorder(null);
		panel_7.setBackground(new Color(47, 79, 79));
		panel_7.setForeground(new Color(47, 79, 79));
		panel_7.setBounds(111, 101, 556, 41);
		frame.getContentPane().add(panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JLabel lblNewLabel_4 = new JLabel("Total Number Of Frames :");
		panel_7.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Palatino Linotype", Font.BOLD, 14));
		lblNewLabel_4.setForeground(new Color(240, 255, 255));
		label = new JLabel("0");
		label.setText(Integer.toString(noOfFrames));
		label.setForeground(Color.CYAN);
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_7.add(label);
		panel_7.add(Box.createHorizontalStrut(20));
		lblNewLabel_6 = new JLabel("Total Number Of Pages :");
		panel_7.add(lblNewLabel_6);
		lblNewLabel_6.setFont(new Font("Palatino Linotype", Font.BOLD, 14));
		lblNewLabel_6.setForeground(new Color(240, 255, 255));
		JLabel lblTotalPages = new JLabel("");
		panel_7.add(lblTotalPages);
		lblTotalPages.setText(Integer.toString(noOfPages));
		lblTotalPages.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTotalPages.setForeground(Color.CYAN);
		JLabel lblReferencedPages = new JLabel("Referenced Pages");
		lblReferencedPages.setForeground(new Color(135, 206, 250));
		lblReferencedPages.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblReferencedPages.setBounds(109, 643, 154, 33);
		frame.getContentPane().add(lblReferencedPages);

		lblNewLabel = new JLabel("<html>Physical<br>Address Space</html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(1011, 289, 90, 30);
		frame.getContentPane().add(lblNewLabel);

		lblNewLabel_5 = new JLabel("<html>Logical<br>Address Space</html>");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(1131, 289, 90, 30);
		frame.getContentPane().add(lblNewLabel_5);
		
				referenceStringParentPanel = new JPanel();
				referenceStringParentPanel.setBounds(239, 628, 743, 48);
				frame.getContentPane().add(referenceStringParentPanel);
				referenceStringParentPanel.setLayout(new BoxLayout(referenceStringParentPanel, BoxLayout.X_AXIS));
				
						referenceStringPanel = new JPanel();
						referenceStringPanel.setLayout(new BoxLayout(referenceStringPanel, BoxLayout.X_AXIS));
						referenceStringParentPanel.add(referenceStringPanel);

		panel_10 = new JPanel();
		panel_10.setBounds(1007, 141, 258, 147);
		frame.getContentPane().add(panel_10);
		for(int i:referenceString){
			JLabel page = new JLabel(Integer.toString(i));
			page.setMinimumSize(new Dimension(30,30));
			page.setPreferredSize(new Dimension(30,30));
			page.setMaximumSize(new Dimension(30,30));
			page.setOpaque(true);
			page.setHorizontalAlignment(JLabel.CENTER);
			referenceStringPanel.add(Box.createHorizontalStrut(20));
			referenceStringPanel.add(page);	
		}

		createPhysicalAddSpace();
		for(int i=noOfPages-1;i>=0;i--){
			JPanel pan1 = new JPanel();
			pan1.setBackground(Color.CYAN);
			pan1.setBorder(new LineBorder(new Color(0, 0, 0)));
			logicalPanel.add(pan1);
			pan1.setLayout(new BoxLayout(pan1, BoxLayout.X_AXIS));
			JLabel lbl1 = new JLabel("Page  " + i);
			lbl1.setMinimumSize(new Dimension(100,20));
			lbl1.setMaximumSize(new Dimension(100,20));
			lbl1.setPreferredSize(new Dimension(100,2));
			lbl1.setHorizontalAlignment(JLabel.CENTER);
			pan1.add(lbl1);
		}
		dataset =  new DefaultCategoryDataset( );  		        

		// Generate the graph
		JFreeChart chart = ChartFactory.createBarChart(
				"Statistics", 				// Title
				"Page Replacement Algorithm",            
				"Page Faults",
				dataset, 					// Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, 						// Show Legend
				true, 				// Use tooltips
				false 					// Configure chart to generate URLs?
				);
		panel_10.setLayout(new java.awt.BorderLayout());
		ChartPanel CP = new ChartPanel(chart);
		panel_10.add(CP,BorderLayout.CENTER);

		algoSelectionPanel = new JPanel();
		algoSelectionPanel.setBounds(1007, 634, 311, 23);
		frame.getContentPane().add(algoSelectionPanel);
		algoSelectionPanel.setLayout(new BoxLayout(algoSelectionPanel, BoxLayout.X_AXIS));

		Component horizontalStrut_2 = Box.createHorizontalStrut(10);
		algoSelectionPanel.add(horizontalStrut_2);

		JLabel lblNewLabel_8 = new JLabel("Page Replacement Algorithm");
		algoSelectionPanel.add(lblNewLabel_8);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		algoSelectionPanel.add(horizontalStrut);

		comboBoxAlgo = new JComboBox<String>();
		comboBoxAlgo.setMaximumSize(new Dimension(80, 40));
		comboBoxAlgo.setModel(new DefaultComboBoxModel<String>(new String[] {"FIFO", "LRU", "OPT"}));
		algoSelectionPanel.add(comboBoxAlgo);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		algoSelectionPanel.add(horizontalStrut_1);

		btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonNextInst.setText("Next Instruction");
				pc=0;
				returnInstructionIndex=0;
				referenceIndex=0;
				isJumpInstruction=false;
				referenceString.clear();
				referenceStringPanel.removeAll();
				referenceStringParentPanel.removeAll();
				referenceStringList.clear();
				pageSet.clear();
				index.clear();
				hashIndex.clear();
				physicalFrames.clear();
				physicalPanel.removeAll();
				createPhysicalAddSpace();
				pageFaults=0;
				noOfHits=0;
				selectedAlgo=comboBoxAlgo.getSelectedItem().toString();
				buttonNextInst.setText("Next Instruction");
				parentPanel.removeAll();
				referenceStringPanel.revalidate();
				referenceStringParentPanel.revalidate();
				parentPanel.revalidate();
				btnRun.setEnabled(false);
			}
		});
		btnRun.setForeground(Color.WHITE);
		btnRun.setBackground(SystemColor.textHighlight);
		algoSelectionPanel.add(btnRun);
		algoSelectionPanel.setVisible(false);
		CategoryPlot categoryPlot = chart.getCategoryPlot();
		BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
		br.setMaxBarWidth(.05); // set maximum width to 5% of chart
		panel_10.validate();
	}
	public java.awt.Image getImageFromPanel(Component component) {

		BufferedImage image = new BufferedImage(component.getWidth(),component.getHeight(), BufferedImage.TYPE_INT_RGB);
		component.paint(image.getGraphics());
		return image;
	}
	public void printToPDF(java.awt.Image awtImage, String fileName) {
		try {
			Document d = new Document();
			PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(fileName));
			d.open();
			Image iTextImage = Image.getInstance(writer, awtImage, 1);
			iTextImage.setAbsolutePosition(30, 30);
			iTextImage.scalePercent(50);
			d.add(iTextImage);
			d.close();

		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
	void createPhysicalAddSpace(){
		for(int i=0;i<noOfFrames;i++){

			JPanel pan = new JPanel();
			pan.setBackground(Color.GREEN);
			pan.setBorder(new LineBorder(new Color(0, 0, 0)));
			physicalPanel.add(pan);
			pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));
			JLabel lbl = new JLabel();
			lbl.setMinimumSize(new Dimension(100,20));
			lbl.setMaximumSize(new Dimension(100,20));
			lbl.setPreferredSize(new Dimension(100,20));
			lbl.setHorizontalAlignment(JLabel.CENTER);
			pan.add(lbl);
			physicalFrames.add(pan);
		}
	}
	void executeInstruction(){
		if(pc < home.instructions.size()){
			MyPanel  myPanel = home.instructions.get(pc);
			int startPage = myPanel.getStartPage();
			int destPage = myPanel.getDestinationPage();
			String type= (String)myPanel.comboBox.getSelectedItem();
			// if two pages are referenced in a single instruction
			if(startPage!=destPage){

				referenceString.add(startPage);
				addPageInReferenceString(startPage);
				referenceString.add(destPage);
				addPageInReferenceString(destPage);

			}
			else{
				referenceString.add(startPage);
				addPageInReferenceString(startPage);	
			}
			for(JLabel l:referenceStringList)
				l.setOpaque(false);
			referenceStringList.get(referenceStringList.size()-1).setOpaque(true);
			referenceStringPanel.revalidate();
			createMap(Integer.toString(startPage),Integer.toString(destPage),type);
			if(isJumpInstruction){
				isJumpInstruction=false;
				pc=returnInstructionIndex;

			}
			else if(type.equals("GoTo")){
				int destAdd = myPanel.getDestinationAddress();
				if(pc+1  < home.instructions.size()){
					int startAdd=home.instructions.get(pc+1).getStartAddress();

					if(destAdd==startAdd){
						pc++;
					}
				else{
					// save address of next instruction to be executed
					returnInstructionIndex=pc+1;

					for(MyPanel p:home.instructions){
						if(p.getStartAddress() == destAdd){
							// load pc with the address of new instruction
							pc = home.instructions.indexOf(p);
							isJumpInstruction =true;
							break;
						}
					}
				}
				}
			}
			else{
				pc++;
			}
			JPanel line = new JPanel();
			line.setPreferredSize(new Dimension(100, 2));
			line.setBorder(new LineBorder(new Color(0, 0, 0), 2, false));
			parentPanel.add(line);
		}
		else{
			((JButton)algoSelectionPanel.getComponent(5)).setEnabled(true);
			buttonNextInst.setText("Save As PDF");
			dataset.addValue(pageFaults, selectedAlgo, "");
			JLabel displayMsg=new JLabel("Executed All Instructions");
			displayMsg.setFont(new Font("Tahoma", Font.BOLD, 18));
			displayMsg.setForeground(Color.RED);
			parentPanel.add(displayMsg);
		
		}

	}
	void addPageInReferenceString(int pageNo){
		JLabel page = new JLabel(Integer.toString(pageNo));
		page.setMinimumSize(new Dimension(30,30));
		page.setPreferredSize(new Dimension(30,30));
		page.setMaximumSize(new Dimension(30,30));
		page.setBackground(Color.GREEN);
		page.setHorizontalAlignment(JLabel.CENTER);
		referenceStringPanel.add(Box.createHorizontalStrut(20));
		referenceStringPanel.add(page);
		referenceStringList.add(page);
		referenceStringPanel.revalidate();
	}
	@SuppressWarnings("unchecked")
	void createMap(String start,String end,String insType){
		ArrayList<Frame> frames= new ArrayList<Frame>();
		InstructionPanel instPanel = new InstructionPanel();
		instPanel.lblStartPage.setText(start);
		instPanel.lblType.setText(insType);	
		instPanel.lblDestPage.setText(end);
		parentPanel.add(Box.createVerticalStrut(30));
		parentPanel.add(instPanel);
		// traverse the page reference  string 
		while(referenceIndex<referenceString.size() ){
			//if empty frames exist 
			if (pageSet.size() < noOfFrames){
				//if a given page in reference string does not exist in any frame
				if (!pageSet.contains(referenceString.get(referenceIndex))){
					pageSet.add(referenceString.get(referenceIndex));
					index.add(referenceString.get(referenceIndex));
					hashIndex.put(referenceString.get(referenceIndex), referenceIndex);
					pageFaults++;
				}
			}

			else{
				//No room in frames and the referenced page does not exist in frames i.e page fault occurred 
				if (!pageSet.contains(referenceString.get(referenceIndex))){
					instPanel.pf.setVisible(true);
					if(selectedAlgo == null){
						selectedAlgo = (String)JOptionPane.showInputDialog(null, "Choose any Replacement Algorithm ", "Page Fault Occured",
								JOptionPane.INFORMATION_MESSAGE,null, replacementAlgos, replacementAlgos[0]);
						algoSelectionPanel.setVisible(true);
						((JComboBox<String>)algoSelectionPanel.getComponent(3)).setSelectedItem(selectedAlgo);
						((JButton)algoSelectionPanel.getComponent(5)).setEnabled(false);
					}
					if(selectedAlgo.equals("FIFO")){
						fifo(referenceIndex);
					}
					else if(selectedAlgo.equals("LRU")){
						lru(referenceIndex);

					}	
				}
				//No room in frames but the referenced page already present in some frame
				else{
					hashIndex.put(referenceString.get(referenceIndex),referenceIndex);				
					noOfHits++;

				}
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
				Page p = new Page(pageSet.get(pageNo).toString());
				f.add(p);
				f.frame.setBackground(Color.RED);
				physicalFrames.get(noOfFrames-1-i).setBackground(Color.RED);
				((JLabel)physicalFrames.get(noOfFrames-1-i).getComponent(0)).setText("      Page  " + pageSet.get(pageNo).toString() +"     ");
				tempDesc = tempDesc +" Page No. " + (pageSet.get(pageNo).toString()) + " mappped to Frame No." + i+"<br>";
				pageNo++;
			}
			instPanel.mappingPanel.add(f);
			frames.add(f);	
		}
		tempDesc = tempDesc + "</html>";
		instPanel.description.setText(tempDesc);
		parentPanel.revalidate();
	}

	void fifo(int i){	
		int val = index.poll();
		int ind=pageSet.indexOf(val);
		pageSet.remove(ind);
		pageSet.add(ind, referenceString.get(i));
		index.add(referenceString.get(i));

		pageFaults++;
		//lblPageFaults.setText("Page Faults:  "+pageFaults);
		physicalSpacePanel.revalidate();
		parentPanel.revalidate();

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
		pageSet.add(pageIndex,referenceString.get(i));
		hashIndex.put(referenceString.get(i), i);

		pageFaults++;
		//lblPageFaults.setText("Page Faults:  "+pageFaults);
		((JLabel)physicalFrames.get(noOfFrames-1-pageIndex).getComponent(0)).setText("      Page  " + referenceString.get(i) +"     ");
		physicalSpacePanel.revalidate();
		parentPanel.revalidate();	
	}

	private class Frame extends JPanel{
		private Frame panel;
		private JPanel frame;
		private JLabel frameNo;
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
			frameNo=new JLabel("F");
			frame.add(frameNo);
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
			page.setBackground(Color.CYAN);
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


	private class InstructionPanel extends JPanel {
		private JLabel lblInstructionType;
		private JLabel lblType;
		private JLabel lblInstructionSourcePageNo;
		private JLabel lblStartPage;
		private JLabel lblDestinationPageNo;
		private JLabel lblDestPage;
		InstructionPanel panel;
		private JPanel instructionPanel;
		private JPanel instructionTypePanel;
		private JPanel startPagePanel;
		private JPanel destinationPagePanel;
		private JPanel mappingPanel;
		public JLabel description;
		public JLabel pf;

		InstructionPanel() {
			super();
			panel = this;
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			instructionPanel = new JPanel();
			panel.add(instructionPanel);
			instructionPanel.setLayout(new BoxLayout(instructionPanel,BoxLayout.Y_AXIS));
			pf = new JLabel("PAGE FAULT OCCURED");
			pf.setForeground(Color.red);
			pf.setFont(new Font("Tahoma", Font.BOLD, 14));
			instructionPanel.add(Box.createVerticalStrut(10));
			pf.setVisible(false);
			instructionTypePanel = new JPanel();
			instructionPanel.add(instructionTypePanel);

			lblInstructionType = new JLabel("Instruction Type   :");
			lblInstructionType.setFont(new Font("Tahoma", Font.BOLD, 14));
			instructionTypePanel.add(lblInstructionType);

			lblType = new JLabel("");
			instructionTypePanel.add(lblType);

			instructionPanel.add(Box.createVerticalStrut(20));
			startPagePanel = new JPanel();
			instructionPanel.add(startPagePanel);

			lblInstructionSourcePageNo = new JLabel("Source Page Number:         ");
			lblInstructionSourcePageNo.setFont(new Font("Tahoma", Font.BOLD, 14));
			startPagePanel.add(lblInstructionSourcePageNo);

			lblStartPage = new JLabel("");

			startPagePanel.add(lblStartPage);
			instructionPanel.add(Box.createVerticalStrut(20));
			destinationPagePanel = new JPanel();
			instructionPanel.add(destinationPagePanel);
			lblDestinationPageNo = new JLabel("Destination Page Number:");
			lblDestinationPageNo.setFont(new Font("Tahoma", Font.BOLD, 14));
			destinationPagePanel.add(lblDestinationPageNo);

			lblDestPage = new JLabel("");
			destinationPagePanel.add(lblDestPage);
			instructionPanel.add(Box.createVerticalStrut(70));
			mappingPanel = new JPanel();
			panel.add(Box.createHorizontalStrut(100));
			panel.add(mappingPanel);
			panel.add(Box.createHorizontalStrut(30));
			mappingPanel.add(pf);
			description = new JLabel(" ");
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			p.add(pf);
			p.add(Box.createVerticalStrut(10));
			p.add(description);
			panel.add(p);

		}

	}
}


