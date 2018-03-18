/**
 * 
 */
package fr.laclairiere.controller;

import java.util.ArrayList;

import fr.laclairiere.mvc.model.Model;

/**
 * Controller for Dave's user stories.
 * @author HugoPopo
 *
 */
public class DaveController extends Controller {

	public DaveController(Model model) {
		super(model);
	}

	/**
	 * Orders the model to trigger the process for the top tag user story
	 * @param param the tag given by the user
	 */
	public void topTag(ArrayList<String> param) {
		try {
			this.model.processChoice(0, param);
		} catch (InterruptedException e) {
			System.out.println("pbm controller");
			e.printStackTrace();
		}
	}
	
	/**
	 * Orders the model to trigger the process for the top several tag user story
	 * @param param the tag given by the user
	 */
	
	public void topSeveralTags(ArrayList<String> param) {
		try {
			this.model.processChoice(1, param);
		} catch (InterruptedException e) {
			System.out.println("pbm controller");
			e.printStackTrace();
		}
	}
	
	/**
	 * Orders the model to trigger the process for the top contributor user story
	 * @param param the tag given by the user
	 */
	
	public void topContributors(ArrayList<String> param) {
		try {
			this.model.processChoice(2, param);
		} catch (InterruptedException e) {
			System.out.println("pbm controller");
			e.printStackTrace();
		}
	}

}
