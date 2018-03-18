/**
 * 
 */
package fr.laclairiere.model.item;

/**
 * @author QC
 *
 */
public class Questions extends Item{
	private String question;
	
	public Questions(String q) {
		question = q;
	}
	
	public String toString() {
		return question + "\n";
	}

	public int getScore() {
		return 0;
	}
	
}
