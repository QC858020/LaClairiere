/**
 * 
 */
package fr.laclairiere.mvc.model;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.User;

/**
 * This class gets the top 100 users of Stack Overflow, based on their reputation.
 * @author HugoPopo
 *
 */
public class GetTop100UsersProcess extends Process {

	/**
	 * 
	 */
	public GetTop100UsersProcess() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		String url = this.generateURL(null, 100);
		String response = this.sendGet(url);
		JSONArray itemArray = transformResponseToItemArray(response);
		this.results = (ArrayList<Item>) processItems(parseResponse(itemArray));

	}

	/**
	 * @see fr.laclairiere.mvc.model.Processable#generateURL(java.lang.String, int)
	 */
	public String generateURL(String arg, int pageSize) {
		String url = URL_DOMAIN_NAME+"/users?pagesize="+pageSize+"&order=desc&sort=reputation&site=stackoverflow";
		return url;
	}

	/**
	 * @see fr.laclairiere.mvc.model.Processable#parseResponse(org.json.JSONArray)
	 */
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) {
		ArrayList<User> users = new ArrayList<User>();
		if(itemArray.length()==0)
			try {
				throw new ResponseException();
			} catch (ResponseException e1) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			}
		for(int i=0; i<itemArray.length(); i++)
		{
			JSONObject jItem = new JSONObject(itemArray.get(i).toString());
			User user = null;
			try {
				user = extractUserFromJSONItem(jItem);
			} catch (JSONException e) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			}
			users.add(user);
		}

		return users;
	}

	private User extractUserFromJSONItem(JSONObject jUser) {

		String reputation = String.valueOf(jUser.getInt("reputation"));
		String profileImage = jUser.getString("profile_image");
		// System.out.println(profileImage);
		String userId = String.valueOf(jUser.getInt("user_id"));
		// System.out.println(userId);

		String userType = jUser.getString("user_type");
		String displayName = jUser.getString("display_name");
		String link = jUser.getString("link");

		User user = null;
		try {
			user = new User(reputation, userId, userType, profileImage, displayName, link);
		} catch (MalformedURLException e) {
			this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
		}
		return user;
	}

	/**
	 * @see fr.laclairiere.mvc.model.Processable#processItems(java.util.ArrayList)
	 */
	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO Auto-generated method stub
		return items;
	}

}
