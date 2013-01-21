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


package tp3.parsetime;

import java.util.Vector;

import tp3.representation.Entity;
import tp3.representation.EntityClass;
import tp3.representation.Expression;
import tp3.representation.Operator;
import tp3.representation.Predicat;

/**
 * the parser for operator
 * @author s
 *
 */
public class OperatorParser {
	private String name;
	private String params;
	private String preconds;
	private String effects;
	
	/**
	 * private constructor
	 *
	 */
	private OperatorParser(){
		
	}
	
	/**
	 * public constructor
	 * @param name
	 * @param params
	 * @param preconds
	 * @param effects
	 */
	public OperatorParser(String name, String params, String preconds, String effects){
		this.name = name;
		this.params = params;
		this.preconds = preconds;
		this.effects = effects;
	}

	/**
	 * parse an operator
	 * @return
	 */
	public Operator parse() {
		Vector paramsVector ;
		Vector precondsVector ;
		Vector addEffectsVector ;
		Vector delEffectsVector ;
		
		name = name.replaceAll(" ", "");

		paramsVector = parseParams();
		precondsVector = parse_precondsVector();
		addEffectsVector = parse_addEffectsVector();
		delEffectsVector = parse_delEffectsVector();
		
		Operator operator = new Operator(name, paramsVector, precondsVector, addEffectsVector, delEffectsVector);
		return operator;
	}

	/**
	 * parse the deletions
	 * @return
	 */
	private Vector parse_delEffectsVector() {
		return genericParser(effects, true);
	}

	/**
	 * parse the additions
	 * @return
	 */
	private Vector parse_addEffectsVector() {
		return genericParser(effects, false);
	}

	/**
	 * parse the preconditions
	 * @return
	 */
	private Vector parse_precondsVector() {
		return genericParser(preconds, false);
	}

	/**
	 * a generic parser for Expression lists
	 * @param stuff
	 * @return
	 */
	private Vector genericParser(String stuff, boolean del){
		Vector vector = new Vector();
		
		// (at <rocket> <place>)  (at <object> <place>))
		stuff = stuff.trim();
		stuff = stuff.replace('(', '!');
		stuff = stuff.replace(')', '!');
		
		//System.out.println(preconds);

		String[] tokens = stuff.split("![ ]{1,}!");
		//System.out.println(tokens.length);
		for(int i = 0 ; i < tokens.length; i++){
			tokens[i] = tokens[i].replaceAll("!", "");
			
			String[] internalTokens = tokens[i].split("[ ]{1,}");
			//System.out.println(internalTokens[0]);
			int predicatIndex = 0;
			if(internalTokens[0].equals("del")){
				predicatIndex++;
			}
			Predicat predicat = Predicat.build(internalTokens[predicatIndex]);
			Vector entities = new Vector();
			for(int j = predicatIndex+1 ; j < internalTokens.length; j++){
				String key = internalTokens[j];
				Entity entity = Entity.get(key);
				if(entity == null){
					System.out.println(key+" is null");
					return null;
				}
				entities.add(entity);
			}
			Expression expression = new Expression(predicat, entities);
			if(del && predicatIndex == 1 || !del && predicatIndex == 0){
				vector.add(expression);
			}
		}
		return vector;	
	}
	
	/**
	 * parse the params
	 * @return
	 */
	private Vector parseParams() {
		//System.out.println(params);
		//(<object> CARGO) (<rocket> ROCKET) (<place> PLACE))
		Vector vector = new Vector();
		params = params.trim();
		params = params.replace('(', '!');
		params = params.replace(')', '!');
		//		 System.out.println(params);
		String[] tokens = params.split("! !");
		//		 System.out.println(tokens.length);
		// (<object> CARGO
		// <rocket> ROCKET
		// <place> PLACE))
		for(int i = 0 ; i < tokens.length; i++){
			tokens[i] = tokens[i].replaceAll("!", "");
			tokens[i] = tokens[i].replaceAll("!", "");
			String[] internalTokens = tokens[i].split(" ");
			String identifier = internalTokens[0];
			
			String className = internalTokens[1];
			Entity entity = Entity.build(identifier, EntityClass.build(className));
			//			 System.out.println(entity);
			vector.add(entity);
		}
		return vector;
	}
}
