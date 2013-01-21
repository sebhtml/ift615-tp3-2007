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

import tp3.solver.Solver;

public abstract class Intelligence implements Runnable {
	protected Solver solver ;
	protected boolean failure ;
	public void setSolver(Solver solver){
		this.solver = solver ;
	}
	
	public abstract void thinkNow();
	public abstract void run();
}
