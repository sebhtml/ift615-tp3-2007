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

import java.util.Iterator;
import java.util.Vector;

import tp3.representation.Action;
import tp3.representation.Expression;

/**
 * the solver state
 * @author s
 *
 */
public class SolverState{
	private Vector facts; // Vector<Expression>
	private Vector possibleActions ; // Vector<Action>
	private PossibleActionsGenerator possibleActionsGenerator;
	private Solver solver;
	
	/**
	 * private constructor
	 *
	 */
	private SolverState(){
		
	}
	
	/**
	 * public constructor
	 * @param facts
	 * @param possibleActions
	 */
	public SolverState(Vector facts, Vector possibleActions, PossibleActionsGenerator possibleActionsGenerator, Solver solver){
		this.facts = facts;
		this.possibleActions = possibleActions;
		this.possibleActionsGenerator = possibleActionsGenerator;
		this.solver = solver;
	}

	/**
	 * get the facts
	 * @return
	 */
	public Vector getFacts() {
		return facts;
	}
	
	/**
	 * get the possible actions
	 * @return
	 */
	public Vector getPossibleActions(){
		return possibleActions;
	}

	/**
	 * apply action
	 * @param action
	 * @return
	 */
	public SolverState applyAction(Action action) {
		Vector newFacts = (Vector)facts.clone();
		Vector adds = action.addFacts();
		Vector dels = action.delFacts();
		Iterator iterator = adds.iterator();
		
		//System.out.println("action: "+action);
		//System.out.println("facts before: "+getFacts());
		
		while(iterator.hasNext()){
			newFacts.add(iterator.next());
		}
		iterator = dels.iterator();
		while(iterator.hasNext()){
			newFacts.remove(iterator.next());
		}
		
		//System.out.println("facts after: "+newFacts);
		
		Vector newPossibleActions = possibleActionsGenerator.dump(newFacts) ;
		return new SolverState(newFacts, newPossibleActions, possibleActionsGenerator, solver);
	}

	public SolverState applyActions(Vector actions){
		SolverState state = this;
		Iterator iterator = actions.iterator();
		while(iterator.hasNext()){
			Action action = (Action)iterator.next();
			if(state.getPossibleActions().contains(action)){
				state = state.applyAction(action);
			}else{
				return state;
			}
		}
		
		//System.out.println("root facts: "+getFacts());
		return state;
	}
	
	/**
	 * get the effects green
	 * @return
	 */
	public Vector getCoolEffects() {
		Vector answer = new Vector();
		
		Iterator iterator = solver.getOmegatronParser().getFileEffects().iterator();
		while(iterator.hasNext()){
			Expression exp = (Expression)iterator.next();
			if(facts.contains(exp)){
				answer.add(exp);
			}
		}
		return answer;
	}

	
}
