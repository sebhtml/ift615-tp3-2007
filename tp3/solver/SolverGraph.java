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
 * a graph
 * @author s
 *
 */
public class SolverGraph {
	private Node root;
	private Node currentNode;
	private Vector nodes;
	
	/**
	 * reset it..
	 */
	public void reset(){
		currentNode = root;
		nodes = new Vector();
		nodes.add(currentNode);
		currentNode.removeChildren();
	}
	
	/**
	 * private constructor
	 *
	 */
	private SolverGraph(){
	}
	
	/**
	 * get the nodes
	 */
	public Vector getNodes(){
		return nodes;
	}
	
	public Node getRoot(){
		return root;
	}
	
	/**
	 * nice constructor
	 * @param root
	 */
	public SolverGraph(Node root){
		nodes = new Vector();
		this.root = root;
		currentNode = root;
		nodes.add(currentNode);
	}
	
	/**
	 * set the current
	 */
	public void setCurrentNode(Node node){
		currentNode = node ;
	}
	
	/**
	 * get the current node
	 * @return
	 */
	public Node getCurrentNode() {
		return currentNode;
	}
	
	/**
	 * apply action
	 */
	public void applyAction(Action action){
		Node newNode = currentNode.applyAction(action);
		currentNode = newNode;
		nodes.add(currentNode);
	}
}

