package fr.laclairiere.mvc.model;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.Answer;
import fr.laclairiere.model.item.ErrorItem;

/**
 * @author QC
 * 
 * @param String UserId
 */
public class PrintAllOfMyAnswersProcess extends Process {
	private ArrayList<Item> answerList;
	private String url;
	private String response;
	
	public PrintAllOfMyAnswersProcess() {
		super();
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		getAnswersFromOnline();
		this.results = answerList;
	}
	
	public void generateURL() {
		String UserId = parameters.get(0);
		url = URL_DOMAIN_NAME+"/users/" + UserId + "/answers?order=desc&sort=votes&site=stackoverflow&filter=!mSGf9k)N(K";
	}
	
	/**
	 * 
	 * @param String response
	 * @return ArrayList<Item>(answer)
	 * @throws JSONException
	 * @throws MalformedURLException
	 * @throws TagException
	 * <p>create 1 list of answers from the input stream of servers</p>
	 */
	public ArrayList<Item> parseResponse(String response) throws JSONException, MalformedURLException{
		ArrayList<Item> answerListLocal = new ArrayList<Item>();
		
		JSONArray itemArray = transformResponseToItemArray(response);
		
		for(int i=0; i<itemArray.length(); i++)
		{
			JSONObject jItem = new JSONObject(itemArray.get(i).toString());
			Answer answer = extractAnswerFromJSONItem(jItem);
			answerListLocal.add(answer);
		}
		return answerListLocal;
	}
	
	private void getAnswersFromOnline() {
		try	
		{
			generateURL();
			response = sendGet(url);
			answerList = parseResponse(response);
		}catch (Exception e) {
			this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
		}
	}
	
	private static Answer extractAnswerFromJSONItem(JSONObject jItem) {
		
		int answerId = jItem.getInt("answer_id");
		int questionId = jItem.getInt("question_id");
		int score = jItem.getInt("score");
		String link = jItem.getString("link");
		
		Answer ans = new Answer(answerId, questionId, score, link);
		return ans;
	}
	
	/**
	 * Does nothing, but necessary to a general behavior.
	 * @see fr.laclairiere.model.Processable
	 */
	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		return items;
	}
	public String generateURL(String arg, int pageSize) {
		return null;
	}
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		return null;
	}
}
