/**
 * 
 */
package fr.laclairiere.view;

import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import fr.laclairiere.mvc.model.Model;



/**
 * Corresponds to MVC implementation.
 * It is linked to the model as an Observer.
 * @author HugoPopo
 *
 */
public abstract class View extends JFrame implements Observer {
	
	protected JTextArea resultDisplay = new JTextArea();
	protected JScrollPane scroll = new JScrollPane(resultDisplay);  
	
	public View(String label, Model model){
		super(label);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		model.addObserver(this);
	}

}
