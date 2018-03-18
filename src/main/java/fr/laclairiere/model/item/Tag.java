package fr.laclairiere.model.item;

/**
 * @author HugoPopo
 *
 */

public class Tag extends Item {

	private String tagName;
	public Tag(String name) {
		super();
		this.tagName=name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return tagName;
	}
}
