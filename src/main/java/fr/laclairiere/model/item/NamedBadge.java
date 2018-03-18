/**
 * 
 */
package fr.laclairiere.model.item;

/**
 * This is a type of badge can be stacked by the user. However, they have only one value of rank for a name.
 * @author HugoPopo
 *
 */
public class NamedBadge extends Badge {
	
	/**
	 * the number of times the user stacked this badge
	 */
	private final int awardCount;

	public NamedBadge(String rank, String name, int badgeId, String link, int awardCount) {
		super(rank, name, badgeId, link);
		this.awardCount = awardCount;
	}

	/**
	 * @return the awardCount
	 */
	public int getAwardCount() {
		return awardCount;
	}
	
	public String toString() {
		return "\nBadge "+this.name+":\n"
				+"rang: "+this.getRank().toString()+'\n'
				+"obtenu "+this.awardCount+"fois"+'\n'
				+"lien: "+this.link+'\n';
	}
}
