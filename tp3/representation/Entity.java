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

import java.util.TreeMap;
import java.util.Vector;

/**
 * an entity
 * @author s
 *
 */
public class Entity implements Comparable{
	private EntityClass entityClass;

	private String identifier;
	private boolean generic ;
	
	private static TreeMap treeMap;

	static {
		treeMap = new TreeMap();
	}

	/**
	 * equals method
	 */
	public boolean equals(Object entity){
		return getIdentifier().equals(((Entity)entity).getIdentifier());
	}
	
	/**
	 * get the class
	 * @return
	 */
	public EntityClass getEntityClass(){
		return entityClass;
	}
	
	/**
	 * To string conversion
	 */
	public String toString() {
		return entityClass.getName() + " " + identifier;
	}

	/**
	 * get the identifier
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * a private useless constructor
	 *
	 */
	private Entity() {

	}

	/**
	 * a private usefull constructor
	 * @param identifier
	 * @param entityClass
	 */
	private Entity(String identifier, EntityClass entityClass) {
		this.identifier = identifier;
		this.entityClass = entityClass;
		generic = false ;
	}

	private void setGen(){
		generic = true ;
	}
	
	public boolean getGen(){
		return generic;
	}
	
	/**
	 * build something
	 * @param identifier
	 * @param entityClass
	 * @return
	 */
	public static Entity build(String identifier, EntityClass entityClass) {
		if (!treeMap.containsKey(identifier)) {
			treeMap.put(identifier, new Entity(identifier, entityClass));
		}

		return get(identifier);
	}

	/**
	 * get the object
	 * @param identifier
	 * @return
	 */
	public static Entity get(String identifier) {
		return (Entity) treeMap.get(identifier);
	}

	public int compareTo(Object arg0) {
		return getIdentifier().compareTo(((Entity)arg0).getIdentifier());
	}

	public Entity merge(Entity e2, TreeMap map) {
		if(identifier == e2.identifier || generic){
			return this;
		}
		Entity b = new Entity("<"+(String)map.get(getIdentifier())+">", entityClass) ; 
		b.setGen();
		return b;
	}

	public boolean wrap(Entity c) {
		if(entityClass.equals(c.entityClass)){
			return generic;
		}
		return false ;
	}

	public TreeMap updateGeneric(Vector entities) {
		TreeMap mapping = new TreeMap();
		if(!generic){
			return mapping;
		}
		
		for(int i = 0 ; i < entities.size(); i++){
			Entity entity = (Entity) entities.get(i);
			if(entity.getEntityClass().equals(getEntityClass())){
				generic = false;
				
				Entity old = new Entity(getIdentifier(), getEntityClass());
				old.setGen();
				mapping.put(old, entity);
				identifier = entity.getIdentifier();
				//System.out.println("b");
				//System.out.println(entities);
				entities.remove(entity);
				//System.out.println(entities);
				//System.out.println("Entity Replacing "+mapping);
				return mapping;
			}
		}
		return mapping;
	}
}
