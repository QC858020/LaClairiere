/**
 * 
 */
package fr.laclairiere.mvc.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.laclairiere.model.item.Badge;
import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.NamedBadge;
import fr.laclairiere.model.item.TagBasedBadge;


/**
 * This class gets the 30 more ranked badges of a given user. 
 * @author HugoPopo
 *
 */
public class GetUserBadgesProcess extends Process {

	/**
	 * 
	 */
	public GetUserBadgesProcess() {
		super();
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		String param = this.parameters.get(0);
		String url = this.generateURL(param, 1);
		String response = this.sendGet(url);
		JSONArray itemArray = transformResponseToItemArray(response);
		try	
		{
			this.results = (ArrayList<Item>) processItems(parseResponse(itemArray));
			
		}catch (Exception e) {
			this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
		}
	}

	/**
	 * @see fr.laclairiere.mvc.model.Processable#generateURL(java.lang.String, int)
	 */
	public String generateURL(String arg, int pageSize) {
		String url = AbstractProcess.URL_DOMAIN_NAME+"/users/" + arg + "/badges?order=desc&sort=rank&site=stackoverflow&filter=!-pUWD-S.";
		return url;
	}

	/**
	 * @see fr.laclairiere.mvc.model.Processable#parseResponse(org.json.JSONArray)
	 */
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		ArrayList<Badge> badges = new ArrayList<Badge>();

		if(itemArray.length()==0) throw new ResponseException();
		for(int i=0; i<itemArray.length(); i++)
		{
			JSONObject jItem = new JSONObject(itemArray.get(i).toString());
			Badge badge = extractBadgeFromJSONItem(jItem);
			badges.add(badge);
		}

		return badges;
	}

	/**
	 * Extracts a badge from a raw JSON badge item
	 * @param jItem the badge item to extract
	 * @return the Badge object, whether a NamedBadge or a TagBasedBadge
	 */
	private Badge extractBadgeFromJSONItem(JSONObject jItem) {
		String rank = String.valueOf(jItem.get("rank"));
		int badgeId = jItem.getInt("badge_id");
		String name = jItem.getString("name");
		String link = jItem.getString("link");

		Badge badge = null;
		if(jItem.getString("badge_type").equals("tag_based")) {
			badge = new TagBasedBadge(rank, name, badgeId, link);
		}
		if(jItem.getString("badge_type").equals("named")) {
			int awardCount = jItem.getInt("award_count");
			badge = new NamedBadge(rank, name, badgeId, link, awardCount);
		}
		return badge;
	}

	/* (non-Javadoc)
	 * @see fr.laclairiere.mvc.model.Processable#processItems(java.util.ArrayList)
	 */
	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO Auto-generated method stub
		return items;
	}

}
