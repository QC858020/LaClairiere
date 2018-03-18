package fr.laclairiere.mvc.model;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.Questions;


/**
 * @author THOMAS0COURANT
 */

public class SearchQuestionProcess extends Process {

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
			this.results.add(new ErrorItem("Une erreur est survenue lors de la récupératon des donnees."));
		}

	}
	/**
	 * @param arg the sentence wich will be used to search questions
	 * @see fr.laclairiere.model.Processable
	 */
	public String generateURL(String arg, int pageSize) {
		String url = URL_DOMAIN_NAME+"/similar?pagesize="
				+pageSize+"&order=desc&sort=activity&title="
				+arg+"&site=stackoverflow";
					
		
		return url;
	}
	/**
	 * @return a list of Questions objects
	 * @see fr.laclairiere.model.Processable
	 */
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		ArrayList<Questions> listQuestion = new ArrayList<Questions>();
		if(itemArray.length()==0) throw new ResponseException();
		for(int i=0; i<itemArray.length(); i++)
		{
			JSONObject jItem = new JSONObject(itemArray.get(i).toString());
			
			Questions question = null;
			
			try {
				
				question = extractQuestionFromJSONItem(jItem);
				
			} catch (JSONException e) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			} catch (MalformedURLException e) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			}
			
				listQuestion.add(question);
				
		}
		
		return listQuestion;
	}
	
	private Questions extractQuestionFromJSONItem(JSONObject jItem) throws JSONException, MalformedURLException {

		String name=jItem.getString("title");

		Questions question=new Questions(name);
		return question;
}

	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO Auto-generated method stub
		return null;
}
}
