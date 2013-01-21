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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import tp3.representation.Entity;
import tp3.representation.Operator;
import tp3.solver.Solver;
import tp3.solver.SolverGraph;
import tp3.solver.algorithm.Intelligence;
import tp3.solver.algorithm.LearnerIntelligence;

/**
 * a class for the solver view
 * 
 * @author s
 * 
 */
public class SolverView implements ActionListener, TreeSelectionListener {
	private JPanel facts;

	private JPanel operations;
	private GraphActionsListener graphActionsListener;
	private ActionsGraphActionsListener actionsGraphActionsListener;
	private JPanel actions;

	private JPanel entities;

	private JPanel preconditions;

	private JPanel effects;
	
	private JPanel possibleActions;
	private PaneGenerator paneGenerator;
	private Solver solver;
	private JButton  solverButton;
	private JFrame frame;

	private JButton factsButton;

	private JButton opButton;

	private TreeMap operationButtonForMapping;

	private JLabel amountTries;
	private JLabel amountMem;
	private boolean enable;
	/**
	 * get a listener
	 */
	public ActionsGraphActionsListener getActionsGraphActionsListener(){
		return actionsGraphActionsListener;
	}
	
	/**
	 * constructor
	 * @param solver
	 */
	public SolverView(Solver solver) {
		this.solver = solver;
		operationButtonForMapping = new TreeMap();
		paneGenerator = new PaneGenerator();
		enable =  true;
		amountTries = new JLabel();
	}

	/**
	 * show it
	 *
	 */
	public void show(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                show2();
            }
        });
	}
	
	/**
	 * show it
	 *
	 */
	private void show2() {
		generateActionsListener();
		
		createGrids();
		showPanels();
		

		//frame.pack();
		frame.setVisible(true);
	}

	/**
	 * get action listener
	 *
	 */
	private void generateActionsListener() {
		graphActionsListener = new GraphActionsListener(solver);
		actionsGraphActionsListener = new ActionsGraphActionsListener(solver);
	}

	/**
	 * create various grids
	 *
	 */
	private void createGrids() {
		frame = new JFrame("Solver View 1.22");
	
		frame.setSize(1000, 700);
		frame.validate();
		facts = new JPanel();
		operations = new JPanel();
		actions = new JPanel();
		entities = new JPanel();
		preconditions = new JPanel();
		effects = new JPanel();
		possibleActions = new JPanel();
		amountMem = new JLabel();
		JPanel gridLayoutPanel = new JPanel();
		JPanel gridLayoutMainPanel = new JPanel();
		gridLayoutMainPanel.setLayout(new BorderLayout());
		gridLayoutMainPanel.add(gridLayoutPanel, BorderLayout.CENTER);
		
		gridLayoutPanel.setLayout(new GridLayout(2, 1));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));
		gridLayoutPanel.add(topPanel);
		generateFactsGrid(topPanel);
		generateOperatorsGrid(topPanel);
		generateMainGrid(gridLayoutPanel);		
		frame.getContentPane().add(gridLayoutMainPanel);
	}

	/**
	 * generate the facts grid
	 * @param gridLayoutPanel
	 */
	private void generateFactsGrid(JPanel gridLayoutPanel) {
		JPanel gridFactsPanel = new JPanel();
		JPanel gridFacts2Panel = new JPanel();
		factsButton = new JButton(solver.getSolution().getFactsDisplay());
		factsButton.addActionListener(this);
		gridFacts2Panel.setLayout(new BorderLayout());
		gridFacts2Panel.add(factsButton, BorderLayout.WEST);
		
		gridFacts2Panel.add(gridFactsPanel, BorderLayout.CENTER);
		

		gridFactsPanel.setLayout(new GridLayout(1, 3));
		gridFactsPanel.add(entities);
		gridFactsPanel.add(preconditions);
		gridFactsPanel.add(effects);
		gridLayoutPanel.add(gridFacts2Panel);
	}

	/**
	 * generate the operator grid
	 *
	 */
	private void generateOperatorsGrid(JPanel gridLayoutPanel) {
		opButton = new JButton(solver.getSolution().getOperatorsDisplay());
		JPanel opGrid2Panel = new JPanel();
		opButton.addActionListener(this);
		opGrid2Panel.setLayout(new BorderLayout());
		opGrid2Panel.add(opButton, BorderLayout.WEST);

		opGrid2Panel.add(operations, BorderLayout.CENTER);
		gridLayoutPanel.add(opGrid2Panel);
	}

	/**
	 * generate the main grid
	 *
	 */
	private void generateMainGrid(JPanel gridLayoutPanel) {
		JPanel mainGrid2Panel = new JPanel();
		JPanel mainGridPanel = new JPanel();
		actions.setLayout(new BorderLayout());
		possibleActions.setLayout(new BorderLayout());
		facts.setLayout(new BorderLayout());
		mainGridPanel.setLayout(new GridLayout(1, 3));
		mainGridPanel.setSize(600, 600);
		mainGridPanel.setForeground(Color.YELLOW);
		mainGridPanel.validate();
		effects.setLayout(new BorderLayout());
		mainGridPanel.add(actions);
		mainGridPanel.add(facts);
		mainGridPanel.add(possibleActions);
		mainGrid2Panel.setLayout(new BorderLayout());
		JPanel bestJPanel = new JPanel();
		bestJPanel.setLayout(new FlowLayout());
		bestJPanel.add(new JLabel("Plan"));
		solverButton = new JButton("Solve!");
		bestJPanel.add(solverButton);
		bestJPanel.add(amountTries);
		bestJPanel.add(amountMem);
		solverButton.addActionListener(this);
		mainGrid2Panel.add(bestJPanel, BorderLayout.NORTH);
		mainGrid2Panel.add(mainGridPanel, BorderLayout.CENTER);
		gridLayoutPanel.add(mainGrid2Panel);
	}

	/**
	 * set tries
	 */
	public void setTries(int i){
		amountTries.setText("Amount: "+i);
	}
	
	/**
	 * set tries
	 */
	public void setMem(int i){
		amountMem.setText("In memory: "+i);
	}
	/**
	 * show the panels
	 *
	 */
	private void showPanels() {
		showEntities();
		showPreconds();
		showEffects();
		showOperators();
		
		updateMainPanel();
	}

	/**
	 * update the main panel
	 *
	 */
	public void updateMainPanel() {
		showActions();
		showPossibleActions();
		showFacts();
		showEffects();
		//frame.pack();
		getActionsGraphActionsListener().generateMapping();
	}

	/**
	 * show actions
	 *
	 */
	private void showActions() {
		SolverGraph solverGraph = solver.getSolverGraph();
		Vector nodes = solverGraph.getNodes();
	
		JScrollPane treeView = paneGenerator.generatePaneNodes(nodes, "Actions taken so far so good", actionsGraphActionsListener);
		
		actions.removeAll();
		actions.add(treeView);
		actions.validate();
	}
	
	/**
	 * show possible actions
	 *
	 */
	private void showPossibleActions() {
		Vector vector = solver.getPossibleActs();
		String selectedNode = solver.getSolverGraph().getCurrentNode().toString();
		JScrollPane treeView = paneGenerator.generatePaneAction(vector, "Possible actions", graphActionsListener, selectedNode);

		possibleActions.removeAll();
		possibleActions.add(treeView);
		possibleActions.validate();
	}
	/**
	 * show the facts
	 *
	 */
	private void showFacts() {
		Vector vector = solver.getSolverFacts();

		JScrollPane treeView = (new PaneGenerator()).generatePane(vector, "Facts");

		facts.removeAll();
		facts.add(treeView);
		facts.validate();
	}

	/**
	 * show the operators
	 *
	 */
	private void showOperators() {
		Vector vector = solver.getOmegatronParser().getFileOperations();

		Iterator iterator = vector.iterator();

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Operators");
		while (iterator.hasNext()) {
			Operator expression = (Operator) iterator.next();
	
			String key = expression.getName();
			operationButtonForMapping.put(key, expression);
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
					key);
			
			top.add(defaultMutableTreeNode);
	
		}
		JTree operatorsTree = new JTree(top);
		operatorsTree.addTreeSelectionListener(this);
		JScrollPane treeView = new JScrollPane();
		treeView.getViewport().add(operatorsTree);
		operations.setLayout(new BorderLayout());
		operations.add(treeView);
	}

	/**
	 * show effects
	 *
	 */
	private void showEffects() {
		Vector vector = solver.getOmegatronParser().getFileEffects();
		Vector facts = solver.getSolverGraph().getCurrentNode().getSolverState().getFacts();
		JScrollPane treeView = (new PaneGenerator()).generatePaneEffects(vector, "Effects", facts);
	
		effects.removeAll();
		effects.add(treeView);
		effects.validate();
	}

	/**
	 * shoe preconditions
	 *
	 */
	private void showPreconds() {
	
		Vector vector = solver.getOmegatronParser().getFilePreconditions();

		JScrollPane treeView = (new PaneGenerator()).generatePane(vector, "Preconditions");
		preconditions.setLayout(new BorderLayout());
		preconditions.add(treeView);
	}

	/**
	 * show entities 
	 *
	 */
	private void showEntities() {
	
		Vector vector = solver.getOmegatronParser().getFileEntities();
		Iterator iterator = vector.iterator();

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Entities");
		while (iterator.hasNext()) {
			Entity entity = (Entity) iterator.next();
			
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
					entity.toString());

			top.add(defaultMutableTreeNode);
			
		}
		JTree jTree = new JTree(top);
		JScrollPane treeView = new JScrollPane();
		treeView.getViewport().add(jTree);
		entities.setLayout(new BorderLayout());
		entities.add(treeView);
	}

	/**
	 * when an action is performed
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == factsButton) {
			FileViewer fileViewer = new FileViewer(solver.getSolution().getFactsFile());
			fileViewer.goForIt();
		} else if (e.getSource() == opButton) {
			FileViewer fileViewer = new FileViewer(solver.getSolution().getOperatorsFile());
			fileViewer.goForIt();
		} else if(e.getSource() == solverButton){
			if(enable){
				Intelligence intelligence = new LearnerIntelligence();
				intelligence.setSolver(solver);
				intelligence.thinkNow();
				enable = false;
				solverButton.setText(solverButton.getText()+" [stop]");
			}else{
				enable();
			}
		}
	}
	

	/**
	 * detect tree Event..
	 */
	public void valueChanged(TreeSelectionEvent e) {

		String key = (String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject();
		if(operationButtonForMapping.containsKey(key)){
			Operator operator = (Operator)operationButtonForMapping.get(key);
			OperatorWindow operatorWindow = new OperatorWindow(operator);
			operatorWindow.run();
		}
		
	}

	/**
	 * check if it is enabled
	 * @return
	 */
	public boolean getEnable() {
		return enable;
	}

	public void enable() {
		enable = true;
		solverButton.setText("solve");
	}
}
