/**
 * 
 */
package fr.laclairiere.mvc.model;

import java.util.ArrayList;

import org.json.JSONArray;

import fr.laclairiere.model.item.Item;

/**
 * This class is a node in a tree made of different processes, each retrieving a different type of information.
 * This class is charged to analyze and treat these different types of information.
 * It suits the Composite class of the Composite design pattern.
 * @author HugoPopo
 *
 */
public abstract class ComplexProcess extends AbstractProcess {
	
	public ArrayList<AbstractProcess> subProcesses;

	/**
	 * Empty constructor
	 */
	public ComplexProcess() {
		super();
		this.subProcesses = new ArrayList<AbstractProcess>();
	}

	/**
	 * Empty constructor
	 */
	public ComplexProcess(ArrayList<AbstractProcess> subProcesses) {
		super();
		this.subProcesses = subProcesses;
	}

}
