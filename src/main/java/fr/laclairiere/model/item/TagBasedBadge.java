/**
 * 
 */
package fr.laclairiere.model.item;

import java.util.HashMap;

/**
 * This type of badge is based on a tag. They can have different rank but the same name, where the NamedBadge cannot.
 * A user can have this badge only one time, only the rank can increase.
 * @author HugoPopo
 *
 */
public class TagBasedBadge extends Badge {
	
	/**
	 * This map states the numeric values of the badge rank used to sort the Users
	 */
	public static HashMap<BadgeRank,Integer> RANK_VALUE = createRankValuesMap();

	/**
	 * @return the Map associated a rank with its numeric value
	 */
	private static HashMap<BadgeRank, Integer> createRankValuesMap() {
		return new HashMap<BadgeRank,Integer>(){{
			int i = 1;
			for(BadgeRank br : BadgeRank.values()){
				i++;
				this.put(br, i);
			}
		}};
	}

	public TagBasedBadge(String rank, String name, int badgeId, String link) {
		super(rank, name, badgeId, link);
	}
	
	/**
	 * Compares the rank of the calling badge to given one's rank based on the Map of the class.
	 * @param badge the badge to compare the rank from
	 * @return a positive value if the rank of the given badge is higher, a negative value else, or 0 if the ranks are the same.
	 */
	private int rankCompare(TagBasedBadge badge) {
		return RANK_VALUE.get(this.getRank())-RANK_VALUE.get(badge.getRank());
	}
	
	/**
	 * Checks if the badge has a superior rank than the given one.
	 * @param badge the badge to compare
	 * @return true if superior, false else
	 */
	public boolean isSuperiorOrEqual(TagBasedBadge badge) {
		if(this.rankCompare(badge)<=0){
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "\nBadge "+this.name+":\n"
				+"rank: "+this.getRank().toString()+'\n'
				+"link: "+this.link+'\n';
	}

}
