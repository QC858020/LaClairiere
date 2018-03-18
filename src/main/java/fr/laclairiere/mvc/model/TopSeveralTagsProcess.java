package fr.laclairiere.mvc.model;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.User;

/**
 * @author Xiaxt
 */

public class TopSeveralTagsProcess extends Process{

	public TopSeveralTagsProcess(){
		super();
	}
	@SuppressWarnings("unchecked")
	public void run() {
		this.results = new ArrayList<Item>();
		for (int j=0;j<this.parameters.size();j++){
			String param = this.parameters.get(j);
			String url = this.generateURL(param, 30);
			String response = this.sendGet(url);
			JSONArray itemArray = transformResponseToItemArray(response);

			try {
				this.results.addAll( (ArrayList<Item>) parseResponse(itemArray));
			} catch (ResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.results = this.processItems(this.results, false);
	}
	/**
	 * @param arg the tag to get best users from
	 * @see fr.laclairiere.model.Processable
	 */
	public String generateURL(String arg, int pageSize) {
		String url = AbstractProcess.URL_DOMAIN_NAME
				+"/tags/" + URLEncoder.encode(arg)
				+ "/top-answerers/all_time?"
				+ "&pagesize=" + pageSize
				+ "&site=stackoverflow";
		return url;
	}
	/**
	 * @return a list of User objects
	 * @see fr.laclairiere.model.Processable
	 */
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		ArrayList<User> users = new ArrayList<User>();
		if(itemArray.length()==0) throw new ResponseException();
		for(int i=0; i<itemArray.length(); i++)
		{
			JSONObject jItem = new JSONObject(itemArray.get(i).toString());
			User user = null;
			try {
				user = extractUserFromJSONItem(jItem);
			} catch (JSONException e) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			} catch (MalformedURLException e) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			}
			users.add(user);
		}

		return users;
	}
	/**
	 * Creates a User object from a JSON user item
	 * @param jItem the JSON item
	 * @return User the exploitable user
	 * @throws JSONException
	 * @throws MalformedURLException
	 */
	private User extractUserFromJSONItem(JSONObject jItem) throws JSONException, MalformedURLException {
		JSONObject jUser = new JSONObject(jItem.getJSONObject("user").toString());
		// System.out.println(jUser.toString()+'\n');

		String reputation = String.valueOf(jUser.getInt("reputation"));
		String profileImage = jUser.getString("profile_image");
		// System.out.println(profileImage);
		String userId = String.valueOf(jUser.getInt("user_id"));
		// System.out.println(userId);

		String userType = jUser.getString("user_type");
		String displayName = jUser.getString("display_name");
		String link = jUser.getString("link");

		String postCount = String.valueOf(jItem.getInt("post_count"));
		// System.out.println(postCount);
		String score = String.valueOf(jItem.getInt("score"));
		// System.out.println(score);

		User user = new User(reputation, userId, userType, profileImage, displayName, link, postCount, score);
		return user;
	}

	/**
	 * Does nothing, but necessary to a general behavior.
	 * @see fr.laclairiere.model.Processable
	 */
	public ArrayList<Item> processItems( ArrayList<Item> items, final boolean asc) {
		Collections.sort(items, new Comparator<Item>() {
			public int compare(Item o1, Item o2) {
				if(asc)
					return ((User)o1).getScore() - ((User)o2).getScore();
				return ((User)o2).getScore() - ((User)o1).getScore();
			}
		});
		return items;
	}
	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO Auto-generated method stub
		return null;
	}
}
