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
import java.util.TreeMap;
import java.util.Vector;

import tp3.representation.Action;
import tp3.representation.Entity;
import tp3.representation.Expression;
import tp3.representation.Operator;

/**
 * a class for possible actions
 * @author s
 *
 */
public class PossibleActionsGenerator {
	private Vector fileOperations;
	private Vector fileEntities;
	private Vector allPotentialActions;
	
	/**
	 * get all potential actions
	 */
	public Vector getAllPotentialActions(){
		return allPotentialActions;
	}
	
	/**
	 * private constructor
	 *
	 */
	private PossibleActionsGenerator(){
		
	}
	
	/**
	 * constructor
	 * @param fileOperations
	 * @param fileEntities
	 */
	public PossibleActionsGenerator(Vector fileOperations, Vector fileEntities){
		this.fileOperations=fileOperations;
		this.fileEntities=fileEntities;
		allPotentialActions = new Vector();
		generateActionsWithOperators();
	}

	/**
	 * generate the actions
	 *
	 */
	private void generateActionsWithOperators() {
		Iterator iterator = fileOperations.iterator();
		while(iterator.hasNext()){
			Operator operator = (Operator)iterator.next();
			generateActionsForOperator(operator);
		}
	}

	/**
	 * generate actions for operator
	 * @param operator
	 */
	private void generateActionsForOperator(Operator operator) {
		Vector params = operator.getParams();
		Vector entities = fileEntities ;
		TreeMap possibleParams = new TreeMap();
		Iterator paramsIterator = params.iterator();
		while(paramsIterator.hasNext()){
			Entity param = (Entity)paramsIterator.next();
			Vector possibleEntities = new Vector();
			Iterator entitiesIterator = entities.iterator();
			while(entitiesIterator.hasNext()){
				Entity entity = (Entity)entitiesIterator.next();
				if(param.getEntityClass().compareTo(entity.getEntityClass()) == 0){
					possibleEntities.add(entity);
				}
			}
			possibleParams.put(param, possibleEntities);
		}
		
		// params1 : a, b, c
		// params2: d, e, f
		// operator: yo
		
		// generates:
		/*
		 * yo(a,d)
		 * yo(a,e)
		 * yo(a,f)
		 * yo(b,d)
		 * yo(b,e)
		 * yo(b,f)
		 * yo(c,d)
		 * yo(c,e)
		 * yo(c,f)
		 */
		Vector sets = new Vector();
		Iterator iterator = params.iterator();
		//System.out.println("params size "+params.size());
		while(iterator.hasNext()){
			Entity param = (Entity)iterator.next();
			Vector values = (Vector)possibleParams.get(param);
			//System.out.println("param values "+values.size());
			//System.out.println("sets size in loop..: "+sets.size());
			sets.add(values);
		}
		Vector entityCartesianProduct = cartesianProduct(sets);
		//System.out.println(entityCartesianProduct.toString());
		//System.out.println("sets size before product: "+sets.size());
		//System.out.println("Cartesian size :"+ ((Vector)entityCartesianProduct).size());
		Iterator entityCartesianProductIterator = entityCartesianProduct.iterator();
		while(entityCartesianProductIterator.hasNext()){
			Vector paramsEntities = (Vector)entityCartesianProductIterator.next();
			Action action = new Action(operator, paramsEntities);
			allPotentialActions.add(action);
		}
	}
	
	/**
	 * do a cartesian product
	 * @param collection
	 * @return
	 */
	private Vector cartesianProduct(Vector collection2) {
		//System.out.println("cartesian size rec: "+ collection2.toString());
		Vector collection = (Vector)collection2.clone();
		
		if(collection.size() == 1){
			Vector result = new Vector();
			Iterator collectionIterator = ((Vector)collection.get(0)).iterator();
			while(collectionIterator.hasNext()){
				Vector row = new Vector();
				row.add(collectionIterator.next());
				result.add(row);
			}
			
			//System.out.println("result: "+ result.toString());
			return result;
		}else{
			Iterator collectionIterator = collection.iterator();
			
			Vector first = (Vector)collectionIterator.next();
			collection.remove(first);
			Vector recursiveResult = cartesianProduct(collection);
			Iterator firstIterator = first.iterator();
			//System.out.println("FIRST: "+first);
			Vector realResults = new Vector();
			while(firstIterator.hasNext()){
				Entity entity = (Entity)firstIterator.next();
				Iterator recurIterator = recursiveResult.iterator();
				while(recurIterator.hasNext()){
					Vector values2 = (Vector)recurIterator.next();
					Vector values3 = (Vector)values2.clone();
					values3.add(0, entity);
					realResults.add(values3);
				}
			}
			
			//System.out.println("result: "+ realResults.toString());
			return realResults;
		}
	}

	/**
	 * dump the possible actions with the vector
	 * @param facts
	 * @return
	 */
	public Vector dump(Vector facts) {
		Vector possibleActions = new Vector();
		Iterator allPotentialActionsIterator = allPotentialActions.iterator();
		while(allPotentialActionsIterator.hasNext()){
			Action action = (Action)allPotentialActionsIterator.next();
			if(isReachable(action, facts)){
				possibleActions.add(action);
			}
		}
		return possibleActions;
	}

	/**
	 * check if the action is reachable
	 * @param action
	 * @param facts
	 * @return
	 */
	private boolean isReachable(Action action, Vector facts) {
		Vector preconds =  action.getPreconditions();
		Iterator precondsIterator = preconds.iterator();
		while(precondsIterator.hasNext()){
			Expression precond = (Expression)precondsIterator.next();
			// XXX contains should go here
			boolean isThere = false;

			Iterator iterator = facts.iterator();
			while(iterator.hasNext()){
				Expression fact = (Expression)iterator.next();
				if(precond.equals(fact)){
					isThere = true;
					break;
				}
			}
			if(!isThere){
				return false;
			}
		}
		return true;
	}
}
