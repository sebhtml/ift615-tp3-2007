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

import tp3.gui.Solution;
import tp3.gui.SolverView;
import tp3.parsetime.OmegatronParser;
import tp3.representation.Action;

/**
 * the problem solver
 * 
 * @author s
 * 
 */
public class Solver {
	private SolverGraph solverGraph;
	private OmegatronParser omegatronParser;
	private PossibleActionsGenerator possibleActionsGenerator;
	private SolverView solverView;
	private Solution solution;
	
	/**
	 * get the view
	 */
	
	public SolverView getSolverView(){
		return solverView;
	}
	
	/**
	 * get the actions generator
	 */
	public PossibleActionsGenerator  getPossibleActionsGenerator(){
		return possibleActionsGenerator;
	}
	/**
	 * a constructor
	 * @param operations
	 * @param facts
	 */
	public Solver(Solution solution) {
		this.solution = solution;
		omegatronParser = new OmegatronParser(this);
	}

	/**
	 * go go go pokemons
	 *
	 */
	public void go() {
		solverView = new SolverView(this);
		omegatronParser.parse();
		possibleActionsGenerator = new PossibleActionsGenerator(getOmegatronParser().getFileOperations(), getOmegatronParser().getFileEntities());
		
		Vector possibleActions = possibleActionsGenerator.dump(getOmegatronParser().getFilePreconditions()) ;
		SolverState firstState = new SolverState((Vector)getOmegatronParser().getFilePreconditions(), possibleActions, possibleActionsGenerator, this);
		solverGraph = new SolverGraph(new Node(firstState, null, null));
		solverView.show();
	}

	public Solution getSolution(){
		return solution;
	}

	/**
	 * get the parser (Transformer style.)
	 * @return
	 */
	public OmegatronParser getOmegatronParser() {
		return omegatronParser;
	}
	
	/**
	 * get the facts
	 */
	public Vector getSolverFacts(){
		return getSolverGraph().getCurrentNode().getSolverState().getFacts();
	}
	
	/**
	 * get the solver graph
	 * @return
	 */
	public SolverGraph getSolverGraph(){
		return solverGraph;
	}

	/**
	 * get the possible actions
	 * @return
	 */
	public Vector getPossibleActs() {
		return getSolverGraph().getCurrentNode().getSolverState().getPossibleActions();
	}
	
	/**
	 * apply action
	 */
	public void applyAction(Action action){
		solverGraph.applyAction(action);
	}
}
