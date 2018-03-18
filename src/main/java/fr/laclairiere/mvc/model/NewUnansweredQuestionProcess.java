package fr.laclairiere.mvc.model;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.Questions;

public class NewUnansweredQuestionProcess extends Process {

	@SuppressWarnings("unchecked")
	public void run() {
		String param = this.parameters.get(0)+";"+this.parameters.get(1)+";"+this.parameters.get(2);
		this.results = new ArrayList<Item>();
		String url = this.generateURL(param, 30);
		String response = this.sendGet(url);
		JSONArray itemArray = transformResponseToItemArray(response);

		try {
			this.results = (ArrayList<Item>) parseResponse(itemArray);
		} catch (ResponseException e) {
			this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
		}

	}

	public String generateURL(String arg, int pageSize) {
		String url;
		
		url = URL_DOMAIN_NAME+"/questions/unanswered?page=1&pagesize="
		+pageSize+"&order=desc&sort=creation&tagged="
		+URLEncoder.encode(arg)+"&site=stackoverflow";
		
		return url;
	}
	
	/**
	 * @return a list of Questions objects
	 * @see fr.laclairiere.model.Processable
	 */

	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		ArrayList<Questions> questionList = new ArrayList<Questions>();
		
		if(itemArray.length()==0) throw new ResponseException();
		for(int i=0; i<itemArray.length(); i++)
		{
			JSONObject jItem = new JSONObject(itemArray.get(i).toString());
			Questions question = extractQuestionFromJSONItem(jItem);
			questionList.add(question);
		}
		return questionList;
	}

	private Questions extractQuestionFromJSONItem(JSONObject jItem) throws ResponseException {
		String questionString = jItem.getString("title");

		Questions qst = new Questions(questionString);
		return qst;
	}

	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO Auto-generated method stub
		return null;
	}

}
