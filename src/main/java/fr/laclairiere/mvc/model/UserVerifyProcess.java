package fr.laclairiere.mvc.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.User;

/**
 * This class gets the Stack Overflow user basing on a user_id given by the user of the application
 * @author QC
 * 
 */
public class UserVerifyProcess extends Process {
	
	public UserVerifyProcess() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try	
		{
			String userId = parameters.get(0);
			String url = generateURL(userId, 0);
			String response = sendGet(url);
			JSONArray itemArray = transformResponseToItemArray(response);
			this.results = (ArrayList<Item>) parseResponse(itemArray);
		}catch (Exception e) {
			this.results.add(new ErrorItem("Une erreur est survenue lors de la récupératon des donnees."));
		}
	}
	
	/**
	 * Parses the item array returned by the API to extract the user
	 * @param itemArray the JSON array to extract the user from
	 * @return an ArrayList<User> containing the user
	 */
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		ArrayList<User> userVerify = new ArrayList<User>();
		
		if (itemArray.get(0).toString() == "")
			return userVerify;
		else {
			for(int i=0; i<itemArray.length(); i++)
			{
				JSONObject jItem = new JSONObject(itemArray.get(i).toString());
				User user = extractUserFromJSONItem(jItem);
				userVerify.add(user);
			}
			return userVerify;
		}
		
	}
	
	/**
	 * Creates a User object from a JSON item
	 * @param jItem the JSON item containing information on the user
	 * @return a User object
	 */
	private static User extractUserFromJSONItem(JSONObject jItem) {
		
		int user_id = jItem.getInt("user_id");
		int reputation = jItem.getInt("reputation");
		String link = jItem.getString("link");
		
		User u = new User(user_id, reputation,link);
		return u;
	}
	
	/**
	 * Does nothing, but necessary to a general behavior.
	 * @see fr.laclairiere.model.Processable
	 */
	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		return items;
	}
	
	/**
	 * Generates the URL corresponding to the GET request to the Stack Exchange REST API
	 * @param arg the user_id to get the user from
	 * @param pageSize the number of items returned by the API
	 * @return the URL corresponding to the HTTP request
	 */
	public String generateURL(String arg, int pageSize) {
		String url = AbstractProcess.URL_DOMAIN_NAME
				+"/users/" + arg
				+"?order=desc&sort=reputation&site=stackoverflow&filter=!T6o*9ZK8ixKg(p5blk";
		return url;
	}
}
