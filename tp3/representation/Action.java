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



package tp3.representation;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

/**
 * a class for an action
 * 
 * @author s
 * 
 */
/**
 * a class for actions
 */
public class Action implements Comparable {
	private Operator operator;

	private Vector entities; // Vector<Entity>
	private TreeMap variablesMap ;
	
	public Vector getEntities(){
		return entities;
	}
	
	public Object clone(){
		return new Action(operator, (Vector)entities.clone());
	}
	
	public int compareTo(Object object){
		return toString().compareTo(((Action)object).toString());
	}
	
	/**
	 * get the preconditions
	 */
	public Vector getPreconditions(){
		return generateExpressions(operator.getPreconds());
	}
	
	/**
	 * equals method
	 */
	public boolean equals(Object entity){
		return operator.equals(((Action)entity).operator) && entities.equals(((Action)entity).entities);
	}
	
	/**
	 * generate the expressions
	 */
	private Vector generateExpressions(Vector preconditions){
		Vector preconditionsForAction = new Vector();
		
		Iterator preconditionsIterator = preconditions.iterator();
		while(preconditionsIterator.hasNext()){
			Expression expression = (Expression)preconditionsIterator.next();
			preconditionsForAction.add(expression.applyMapping(variablesMap));
		}
		
		return preconditionsForAction;
	}
	
	/**
	 * private constructor
	 *
	 */
	private Action(){
		
	}
	
	/**
	 * public constructor
	 */
	public Action(Operator operator, Vector entities){
		this.operator = operator;
		this.entities = entities;
		generateMapping();
	}
	
	/**
	 * generate the mapping
	 *
	 */
	private void generateMapping() {
		variablesMap = new TreeMap();
		Vector params = operator.getParams();
		Iterator paramsIterator = params.iterator();
		Iterator entitiesIterator = entities.iterator();
		while(paramsIterator.hasNext()){
			variablesMap.put(paramsIterator.next(), entitiesIterator.next());
		}
	}

	/**
	 * convert this to string
	 */
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(operator.getName());
		stringBuffer.append("(");
		Iterator iterator = entities.iterator();
		
		while(iterator.hasNext()){
			Entity entity = (Entity)iterator.next();
			stringBuffer.append(entity.getIdentifier());
			if(iterator.hasNext()){
				stringBuffer.append(", ");
			}
		}
		stringBuffer.append(")");
		return stringBuffer.toString();
	}

	/**
	 * the additions facts
	 * @return
	 */
	public Vector addFacts() {
		return generateExpressions(operator.getAddEffects());
	}

	/**
	 * the deletion facts
	 * @return
	 */
	public Vector delFacts() {
		return generateExpressions(operator.getDelEffects());
	}

	public void merge(Action act2, TreeMap map) {
		if(operator.equals(act2.operator)){
			Iterator it1 = entities.iterator();
			Iterator it2 = act2.entities.iterator();
			int i = 0 ;
			while(it1.hasNext()){
				Entity e1 = (Entity) it1.next();
				Entity e2 = (Entity) it2.next();
				entities.set(i, e1.merge(e2, map));
				i ++;
			}
		}
	}

	public boolean wrap(Action a) {
		if(operator == a.operator){
			for(int i = 0 ; i < entities.size(); i++){
				Entity b = (Entity)entities.get(i);
				Entity c = (Entity)a.entities.get(i);
				if(!b.wrap(c)){
					return false ;
				}
			}
		}
		return true;
	}

	public TreeMap updateGenerics(Vector entities2) {
		TreeMap mapping = new TreeMap() ;
		for(int i = 0 ; i < entities.size(); i++){
			Entity entity = (Entity) entities.get(i);
			mapping = entity.updateGeneric(entities2) ;
			if(mapping.size() > 0){
				return mapping;
			}
		}
		return mapping;
	}

	public boolean hasGenericVariable() {
		for(int i = 0 ; i < entities.size(); i++){
			Entity entity = (Entity) entities.get(i);
			if(entity.getGen()){
				return true;
			}
		}
		return false;
	}

	public TreeMap checkSimilarity(Action action2) {
		TreeMap map = new TreeMap();
		if(operator.equals(action2.operator)){
			int indexOfGeneric = -1 ;
			for(int i = 0 ; i < entities.size(); i++){
				Entity entity = (Entity) entities.get(i);
				if(entity.getGen()){
					indexOfGeneric = i ;
				}
			}
			
			for(int i = 0 ; i < entities.size(); i++){
				Entity entity = (Entity) entities.get(i);
				Entity entity2 = (Entity) action2.entities.get(i);
				if(i != indexOfGeneric){
					if(!entity.equals(entity2)){
						return map;
					}
				}
			}
			if(indexOfGeneric != -1){
				//System.out.println(entities.get(indexOfGeneric)+" -> "+action2.entities.get(indexOfGeneric));
				map.put(entities.get(indexOfGeneric), action2.entities.get(indexOfGeneric));
				entities.set(indexOfGeneric, action2.entities.get(indexOfGeneric));
			}
		}
		return map;
	}

	public void applyMap(TreeMap mapping) {
		//System.out.println("before: "+entities);
		for(int i = 0 ; i < entities.size(); i++){
			Entity en = (Entity) entities.get(i);
			
			if(mapping.containsKey(en)){
				Entity newE = (Entity) mapping.get(en);
				entities.set(i, newE);
			}
		}
		//System.out.println("after"+entities);
	}
}
