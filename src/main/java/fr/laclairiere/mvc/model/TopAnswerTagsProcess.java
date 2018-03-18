package fr.laclairiere.mvc.model;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.Tag;

/**
 * @author Xiaxt
 *
 */

public class TopAnswerTagsProcess extends Process {
	@SuppressWarnings("unchecked")
	public void run() {
		String param = this.parameters.get(0);
		this.results = new ArrayList<Item>();
		String url = this.generateURL(param, 10);
		String response = this.sendGet(url);
		JSONArray itemArray = transformResponseToItemArray(response);

		try {
			
			this.results=(ArrayList<Item>)parseResponse(itemArray);
			
		} catch (ResponseException e) {
			this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
		}

	}
	/**
	 * @param arg the userId to get the favorite tags from
	 * @see fr.laclairiere.model.Processable
	 */
	public String generateURL(String arg, int pageSize) {
		String url = URL_DOMAIN_NAME+"/users/"
					+arg+"/top-answer-tags?page=1&pagesize="
					+pageSize+"&site=stackoverflow&filter=!--LRPlt1gYHg";
		
		return url;
	}
	/**
	 * @return a list of Tag objects
	 * @see fr.laclairiere.model.Processable
	 */
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		ArrayList<Tag> listTag = new ArrayList<Tag>();
		if(itemArray.length()==0) throw new ResponseException();
		for(int i=0; i<itemArray.length(); i++)
		{
			JSONObject jItem = new JSONObject(itemArray.get(i).toString());
			
			Tag tag = null;
			
			try {
				
				tag = extractTagFromJSONItem(jItem);
				
			} catch (JSONException e) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			} catch (MalformedURLException e) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			}
			
				listTag.add(tag);
				
		}
		
		return listTag;
	}
	
	private Tag extractTagFromJSONItem(JSONObject jItem) throws JSONException, MalformedURLException {

		String name=jItem.getString("tag_name");

		Tag tag=new Tag(name);
		return tag;
}

	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO Auto-generated method stub
		return null;
	}

}
