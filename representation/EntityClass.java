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
 * a class of entities
 * @author s
 *
 */
public class EntityClass implements Comparable {
	private String name;

	private static TreeMap treeMap;

	static {
		treeMap = new TreeMap();
	}

	/**
	 * private constructor
	 *
	 */
	private EntityClass() {

	}

	/**
	 * usefull constructor
	 * @param name
	 */
	private EntityClass(String name) {
		this.name = name;
	}

	/**
	 * build an object
	 * @param name
	 * @return
	 */
	public static EntityClass build(String name) {
		if (!treeMap.containsKey(name)) {
			treeMap.put(name, new EntityClass(name));
		}

		return (EntityClass) treeMap.get(name);
	}

	/**
	 * convert to string
	 */
	public String toString() {
		return name;
	}

	/**
	 * get the name
	 * @return
	 */
	public String getName() {
		return name;
	}

	public int compareTo(Object arg0) {
		return getName().compareTo((((EntityClass)arg0).getName()));
	}
}
