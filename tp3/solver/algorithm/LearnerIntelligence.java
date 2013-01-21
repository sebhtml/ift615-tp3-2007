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

import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import tp3.gui.BrainView;
import tp3.representation.Action;
import tp3.representation.Entity;
import tp3.representation.Expression;
import tp3.solver.SolverGraph;
import tp3.solver.SolverState;

/**
 * an intelligence
 * 
 * @author s
 * 
 */
public class LearnerIntelligence  extends Intelligence{
	private TreeMap knowledge;

	private BrainView brainView;

	private TreeMap pureKnowledge;

	private static int AMOUNT = 5;

	private static final int MAX_TRIES = 10000;

	private static final int MAX_LEARN_AMOUNT = 15;

	private Vector pureKnowledgeMerged;

	private Vector plan;

	private int p;

	private Vector actions;

	//private Date start;

	private int learns;

	private TreeMap strippedSequences;

	private boolean solutionNotFound;

	/**
	 * constructor
	 * 
	 * @param solver
	 */
	public LearnerIntelligence() {
		brainView = new BrainView();
		p = 1;
		failure = false ;
	}

	public String mergedRowsToString() {
		StringBuffer plan2 = new StringBuffer();
		plan2.append("Merged pure knowledge \n\n");
		for (int i = 0; i < pureKnowledgeMerged.size(); i++) {
			Vector action = (Vector) pureKnowledgeMerged.get(i);
			plan2.append(action.toString() + "\n");
		}
		return plan2.toString();
	}

	public String planToString() {

		if (plan == null) {
			return "";
		}
		StringBuffer plan2 = new StringBuffer();
		for (int i = 0; i < plan.size(); i++) {
			Action action = (Action) plan.get(i);
			plan2.append(action.toString() + "\n");
		}
		return plan2.toString();
	}

	public String realPlanToString() {
		if(failure){
			return "NO SOLUTION FOUND";
		}
		
		if (solutionNotFound) {
			return "";
		}

		return actions.toString();
	}

	/**
	 * now I think
	 * 
	 */
	public void thinkNow() {
		//start = new Date();
		(new Thread(this)).start();
	}

	/**
	 * run me, run me, i wanna feel your body. run me, run me now.
	 */
	public void run() {
		solutionNotFound = true;
		boolean reboot = true;
		int tryId = 0;
		learns = 0;
		learn();
		learns++;
		Vector choicesAlreadySeen = null;
		actions = null;
		while (solutionNotFound) {
			
			if(learns > MAX_LEARN_AMOUNT){
				failure = true ;
				break ;
			}
			tryId++;
			if (tryId > MAX_TRIES) {
				learn();
				learns++;
				tryId = 0;
				reboot = true;
			}
			if (reboot) {
				// System.out.println("rebooting");
				solver.getSolverGraph().reset();
				choicesAlreadySeen = new Vector();
				actions = new Vector();
				reboot = false;
			}
			if (solver.getSolverView().getEnable()) {
				break;
			}

			Vector choices = solver.getSolverGraph().getCurrentNode()
					.getSolverState().getPossibleActions();

			choices = removeUnsafeChoices(choices);
			if (!goalReached()) {
				if (choicesAlreadySeen.contains(choices) || choices.size() == 0) {
					reboot = true;

					continue;
				}

				choicesAlreadySeen.add(choices);

				Random random = new Random();
				int i = random.nextInt(choices.size());
				Action action = (Action) choices.get(i);
				actions.add(action);
				solver.applyAction(action);

			} else {
				solutionNotFound = false;
				solver.getSolverView().enable();
				brainView.showRealPlan(this);
				solver.getSolverView().updateMainPanel();
			}
		}
		
		brainView.showRealPlan(this);
	}

	/**
	 * learn now
	 * 
	 */
	private void learn() {
		knowledge = new TreeMap();
		plan = new Vector();
		pureKnowledgeMerged = new Vector();
		pureKnowledge = new TreeMap();
		strippedSequences = new TreeMap();
		actions = new Vector();
		Vector effects = solver.getOmegatronParser().getFileEffects();
		Iterator iterator = effects.iterator();
		while (iterator.hasNext()) {
			Expression effect = (Expression) iterator.next();
			Vector howTo = new Vector();
			knowledge.put(effect, howTo);
		}
		brainView.showMemories(this);
		iterator = effects.iterator();
		while (iterator.hasNext()) {
			Expression effect = (Expression) iterator.next();
			learnEffect(effect);
		}

		purifyKnowledge();

		brainView.showPureKnowledge(this);
		pureMerge();
		brainView.showPureMerge(this);
		setPlan();
		brainView.showPlan(this);
	}

	private void setPlan() {
		Iterator it = pureKnowledgeMerged.iterator();
		while (it.hasNext()) {
			Vector sequence = (Vector) it.next();
			for (int i = 0; i < sequence.size(); i++) {
				Action action = (Action) sequence.get(i);
				addToPlan(action, i, sequence, plan);
				// System.out.println("plan "+plan);
			}
		}
	}

	private void addToPlan(Action action, int i, Vector sequence, Vector plan2) {
		// System.out.println("plan2: "+plan2);
		// System.out.println("Adding "+action);
		if (plan2.contains(action)) {
			// System.out.println("Already there..");
			return;
		}
		plan2.add(action);
		return;
		/*
		 * if(plan2.size() == 0){ plan2.add(0, action); return ; } for(int j = 0 ;
		 * j < plan2.size(); j++){ if(canInsertHere(plan2, j, action, sequence,
		 * i)){ plan2.add(j, action); break; } }
		 */
	}

	private void pureMerge() {
		pureKnowledgeMerged = new Vector();
		Iterator it = pureKnowledge.values().iterator();
		while (it.hasNext()) {
			pureKnowledgeMerged.add(it.next());
		}

		Vector entities = (Vector) solver.getOmegatronParser()
				.getFileEntities().clone();
		Vector operators = solver.getOmegatronParser().getFileOperations();
		putEntities(pureKnowledgeMerged, entities, operators);
	}

	private void putEntities(Vector pureKnowledgeMerged2, Vector entities,
			Vector operators) {
		if (hasGenericVariable(pureKnowledgeMerged2)) {
			setVariable(pureKnowledgeMerged2, entities, operators);
		}
	}

	/**
	 * the disguting method...
	 * 
	 * @param pureKnowledgeMerged2
	 */
	private void useSimilarities(Vector pureKnowledgeMerged) {
		for (int i = 0; i < pureKnowledgeMerged.size(); i++) {
			Vector actions = ((Vector) pureKnowledgeMerged.get(i));
			for (int j = 0; j < actions.size(); j++) {
				Action action = (Action) ((Vector) pureKnowledgeMerged.get(i))
						.get(j);
				useSimilarities(action, pureKnowledgeMerged, actions);
			}
		}
	}

	private void useSimilarities(Action action, Vector pureKnowledgeMerged2,
			Vector subA) {
		if (action.hasGenericVariable()) {
			for (int ii = 0; ii < pureKnowledgeMerged2.size(); ii++) {
				Vector subAA = ((Vector) pureKnowledgeMerged2.get(ii));
				// System.out.println("subAA "+subAA);
				for (int jj = 0; jj < subAA.size(); jj++) {
					Action action2 = (Action) subAA.get(jj);
					useSimilarities(action, action2, subAA, subA);
				}
			}
		}
	}

	private void useSimilarities(Action action, Action action2, Vector subAA,
			Vector subA) {
		if (!action2.hasGenericVariable()) {
			TreeMap mapping = action.checkSimilarity(action2);
			if (mapping.size() != 0) {
				// System.out.println("Got mapping.");
				for (int jjj = 0; jjj < subA.size(); jjj++) {
					Action action3 = (Action) ((Vector) subA).get(jjj);
					// System.out.println("Mapping on "+action3);
					if (action3.hasGenericVariable()) {
						action3.applyMap(mapping);
					}
				}
			}
		}
	}

	private boolean hasGenericVariable(Vector pureKnowledgeMerged2) {
		for (int i = 0; i < pureKnowledgeMerged2.size(); i++) {
			Vector actions = (Vector) pureKnowledgeMerged2.get(i);
			for (int j = 0; j < actions.size(); j++) {
				Action action = (Action) (((Action) actions.get(j)).clone());
				if (action.hasGenericVariable()) {
					return true;
				}
			}
		}

		return false;
	}

	private void setVariable(Vector pureKnowledgeMerged2, Vector entities,
			Vector operators) {
		for (int i = 0; i < pureKnowledgeMerged2.size(); i++) {
			Vector actions = (Vector) pureKnowledgeMerged2.get(i);
			TreeMap mapping = new TreeMap();
			for (int j = 0; j < actions.size(); j++) {
				Action action = (Action) (((Action) actions.get(j)).clone());

				mapping = action.updateGenerics(entities);
				if(mapping.size() > 0){
					applyMappingToSequence(actions, mapping);
				}
			}
			
			useSimilarities(pureKnowledgeMerged2);
		}

	}

	private void applyMappingToSequence(Vector actions, TreeMap mapping) {
		for(int i = 0 ;i < actions.size();i++){
			Action action = ((Action)actions.get(i)) ;
			//System.out.println("applying mapping "+mapping+" to "+action);
			action.applyMap(mapping);
		}
	}

	private void purifyKnowledge() {
		pureKnowledge = new TreeMap();
		strippedSequences = new TreeMap();
		Vector effects = solver.getOmegatronParser().getFileEffects();
		Iterator iterator = effects.iterator();
		while (iterator.hasNext()) {
			Expression effect = (Expression) iterator.next();
			Vector sequences = (Vector) knowledge.get(effect);
			Vector actions = simplify(effect, sequences, solver
					.getSolverGraph());
			pureKnowledge.put(effect, actions);
		}
	}

	private Vector simplify(Expression effect, Vector sequences,
			SolverGraph solverGraph) {
		// System.out.println("-->"+effect);
		Iterator iterator = sequences.iterator();
		Vector strippedSequences2 = new Vector();
		int i = 0;

		while (iterator.hasNext()) {
			Vector actionsSequence = (Vector) iterator.next();
			Vector simpleSequence = trim(actionsSequence, effect, solverGraph
					.getRoot().getSolverState());

			strippedSequences2.add(i, simpleSequence);
			brainView.showStrippedSequences(this);
			i++;
		}
		strippedSequences.put(effect, strippedSequences2);
		brainView.showStrippedSequences(this);
		return merge(strippedSequences2);
	}

	private Vector trim(Vector actionsSequence, Expression effect,
			SolverState solverState) {
		for (int i = 0; i < actionsSequence.size(); i++) {
			Vector test = (Vector) actionsSequence.clone();
			test.remove(i);
			SolverState newSolverState = solverState.applyActions(test);
			if (newSolverState.getFacts().contains(effect)) {
				return trim(test, effect, solverState);
			}
		}
		return actionsSequence;
	}

	private Vector merge(Vector sequences) {
		// System.out.println(sequences);
		Vector main = (Vector) sequences.firstElement();
		Iterator iterator = sequences.iterator();
		while (iterator.hasNext()) {
			Vector actions = (Vector) iterator.next();
			main = merge(main, actions);
		}
		return main;
	}

	private Vector merge(Vector main, Vector actions) {
		TreeMap treeMap = new TreeMap();
		Vector ret = new Vector();
		for (int i = 0; i < main.size(); i++) {
			Action action = (Action) main.get(i);
			for (int j = 0; j < action.getEntities().size(); j++) {
				Entity entity = (Entity) action.getEntities().get(j);
				String key = entity.getIdentifier();
				if (!treeMap.containsKey(key)) {
					String genericName = "var" + p;
					p++;
					treeMap.put(key, genericName);
				}
			}
		}

		for (int u = 0; u < main.size(); u++) {
			Action act1 = (Action) main.get(u);
			Iterator it2 = actions.iterator();
			Action newAction = ((Action) act1.clone());
			while (it2.hasNext()) {
				Action act2 = (Action) it2.next();
				// System.out.println("merging: "+newAction+" using "+act2);
				newAction.merge(act2, treeMap);
				// System.out.println("Result : "+newAction);
			}
			ret.add(newAction);
		}
		return ret;
	}

	/**
	 * learn an effect
	 * 
	 * @param effect
	 */
	private void learnEffect(Expression effect) {
		Vector howTo = (Vector) knowledge.get(effect);
		for (int i = 0; i < AMOUNT; i++) {
			Vector actions = achieveEffect(effect);
			// System.out.println("TO achieve it: "+ effect+" "+actions);
			howTo.add(actions);
			brainView.showMemories(this);
		}
	}

	/**
	 * achieve effect
	 * 
	 * @return
	 */
	private Vector achieveEffect(Expression effect) {
		boolean reboot = true;
		boolean gotLoop = true;
		Vector choicesAlreadySeen = null;
		Vector actions = null;
		while (gotLoop) {
			if (reboot) {
				// System.out.println("rebooting");
				solver.getSolverGraph().reset();
				choicesAlreadySeen = new Vector();
				actions = new Vector();
				reboot = false;
			}
			if (solver.getSolverView().getEnable()) {
				break;
			}

			Vector choices = solver.getSolverGraph().getCurrentNode()
					.getSolverState().getPossibleActions();

			if (!goalReached(effect)) {
				if (choicesAlreadySeen.contains(choices) || choices.size() == 0) {
					reboot = true;

					continue;
				}

				choicesAlreadySeen.add(choices);

				Random random = new Random();
				int i = random.nextInt(choices.size());
				Action action = (Action) choices.get(i);
				solver.applyAction(action);
				actions.add(action);
			} else {
				// System.out.println("got solution! "+actions);
				gotLoop = false;
				// solver.getSolverView().enable();
			}
		}
		return actions;
	}

	private boolean goalReached(Expression effect) {
		Vector facts = solver.getSolverGraph().getCurrentNode()
				.getSolverState().getCoolEffects();
		return facts.contains(effect);
	}

	/**
	 * remove unsafe choices
	 * 
	 * @param choices
	 */
	private Vector removeUnsafeChoices(Vector choices) {
		// System.out.println("Choices: "+ choices);
		// System.out.println("plan: "+ plan);
		Vector v = new Vector();
		for (int i = 0; i < choices.size(); i++) {
			Action a = ((Action) choices.get(i));
			if (plan.contains(a)) {
				v.add(choices.get(i));
			}
		}
		return v;
	}

	private boolean goalReached() {
		Vector effects = solver.getOmegatronParser().getFileEffects();
		Vector facts = solver.getSolverGraph().getCurrentNode()
				.getSolverState().getCoolEffects();

		return effects.size() == facts.size();
	}

	public String memoriesToString() {
		StringBuffer buffer = new StringBuffer();
		Vector effects = solver.getOmegatronParser().getFileEffects();
		Iterator iterator = effects.iterator();
		while (iterator.hasNext()) {
			Expression effect = (Expression) iterator.next();

			if (knowledge.containsKey(effect)) {
				Vector tries = (Vector) knowledge.get(effect);
				buffer.append("< " + effect + " >" + "" + tries.size() + "\n");
				Iterator tryIterator = tries.iterator();
				while (tryIterator.hasNext()) {
					Vector actions = (Vector) tryIterator.next();
					buffer.append("    " + actions + "\n");
				}
			}
		}
		return buffer.toString();
	}

	public String pureKnowledgeString() {
		StringBuffer buffer = new StringBuffer();
		Vector effects = solver.getOmegatronParser().getFileEffects();
		Iterator iterator = effects.iterator();
		while (iterator.hasNext()) {
			Expression effect = (Expression) iterator.next();

			if (pureKnowledge.containsKey(effect)) {
				Vector actions = (Vector) pureKnowledge.get(effect);
				buffer.append("< " + effect + " >" + "" + "\n");

				buffer.append("    " + actions + "\n");
			}
		}
		return buffer.toString();
	}

	public String showStrippedSequences() {
		StringBuffer buffer = new StringBuffer();
		Vector effects = solver.getOmegatronParser().getFileEffects();
		Iterator iterator = effects.iterator();
		while (iterator.hasNext()) {
			Expression effect = (Expression) iterator.next();

			if (strippedSequences.containsKey(effect)) {
				Vector tries = (Vector) strippedSequences.get(effect);
				buffer.append("< " + effect + " >" + "" + tries.size() + "\n");
				Iterator tryIterator = tries.iterator();
				while (tryIterator.hasNext()) {
					Vector actions = (Vector) tryIterator.next();
					buffer.append("    " + actions + "\n");
				}
			}
		}
		return buffer.toString();
	}
}
