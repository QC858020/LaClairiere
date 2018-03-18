package fr.laclairiere.controller;

import java.util.ArrayList;

import fr.laclairiere.mvc.model.Model;

/**
 * Controller for Alice's user stories.
 * @author QC & xiaxt
 *
 */
public class AliceController extends Controller {

	public AliceController(Model model) {
		super(model);
	}

	/**
	 * Orders the model to trigger the process for the all questions which I have answered by the order of tax of success
	 * @param param the userId which is connected
	 */
	public void printAllOfMyAnswers(ArrayList<String> param) {
		try {
			this.model.processChoice(4, param);
		} catch (InterruptedException e) {
			System.out.println("pbm controller");
			e.printStackTrace();
		}
	}
	/**
	 * Orders the model to trigger the process for the new questions of my favorite tags
	 * @param param the userId which is connected
	 */
	public void myQuestions(ArrayList<String> param) {
		try {
			this.model.processChoice(3, param);
		} catch (InterruptedException e) {
			System.out.println("pbm controller");
			e.printStackTrace();
		}
	}

	public void userVerify(String userId) {
		try {
			ArrayList<String> uid = new ArrayList<String>();
			uid.add(userId);
			this.model.processChoice(5, uid);
		} catch (InterruptedException e) {
			System.out.println("pbm controller");
			e.printStackTrace();
		}
	}

	public void myBadges(ArrayList<String> param) {
		try {
			this.model.processChoice(8, param);
		} catch (InterruptedException e) {
			System.out.println("pbm controller");
			e.printStackTrace();
		}
	}

}
