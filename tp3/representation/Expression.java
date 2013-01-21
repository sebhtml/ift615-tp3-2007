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
 * an expression
 * @author s
 *
 */
public class Expression implements Comparable {
	private Predicat predicat;
	private Vector entities ; // Vector<Entity>
	
	public int compareTo(Object obj){
		return toString().compareTo(((Expression)obj).toString())	;
	}

	/**
	 * the used constructor
	 * @param predicat
	 * @param entities
	 */
	public Expression(Predicat predicat, Vector entities){
		this.predicat = predicat;
		this.entities = entities;
	}
	
	/**
	 * map something
	 * @param mapping
	 * @return
	 */
	public Expression applyMapping(TreeMap mapping){
		Vector newEntities = new Vector();
		Iterator entitiesIterator = entities.iterator();
		while(entitiesIterator.hasNext()){
			newEntities.add(mapping.get(entitiesIterator.next()));
		}
		return new Expression(predicat, newEntities);
	}
	
	/**
	 * conversion to string
	 */
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(predicat.getName());
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
	 * test the equality
	 */
	public boolean equals(Object object){
		if(!getPredicat().equals(((Expression)object).getPredicat())){
			return false;
		} if(!getEntities().equals(((Expression)object).getEntities())){
			return false ;
		}
		return true;
	}

	/**
	 * get the entities
	 * @return
	 */
	private Vector getEntities() {
		return entities;
	}

	private Predicat getPredicat() {
		return predicat;
	}
}
