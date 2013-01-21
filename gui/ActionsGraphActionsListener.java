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

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import tp3.solver.Node;
import tp3.solver.Solver;

/**
 * listener for actions
 * @author s
 *
 */
public class ActionsGraphActionsListener implements TreeSelectionListener {
	private TreeMap mapping;
	private Solver solver;
	
	public ActionsGraphActionsListener(Solver solver){
		this.solver = solver;
		generateMapping();
	}
	
	public void generateMapping() {
		mapping = new TreeMap();
		Vector nodes = solver.getSolverGraph().getNodes();
		Iterator iterator = nodes.iterator();
		while(iterator.hasNext()){
			Node node = (Node)iterator.next();
			mapping.put(node.toString(), node);
		}
	}

	/**
	 * detect tree Event..
	 */
	public void valueChanged(TreeSelectionEvent e) {
		if(solver.getSolverView().getEnable()== false){
			return;
		}
		
		String key = (String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject();
		if(mapping.containsKey(key)){
			Node node = (Node)mapping.get(key);
			solver.getSolverGraph().setCurrentNode(node);
			solver.getSolverView().updateMainPanel();
		}
	}
}
