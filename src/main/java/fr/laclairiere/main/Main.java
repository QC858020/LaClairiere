/**
 * 
 */
package fr.laclairiere.main;

import fr.laclairiere.controller.AliceController;
import fr.laclairiere.controller.BobController;
import fr.laclairiere.controller.DaveController;
import fr.laclairiere.mvc.model.Model;
import fr.laclairiere.view.AliceView;
import fr.laclairiere.view.BobView;
import fr.laclairiere.view.DaveView;

/**
 * @author Gogo
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Model model = new Model();

		DaveController daveController = new DaveController(model);
		
		AliceController aliceController = new AliceController(model);
		
		BobController bobController = new BobController(model);
		
		DaveView daveView = new DaveView("Dave", model, daveController);
		
		AliceView aliceView = new AliceView("Alice", model, aliceController);//Test
		
		BobView bobView = new BobView("Bob", model, bobController);//Test
		
		model.addObserver(daveView);
		model.addObserver(aliceView);
		model.addObserver(bobView);
	}

}
