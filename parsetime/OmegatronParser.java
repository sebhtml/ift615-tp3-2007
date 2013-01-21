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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import tp3.representation.Entity;
import tp3.representation.EntityClass;
import tp3.representation.Expression;
import tp3.representation.Predicat;
import tp3.solver.Solver;

/**
 * omegatron parser, Transformers style
 * 
 * @author s
 * 
 */
public class OmegatronParser {
	private String factsFile;

	private String operatorsFile;

	private Vector fileEntities; // Vector<Entity>

	private Vector filePreconditions; // Vector<Expression>

	private Vector fileEffects; // Vector<Expression>

	private Vector fileOperations; // Vector<Operator>

	private static int ENTITIES = 0;

	private static int PRECONDS = 1;

	private static int EFFECTS = 2;
	
	/**
	 * constructor
	 * @param solver
	 */
	public OmegatronParser(Solver solver) {
		factsFile = solver.getSolution().getFactsFile();
		operatorsFile = solver.getSolution().getOperatorsFile();
		fileEntities = new Vector();
		filePreconditions = new Vector();
		fileEffects = new Vector();
		fileOperations = new Vector();
	}

	/**
	 * parse the things
	 *
	 */
	public void parse() {
		//System.out.println("Parsing the Hell now..");
		parseFactsFile();
		parseOperations();
	}

	/**
	 * parse the operations
	 *
	 */
	private void parseOperations() {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(getOperatorsFile());
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String line = null;
		try {
			line = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String name = null;
		String params = null;
		String preconds = null;
		String effects = null;
	
		while (line != null) {
			if(line.indexOf("(operator") != -1){
		
				try {
					name = bufferedReader.readLine();
					line = bufferedReader.readLine();
					params = bufferedReader.readLine();
					line = bufferedReader.readLine();
					preconds = bufferedReader.readLine();
					line = bufferedReader.readLine();
					effects = bufferedReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				OperatorParser operatorParser = new OperatorParser(name, params, preconds, effects);
				fileOperations.add(operatorParser.parse());
			}else{
				try {
					line = bufferedReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * parse the facts file
	 *
	 */
	private void parseFactsFile() {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(getFactsFile());
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String line = null;
		try {
			line = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int state = ENTITIES;

		while (line != null) {
			if (line.indexOf("(") == 0) {
				if (line.indexOf("(preconds") != -1) {
					state = PRECONDS;
				} else if (line.indexOf("(effects") != -1) {
					state = EFFECTS;
				} else {
					parse(line, state);
				}
			}
			try {
				line = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * parse a line in a state
	 * @param line
	 * @param state
	 */
	private void parse(String line, int state) {
		// System.out.println(">"+line+"<"+" state is "+state);
		if (state == ENTITIES) {
			String[] tokens = line.split(" ");

			// I haven't found how to replace directly a parenthesis.......

			String identifier = tokens[0].replace('(', '@').replaceAll("@", "");
			String className = tokens[1].replace(')', '@').replaceAll("@", "");
			Entity entity = Entity.build(identifier, EntityClass
					.build(className));
			fileEntities.add(entity);
		} else if (state == PRECONDS) {
			Expression expression = parseExpression(line);
			filePreconditions.add(expression);
		} else if (state == EFFECTS) {
			Expression expression = parseExpression(line);
			fileEffects.add(expression);
		}
	}

	/**
	 * parse a line into an expression
	 * @param line
	 * @return
	 */
	private Expression parseExpression(String line) {
		String[] tokens = line.split(" ");
		// System.out.println(line+ " "+ tokens.length);
		tokens[0] = tokens[0].replace('(', '@').replaceAll("@", "");
		tokens[tokens.length - 1] = tokens[tokens.length - 1].replace(')', '@')
				.replaceAll("@", "");
		tokens[tokens.length - 1] = tokens[tokens.length - 1].replace(')', '@')
				.replaceAll("@", "");
		String predicatString = tokens[0];
		// System.out.println(predicatString);
		Predicat predicat = Predicat.build(predicatString);
		Vector entities = new Vector();
		int i = 1;
		while (i <= tokens.length - 1) {
			Entity entity = Entity.get(tokens[i]);
			entities.add(entity);
			i++;
		}

		Expression expression = new Expression(predicat, entities);
		return expression;
	}

	/**
	 * get the effects
	 * @return
	 */
	public Vector getFileEffects() {
		return fileEffects;
	}

	/**
	 * get the entities
	 * @return
	 */
	public Vector getFileEntities() {
		return fileEntities;
	}

	/**
	 * get the operations
	 * @return
	 */
	public Vector getFileOperations() {
		return fileOperations;
	}

	/**
	 * get the preconditions
	 * @return
	 */
	public Vector getFilePreconditions() {
		return filePreconditions;
	}

	/**
	 * get the facts file
	 * @return
	 */
	private String getFactsFile() {
		return factsFile;
	}
	
	/**
	 * get the facts file
	 * @return
	 */
	private String getOperatorsFile() {
		return operatorsFile;
	}
}
