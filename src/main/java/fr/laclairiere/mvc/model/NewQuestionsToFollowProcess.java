/**
 * 
 */
package fr.laclairiere.mvc.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.Questions;

/**
 * @author QC
 * 
 * 
 */
public class NewQuestionsToFollowProcess extends Process {
	private ArrayList<Item> questionsList = new ArrayList<Item>();
	private String url;
	private String response;
	
	public NewQuestionsToFollowProcess() {
		super();
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		String userId = parameters.get(0);
		newQuestionsToFollow(userId);
	}
	
	private void newQuestionsToFollow(String userId) {
		generateURL(userId);
		response = sendGet(url);
		// top 3 tags of the user
		ArrayList<String> listTag = parseResponseMyTopAnsweredTag(response);
		//System.out.println("Vos tags favoris:");
		//System.out.println(listTag.get(0)+' '+listTag.get(1)+' '+listTag.get(2));
		//System.out.println();
		//System.out.println("Vous aimeriez peut-etre suivre:");
		for(String s : listTag){
			generateURL(s, 5);
			response = sendGet(url);
			parseResponseQuestions(response);
		}
		this.results = questionsList;
	}
	
	public  ArrayList<String> parseResponseMyTopAnsweredTag(String res){

		ArrayList<String> listTag = new ArrayList<String>();

		JSONArray itemArray = transformResponseToItemArray(res);

		if(itemArray.length()!=0){
			for(int i=0; i<3; i++)
			{
				JSONObject jTag = new JSONObject(itemArray.getJSONObject(i).toString());
				String tag = jTag.getString("tag_name");
				listTag.add(tag);
			}
		}
		return listTag;
	}
	
	public void parseResponseQuestions(String res){

		JSONArray itemArray = transformResponseToItemArray(res);
		
		if(itemArray.length()!=0){
			for(int i=0; i<5; i++)
			{
				questionsList.add(new Questions(itemArray.getJSONObject(i).getString("title")));
			}
		}
	}
	
	public void generateURL(String userId) {
		url = URL_DOMAIN_NAME+"/users/"+userId+"/top-answer-tags?page=1&pagesize=10&site=stackoverflow&filter=!--LRPlt1gYHg";
	}
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		// TODO 自动生�?的方法存根
		return null;
	}
	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO 自动生�?的方法存根
		return null;
	}
	public String generateURL(String arg, int pageSize) {
		url = URL_DOMAIN_NAME+"/questions?page=1&pagesize=5&fromdate=1484438400&order=desc&sort=votes&tagged="+arg+"&site=stackoverflow&filter=!bA1d_KrIydYE4V";
		return null;
	}
	
}