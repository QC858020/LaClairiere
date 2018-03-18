/**
 * 
 */
package fr.laclairiere.controller;

import java.util.ArrayList;

import fr.laclairiere.mvc.model.Model;

/**
 * Controller for Bob's user stories.
 * @author QC
 *
 */
public class BobController extends Controller {

	public BobController(Model model) {
		super(model);
	}

	/**
	 * The last user's story of Bob " M’indiquer les nouvelles questions (avec déjà des réponses) dans mes champs d’intérêt "
	 * @param param the userId which is connected param[0]
	 */
	public void newQuestionsToFollow(ArrayList<String> param) {
		try {
			this.model.processChoice(6, param);
		} catch (InterruptedException e) {
			System.out.println("pbm controller");
			e.printStackTrace();
		}
	}
		
	/**
	* The first user's story of Bob " M’aider à trouver des questions existantes qui correspondent à mon besoin"
	* @param param the sentence wich will be used to search questions
	*/
	public void newQuestionsToFind(ArrayList<String> param) {
		try {
			this.model.processChoice(6, param);
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

}
