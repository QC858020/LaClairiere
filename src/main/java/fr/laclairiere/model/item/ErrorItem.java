/**
 * 
 */
package fr.laclairiere.model.item;

/**
 * This is a standard item used on the display when an error occurs.
 * @author HugoPopo
 *
 */
public class ErrorItem extends Item {

	/**
	 * The message to display on the view
	 */
	private String errorMessage;
	/**
	 * 
	 */
	public ErrorItem(String e) {
		this.errorMessage = e;
	}

	public String toString() {
		return this.errorMessage+'\n';
	}
}
