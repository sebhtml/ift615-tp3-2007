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

import java.awt.Container;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A file viewer
 * @author s
 *
 */
public class FileViewer {
	private String file;

	/**
	 * private constructor
	 *
	 */
	private FileViewer() {

	}

	/**
	 * real constructor
	 * @param file
	 */
	public FileViewer(String file) {
		this.file = file;
	}

	/**
	 * run this
	 *
	 */
	public void goForIt() {
		JFrame frame = new JFrame(file);

		JTextArea jTextField = new JTextArea();
		jTextField.setEditable(false);

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuffer stringBuffer = new StringBuffer();

		String line = null;
		try {
			line = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (line != null) {
			stringBuffer.append(line + "\n");
			try {
				line = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		jTextField.setText(stringBuffer.toString());

		Container container = frame.getContentPane();
		container.setLayout(new GridLayout(1, 2));
		container.add(new JScrollPane(jTextField));
		
		frame.pack();
		frame.setVisible(true);
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
