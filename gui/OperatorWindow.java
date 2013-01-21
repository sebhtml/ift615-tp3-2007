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
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import tp3.representation.Entity;
import tp3.representation.Expression;
import tp3.representation.Operator;

/**
 * Operator window
 * @author s
 *
 */
public class OperatorWindow {
	private Operator operator;

	private DefaultMutableTreeNode top;
	
	/**
	 * a private constructor
	 *
	 */
	private OperatorWindow(){
		
	}
	
	/**
	 * the real constructor
	 * @param operator
	 */
	public OperatorWindow(Operator operator){
		this.operator = operator;
	}
	
	/**
	 * run
	 */
	public void run(){
		JFrame jFrame = new JFrame(operator.getName());
		Container container = jFrame.getContentPane();
		
		container.setLayout(new GridLayout(1, 2));
	
		top = new DefaultMutableTreeNode(operator.getName());
		
		generateParams();
		generatePreconds();
		generateAdds();
		generateDels();

		JTree jTree = new JTree(top);
		JScrollPane treeView = new JScrollPane(jTree);
		
		container.add(treeView);
		
		jFrame.pack();
		jFrame.setVisible(true);
	}

	/**
	 * generate the deletions
	 *
	 */
	private void generateDels() {
		generate_GENERIC("Deletion effects", operator.getDelEffects());
	}

	/**
	 * generate the adds
	 *
	 */
	private void generateAdds() {		
		generate_GENERIC("Addition effects", operator.getAddEffects());
	}

	/**
	 * generic generator
	 * @param string
	 */
	private void generate_GENERIC(String string, Vector vector) {
		Iterator iterator = vector.iterator();

		DefaultMutableTreeNode addsNode = new DefaultMutableTreeNode(
				string);
		
		while (iterator.hasNext()) {
			Expression expression = (Expression) iterator.next();
			if(expression == null){
				System.out.println("It's null");
				return;
			}
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
				expression.toString());
			
			addsNode.add(defaultMutableTreeNode);
		}
		top.add(addsNode);
	}

	/**
	 * generate the preconditions
	 *
	 */
	private void generatePreconds() {
		generate_GENERIC("Preconditions", operator.getPreconds());
	}

	/**
	 * generate the params
	 *
	 */
	private void generateParams() {
		Vector vector = null ;
		vector = operator.getParams();
		Iterator iterator = vector.iterator();

		DefaultMutableTreeNode paramsNode = new DefaultMutableTreeNode(
		"Parameters");

		while (iterator.hasNext()) {
			Entity expression = (Entity) iterator.next();
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
					expression.toString());
		
			paramsNode.add(defaultMutableTreeNode);
		}
		top.add(paramsNode);
	}
}
