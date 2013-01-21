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

package tp3.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import tp3.solver.Solver;

/**
 * Actions manager for Solution
 * 
 * @author s
 * 
 */
public class ActionsManager implements ActionListener {
	private Solution solution;

	/**
	 * Constructor
	 * 
	 * @param solution
	 */
	public ActionsManager(Solution solution) {
		this.solution = solution;
	}

	/**
	 * When I perform an action
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == solution.getStartButton()) {
			
			Solver solver = new Solver(solution);
			solver.go();
			solution.close();
		} else if (e.getSource() == solution.getOpButton()) {
			JFileChooser fc = new JFileChooser(Solution.PATH);

			int returnVal = fc.showOpenDialog(new JFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				solution.setOperatorsFile(file.getPath());
				solution.setOperatorsDisplay(file.getName());
				solution.updateOpLabel(file.getName());
			}
		} else if (e.getSource() == solution.getFactsButton()) {
			JFileChooser fc = new JFileChooser(Solution.PATH);

			int returnVal = fc.showOpenDialog(new JFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				solution.setFactsFile(file.getPath());
				solution.setFactsDisplay(file.getName());
				solution.updateFactsLabel(file.getName());
			}
		}
	}
}
