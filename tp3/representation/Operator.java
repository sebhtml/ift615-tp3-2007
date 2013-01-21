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

import java.util.Vector;

/**
 * an operator
 * @author s
 *
 */
public class Operator {
	private String name;
	private Vector params; 			// Vector<Entity>
	private Vector preconds; 		// Vector<Expression>
	private Vector addEffects;		// Vector<Expression>
	private Vector delEffects;		// Vector<Expression>
	
	/**
	 * convert it to string
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * public constructor
	 */
	public Operator(String name, Vector params, Vector preconds, Vector addEffects, Vector delEffects){
		this.name = name;
		this.params = params;
		this.preconds = preconds;
		this.addEffects = addEffects;
		this.delEffects = delEffects;
	}
	
	/**
	 * private constructor
	 */
	private Operator(){
		
	}

	/**
	 * geet the addition effects
	 * @return
	 */
	public Vector getAddEffects() {
		return addEffects;
	}

	/**
	 * get the deletion effects
	 * @return
	 */
	public Vector getDelEffects() {
		return delEffects;
	}

	/**
	 * get the parameters
	 * @return
	 */
	public Vector getParams() {
		return params;
	}

	/**
	 * get the preconditions
	 * @return
	 */
	public Vector getPreconds() {
		return preconds;
	}
}
