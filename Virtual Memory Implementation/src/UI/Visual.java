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
	private int referenceIndex=0;
	private Home home;
	ArrayList<Integer> pageSet;
	Queue<Integer> index;
	HashMap<Integer,Integer> hashIndex;
	private String[] replacementAlgos = { "FIFO", "LRU", "OPT" };
	private String selectedAlgo=null;
	JLabel lblPageFaults;
	private int lruValue=0;
	JLabel lblNewLabel;


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
		initialize();
		pageRefString = new ArrayList<Integer>();
		index = new LinkedList<Integer>() ;
		hashIndex=new HashMap<Integer,Integer>();
		noOfFrames=home.getRamSize()/home.getFrameSize();
		pageSet = new ArrayList<Integer>(noOfFrames);

	}
	public Visual() {
		initialize();

	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 51, 51));
		frame.setBounds(100, 100, 943, 469);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 500, 408);
		frame.getContentPane().add(scrollPane);
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		JButton btnEnterNextInstruction = new JButton("Enter Next Instruction");
		btnEnterNextInstruction.setBackground(UIManager.getColor("Button.darkShadow"));
		btnEnterNextInstruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(instructionIndex < home.instructions.size()){
					myPanel  j = home.instructions.get(instructionIndex);	
					int startPage = Integer.parseInt(j.textSPage.getText());
					int destPage = Integer.parseInt(j.textDPage.getText());
					pageRefString.add(startPage);
					pageRefString.add(destPage);
					createMap(j.textSPage.getText(),j.textDPage.getText(),(String)j.comboBox.getSelectedItem());
				}
			}
		});

		btnEnterNextInstruction.setBounds(565, 377, 129, 42);
		frame.getContentPane().add(btnEnterNextInstruction);
		panel_2 = new JPanel();
		panel_2.setVisible(true);
		panel_2.setBounds(538, 74, 188, 292);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		frame.getContentPane().add(panel_2);

		lblPageFaults = new JLabel("Page Faults:  0");
		lblPageFaults.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(lblPageFaults);

		lblNewLabel = new JLabel("New label");
		panel_2.add(lblNewLabel);
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
					if(selectedAlgo == null){
						selectedAlgo = (String)JOptionPane.showInputDialog(null, "Choose any Replacement Algorithm", "Page Fault Occured",
								JOptionPane.ERROR_MESSAGE,null, replacementAlgos, replacementAlgos[0]);
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
		for(int i=0;i<noOfFrames;i++){
			Frame f = new Frame();
			if(pageNo<pageSet.size()){
				Line l = new Line();
				f.add(l);
				Page p = new Page(pageSet.get(pageNo++).toString());
				f.add(p);
				f.frame.setBackground(Color.RED);
			}
			v.mappingPanel.add(f);
			frames.add(f);	
		}
		instructionIndex ++;
		panel_1.revalidate();	
	}
	void fifo(int i){	
		int val = index.poll();
		int ind=pageSet.indexOf(val);
		pageSet.remove(ind);
		pageSet.add(ind, pageRefString.get(i));
		index.add(pageRefString.get(i));
		pageFaults++;
		lblPageFaults.setText(Integer.toString(pageFaults));
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
		lblPageFaults.setText(Integer.toString(pageFaults));
		lblNewLabel.setText(hashIndex.toString());
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
		private JLabel lblInstructionPageNo;
		private JLabel lblStartPage;
		private JLabel lblDestinationPageNo;
		private JLabel lblDestPage;
		ViPanel panel;
		private JPanel instructionPanel;
		private JPanel instructionTypePanel;
		private JPanel startPagePanel;
		private JPanel destinationPagePanel;
		private JPanel mappingPanel;

		ViPanel() {
			super();
			panel = this;
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			instructionPanel = new JPanel();
			panel.add(instructionPanel);
			instructionPanel.setLayout(new BoxLayout(instructionPanel,
					BoxLayout.Y_AXIS));

			instructionTypePanel = new JPanel();
			instructionPanel.add(instructionTypePanel);

			lblInstructionType = new JLabel("INSTRUCTION TYPE:");
			instructionTypePanel.add(lblInstructionType);

			lblType = new JLabel("page 1");
			instructionTypePanel.add(lblType);

			instructionPanel.add(Box.createVerticalStrut(20));

			startPagePanel = new JPanel();
			instructionPanel.add(startPagePanel);

			lblInstructionPageNo = new JLabel("INSTRUCTION PAGE NO: ");
			startPagePanel.add(lblInstructionPageNo);

			lblStartPage = new JLabel("New label");

			startPagePanel.add(lblStartPage);
			instructionPanel.add(Box.createVerticalStrut(20));

			destinationPagePanel = new JPanel();
			instructionPanel.add(destinationPagePanel);

			lblDestinationPageNo = new JLabel("DESTINATION PAGE NO:");
			destinationPagePanel.add(lblDestinationPageNo);

			lblDestPage = new JLabel("New label");
			destinationPagePanel.add(lblDestPage);
			mappingPanel = new JPanel();
			panel.add(mappingPanel);

		}

	}
}
