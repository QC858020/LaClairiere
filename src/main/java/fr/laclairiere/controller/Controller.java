package fr.laclairiere.controller;

import fr.laclairiere.mvc.model.Model;
import fr.laclairiere.view.View;

/**
 * Class corresponding to MVC implementation.
 * It is linked to the model and to a specific view.
 * @author HugoPopo
 *
 */
public abstract class Controller {

	/**
	 * Model of MVC pattern
	 */
	protected Model model;
	
	/**
	 * a specific view
	 */
	private View view = null;

	public Controller(Model model) {
		super();
		this.model = model;
	}

}
