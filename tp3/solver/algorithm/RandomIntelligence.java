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



package tp3.solver.algorithm;

import java.util.TreeMap;
import java.util.Vector;

import tp3.representation.Action;
import tp3.solver.Node;

public class RandomIntelligence extends Intelligence {
	private Vector coloredNodes ;
	private TreeMap parent ;
	private Node finalState;
	private TreeMap actions ;
	private Vector actionsDone ;
	
	public RandomIntelligence(){
		coloredNodes = new Vector();
		parent = new TreeMap();
		actions = new TreeMap();
		actionsDone = new Vector();
	}
	
	public void run() {
		solver.getSolverGraph().reset();
		randomSearch(solver.getSolverGraph().getCurrentNode());
		System.out.println("Final: "+finalState);
	}

	private boolean randomSearch(Node node) {
		System.out.println("Searching in "+node);
		if(coloredNodes.contains(node)){
			return false;
		}
		
		coloredNodes.add(node);
		
		if(goalReached(node)){
			finalState = node ;
			return true ;
		}
		
		Vector choices = node.getSolverState().getPossibleActions();
		System.out.println("Starting for "+node);
		for(int i = 0 ; i < choices.size(); i++){
			Action action = (Action)choices.get(i);
			if(actionsDone.contains(action)){
				continue;
			}
			Node newNode = node.applyAction(action);
			actionsDone.add(action);
			parent.put(newNode, node);
			actions.put(newNode, action);
			
			if(randomSearch(newNode)){
				return true ;
			}
		}
		System.out.println("Ending for "+node);
		return false ;
	}
	
	private boolean goalReached(Node node) {
		Vector effects = solver.getOmegatronParser().getFileEffects();
		Vector facts = node.getSolverState().getCoolEffects();

		return effects.size() == facts.size();
	}

	public void thinkNow() {
		(new Thread(this)).start();
	}

}
