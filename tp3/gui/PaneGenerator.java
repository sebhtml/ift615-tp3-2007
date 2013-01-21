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

import java.awt.Color;
import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import tp3.representation.Action;
import tp3.representation.Expression;
import tp3.solver.Node;

/**
 * a pane generator
 * @author s
 *
 */
public class PaneGenerator implements	TreeCellRenderer {
	private Vector greenOne;
	private Vector redOne;
	private String selectedNode;
	
	/**
	 * generate a pane
	 * @param vector
	 * @param title
	 * @return
	 */
	public JScrollPane generatePaneEffects(Vector vector, String title, Vector facts) {
		greenOne = new Vector();
		redOne = new Vector();
		Iterator iterator = vector.iterator();

		DefaultMutableTreeNode top = new DefaultMutableTreeNode(title);
		while (iterator.hasNext()) {
			Expression expression = (Expression) iterator.next();
			String key = expression.toString();
			
			if(facts.contains(expression)){
				greenOne.add(key);
			}else{
				redOne.add(key);
			}
			
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
					key);
			

			top.add(defaultMutableTreeNode);
		}
		JTree jTree = new JTree(top);
		jTree.setCellRenderer(this);
		JScrollPane treeView = new JScrollPane();
		treeView.getViewport().add(jTree);
		return treeView;
	}
	/**
	 * generate a pane
	 * @param vector
	 * @param title
	 * @return
	 */
	public JScrollPane generatePane(Vector vector, String title) {
		Iterator iterator = vector.iterator();

		DefaultMutableTreeNode top = new DefaultMutableTreeNode(title);
		while (iterator.hasNext()) {
			Expression expression = (Expression) iterator.next();
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
					expression.toString());

			top.add(defaultMutableTreeNode);
		}
		JTree jTree = new JTree(top);
		JScrollPane treeView = new JScrollPane();
		treeView.getViewport().add(jTree);
		return treeView;
	}
	
	/**
	 * generate a pane
	 * @param vector
	 * @param title
	 * @return
	 */
	public JScrollPane generatePaneAction(Vector vector, String title, GraphActionsListener graphActionsListener, String selectedNode) {
		Iterator iterator = vector.iterator();
		this.selectedNode = selectedNode;
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(title);
		while (iterator.hasNext()) {
			Action expression = (Action) iterator.next();
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
					expression.toString());

			top.add(defaultMutableTreeNode);
		}
		JTree jTree = new JTree(top);
		jTree.addTreeSelectionListener(graphActionsListener);
		JScrollPane treeView = new JScrollPane(jTree);
		return treeView;
	}
	
	/**
	 * generate a pane for actions done
	 * @param vector
	 * @param title
	 * @return
	 */
	public JScrollPane generatePaneNodes(Vector vector, String title, ActionsGraphActionsListener actionsGraphActionsListener) {
		Iterator iterator = vector.iterator();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(title);
		while (iterator.hasNext()) {
			Node expression = (Node) iterator.next();
			
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
					expression.toString());

			top.add(defaultMutableTreeNode);
			
			
		}
		JTree jTree = new JTree(top);
		jTree.setCellRenderer(this);
		jTree.addTreeSelectionListener(actionsGraphActionsListener);
		JScrollPane treeView = new JScrollPane(jTree);
		return treeView;
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		String	labelText = (String)node.getUserObject();
		JLabel jLabel = new JLabel();
		jLabel.setText(labelText);
		
		if(greenOne != null && greenOne.contains(labelText)){
			jLabel.setForeground(Color.GREEN);
		}else if(redOne != null && redOne.contains(labelText)){
			jLabel.setForeground(Color.RED);
		}
		
		if(selectedNode != null && selectedNode.equals(labelText)){
			jLabel.setForeground(Color.BLUE);
		}
		
		return jLabel;
	}
}
