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

package tp3.solver;

import java.util.Vector;

import tp3.representation.Action;

/**
 * a node
 * @author s
 *
 */
public class Node  {
	private static int id;
	private Vector children ; // Vector<Node>
	private Node parent;
	private SolverState solverState;
	private int nodeId;
	private Action action;
	
	static{
		id = 1;
	}
	
	/**
	 * get the id
	 *
	 */
	private static int getId(){
		int i = id;
		id ++;
		return i;
	}
	
	
	/**
	 * get the path from root..
	 * @return
	 */
	public Vector getPath(){
		Vector path = new Vector();
		path.add(0, this);
		if(getParent() != null){
			path.addAll(0, getParent().getPath());
		}
		return path;
	}
	
	/**
	 * get the id 
	 * 
	 */
	private int getNodeId(){
		return nodeId;
	}
	
	/**
	 * private constructor
	 *
	 */
	private Node(){
		
	}
	
	/**
	 * to string
	 */
	public String toString(){
		if(parent == null){
			return "id: "+ getNodeId();
		}else{
			return "id: "+ getNodeId()+" parent id: "+ getParent().getNodeId()+" "+action.toString();
		}
	}
	
	/**
	 * get the parent
	 * @return
	 */
	private Node getParent() {
		return parent;
	}

	/**
	 * used constructor
	 * @param solverState
	 */
	public Node(SolverState solverState, Node parent, Action action){
		nodeId = Node.getId();
		this.solverState = solverState;
		this.parent = parent;
		this.action = action;
		children = new Vector();
	}
	
	/**
	 * get the state
	 * @return
	 */
	public SolverState getSolverState(){
		return solverState;
	}

	/**
	 * apply action
	 * @param action
	 * @return
	 */
	public Node applyAction(Action action) {
		SolverState newSolverState = solverState.applyAction(action);
		Node child = new Node(newSolverState, this, action);
		children.add(child);
		return child;
	}

	/**
	 * remove children
	 *
	 */
	public void removeChildren() {
		children = new Vector();
	}
}
