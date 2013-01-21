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

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tp3.solver.algorithm.LearnerIntelligence;

public class BrainView {
	private JFrame window ;
	private JPanel sadMemories = null;

	private JPanel midPlan = null;

	private JPanel strippedSequences = null;
	private JPanel mergeKnowledge = null;
	private JPanel pureKnowledge = null;
	private JPanel solution = null;
	
	public BrainView(){
		window = new JFrame("Results");
		JPanel panel = new JPanel();
		window.getContentPane().add(panel);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		sadMemories = new JPanel ();
		panel.add(sadMemories);
		
		strippedSequences = new JPanel ();
		panel.add(strippedSequences);

		pureKnowledge = new JPanel();
		panel.add(pureKnowledge);

		mergeKnowledge = new JPanel();
		panel.add(mergeKnowledge);

		midPlan = new JPanel();
		panel.add(midPlan);
			
		solution = new JPanel();
		panel.add(solution);
		
		window.setSize(800, 600);
		window.setVisible(true);
	}
	
	private void showAnything(String title, String content, JPanel jPanel){
		JLabel title2 = new JLabel(title);
		JTextArea jTextArea = new JTextArea();
		jTextArea.setText(content);	
		jTextArea.setEditable(false);
		jPanel.removeAll();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		jPanel.add(title2);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.getViewport().add(jTextArea);
		jPanel.add(jScrollPane);
		jPanel.validate();
	}
	
	public void showMemories(LearnerIntelligence intelligence){
		showAnything("Knowledge", intelligence.memoriesToString(), sadMemories);
		showStrippedSequences(intelligence);
		showPureKnowledge(intelligence);
		showPlan(intelligence);
		showPureMerge(intelligence);
		showRealPlan(intelligence);
	}
	
	public void showPureKnowledge(LearnerIntelligence intelligence){
		showAnything("Pure knowledge", intelligence.pureKnowledgeString(), pureKnowledge);
	}

	public void showPlan(LearnerIntelligence intelligence) {
		showAnything("Merged sequences", intelligence.planToString(), midPlan);
	}
	
	/**
	 * show the pure merge knowledge window (only one at the time)
	 */
	public void showPureMerge(LearnerIntelligence intelligence) {
		showAnything("Unified sequences", intelligence.pureKnowledgeString(), mergeKnowledge);
	}
	
	/**
	 * show the real plan window (only one at the time)
	 */
	public void showRealPlan(LearnerIntelligence intelligence) {
		showAnything("Solution", intelligence.realPlanToString(), solution);
	}

	public void showStrippedSequences(LearnerIntelligence intelligence) {
		showAnything("Stripped sequences", intelligence.showStrippedSequences(), strippedSequences);
	}
}
