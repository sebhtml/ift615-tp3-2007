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

/**
 * a predicat
 * @author s
 *
 */
public class Predicat {
	private String name;
	private static TreeMap treeMap;
	
	static{
		treeMap = new TreeMap();
	}
	
	/**
	 * get its name
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * constructor
	 *
	 */
	private Predicat(){
		
	}
	
	/**
	 * another constructor
	 * @param name
	 */
	private Predicat(String name){
		this.name = name ;
	}
	
	/**
	 * build the object
	 * @param name
	 * @return
	 */
	public static Predicat build(String name){
		if(!treeMap.containsKey(name)){
			treeMap.put(name, new Predicat(name));
		}
		
		return (Predicat)treeMap.get(name);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Predicat) {
			return this.getName().equals(((Predicat)obj).getName());
		}
		return false;
	}
}

