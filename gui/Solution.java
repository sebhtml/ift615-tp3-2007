/**
 * ift615 Intelligence artificielle
 * Été 2007
 * tp3 Travail pratique 3
 *
 * Département d'informatique
 * Faculté des sciences
 * Université de Sherbrooke
 *
 * Jonathan Sawyer <Jonathan.Sawyer@USherbrooke.ca>
 * Sébastien Boisvert <Sebastien.Boisvert@USherbrooke.ca>
 *
 */


package tp3.gui;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The solution
 * @author s
 *
 */
public class Solution {
	private Container frame ;
	private JPanel left;
	private JPanel right;
	private ActionsManager actionsManager ;
	private JButton startButton; 
	private JButton opButton;
	private JButton factsButton;
	
	private JFrame frame2 ;
	
	private JLabel opField;
	private JLabel factsField;
	public static final String PATH = "tp3/data/" ;
	private static String DEFAULT_OPERATIONS = "tp3-r_ops.txt"; 
	private static String  DEFAULT_FACTS= "tp3-r_fact4.txt";
	private static String  DEFAULT_OPERATIONSFILE  = PATH+DEFAULT_OPERATIONS; 
	private static String DEFAULT_FACTSFILE  = PATH+DEFAULT_FACTS;

	private String operators_file ;
	private String facts_file ;
	private String operators ;
	private String facts ;
	
	public void setOperatorsFile(String a){
		operators_file = a;
	}
	public void setOperatorsDisplay(String a){
		operators = a;
	}
	public void setFactsFile(String a){
		facts_file = a;
	}
	public void setFactsDisplay(String a){
		facts = a;
	}
	
	public String getOperatorsDisplay(){
		return operators;
	}
	
	public String getFactsDisplay(){
		return facts ;
	}
	
	public String getOperatorsFile(){
		return operators_file;
	}
	
	public String getFactsFile(){
		return facts_file ;
	}
	/**
	 * The constructor
	 *
	 */
	public Solution(){
		operators_file = DEFAULT_OPERATIONSFILE;
		facts_file = DEFAULT_FACTSFILE;
		operators = DEFAULT_OPERATIONS;
		facts = DEFAULT_FACTS;
		actionsManager= new ActionsManager(this);
		
		opButton = new JButton("Load operations");
		
		factsButton = new JButton("Load facts");
		opField = new JLabel(DEFAULT_OPERATIONS);
		factsField = new JLabel(DEFAULT_FACTS);
		opButton.addActionListener(actionsManager);
		factsButton.addActionListener(actionsManager);
		
	}
	
	/**
	 * go for it again
	 *
	 */
	public void go() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	show();
            }
		});
	}

	/**
	 * add buttons
	 *
	 */
	private void addButtons() {
		JPanel jPanel = new JPanel();
		startButton = new JButton("Start");
		startButton.addActionListener(actionsManager);
	
		JPanel jPanel1 = new JPanel();
		JPanel jPanel2 = new JPanel();
		jPanel1.add(opButton);
		jPanel1.add(opField);
		jPanel2.add(factsButton);
		jPanel2.add(factsField);
		jPanel.add(startButton);

		left.add(jPanel1);
		left.add(jPanel2);

		left.add(jPanel);
	}

	/**
	 * show the interface
	 *
	 */
	public void show() {
		createAndShowGUI();
	}

	/**
	 * build the interface
	 *
	 */
	private void createAndShowGUI() {
		frame2 = new JFrame("ift615 - tp3: K THX BYE GG NO RE --lolcat");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame = frame2.getContentPane();

		GridLayout flowLayout = new GridLayout(4, 1);
		GridLayout flowLayout2 = new GridLayout(1, 2);
		//flowLayout.setAlignment(FlowLayout.CENTER);
		frame.setLayout(flowLayout2); 
		left = new JPanel();
		left.setLayout(flowLayout); 
		right = new JPanel();
		frame.add(left);
		frame.add(right);

		JTextArea jTextArea = new JTextArea("Jonathan Sawyer, Sébastien Boisvert");
		jTextArea.setEditable(false);
		left.add(jTextArea);
		
		
		
	
		addButtons();	
	
		frame2.pack();
		
		frame2.setVisible(true);
	}
	
	/**
	 * get the start button
	 * @return
	 */
	public Object getStartButton() {
		return startButton;
	}

	/**
	 * get the operations button
	 * @return
	 */
	public Object getOpButton() {
		return opButton;
	}

	/**
	 * get the facts button
	 * @return
	 */
	public Object getFactsButton() {
		return factsButton;
	}

	/**
	 * update the facts label
	 * @param fileName
	 */
	public void updateFactsLabel(String fileName) {
		getFactsLabel().setText(fileName);
	}

	/**
	 * the the facts label
	 * @return
	 */
	private JLabel getFactsLabel() {
		// TODO Auto-generated method stub
		return factsField;
	}

	/**
	 * update the operations label
	 * @param fileName
	 */
	public void updateOpLabel(String fileName) {
		getOpLabel().setText(fileName);
	}

	/**
	 * get the operation label
	 * @return
	 */
	private JLabel getOpLabel() {
		return opField;
	}

	/**
	 * close it...
	 */
	public void close() {
		frame2.setVisible(false);
	}
}
