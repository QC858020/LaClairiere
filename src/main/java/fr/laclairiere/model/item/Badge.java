/**
 * 
 */
package fr.laclairiere.model.item;

/**
 * This class represents a general badge of Stack Overflow. It can be tag-based or named.
 * @author HugoPopo
 *
 */
public class Badge extends Item {
	
	protected BadgeRank rank;
	
	protected int badgeId;
	
	protected String name;
	
	protected String link;
	
	public Badge(String rank, String name, int badgeId, String link) {
		this.rank = BadgeRank.valueOf(rank);
		this.name = name;
		this.badgeId = badgeId;
		this.link = link;
	}
	
	/**
	 * @return the id
	 */
	public int getBadgeId() {
		return badgeId;
	}

	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the link of the badge
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * @return the rank
	 */
	public BadgeRank getRank() {
		return rank;
	}
}
