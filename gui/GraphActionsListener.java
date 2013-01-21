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

import tp3.representation.Action;
import tp3.solver.Solver;

/**
 * an action listener for actions
 * @author s
 *
 */
public class GraphActionsListener implements TreeSelectionListener{
	private TreeMap mapping;
	private Solver solver;
	
	public int mapSize(){
		return mapping.size();
	}
	
	/**
	 * constructor
	 * @param solver
	 */
	public GraphActionsListener(Solver solver){
		this.solver = solver;
		generateMapping();
	}
	
	/**
	 * generate the mapping
	 *
	 */
	private void generateMapping() {
		mapping = new TreeMap();
		Vector allActions = solver.getPossibleActionsGenerator().getAllPotentialActions();
		Iterator allIterator = allActions.iterator();
		while(allIterator.hasNext()){
			Action action = (Action)allIterator.next();
			mapping.put(action.toString(), action);
		}
	}

	/**
	 * detect tree Event..
	 */
	public void valueChanged(TreeSelectionEvent e) {
		String key = ((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject().toString();
		if(mapping.containsKey(key)){
			Action action = (Action)mapping.get(key);
			solver.applyAction(action);
			solver.getSolverView().updateMainPanel();
		}
	}
}
