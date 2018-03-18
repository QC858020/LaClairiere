package fr.laclairiere.mvc.model;

import java.util.ArrayList;
import org.json.JSONArray;

import fr.laclairiere.model.item.Item;

/**
 * This interface defines the behavior of a Process.
 * It must generate autonomously its URL, and be able to parse and treat the response to the StackExchange REST API.
 * @author HugoPopo
 *
 */
public interface Processable {
	
	/**
	 * Generates the URL corresponding to the HTTP request to the StackExchange API.
	 * @param arg parameter defining the uniqueness of the request. It can be a tag, user_id, question_id, etc.
	 * @param pageSize the maximum number of items returned by the API
	 * @return the URL defining the request
	 */
	public String generateURL(String arg, int pageSize);
	
	/**
	 * Transforms the JSONArray of standard items extracted from the response of the StackExchange API into a list of concrete and usable items
	 * @param itemArray the array in JSON containing the different items
	 * @return the list of item objects
	 * @throws ResponseException
	 */
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException;
	
	/**
	 * Handles the extracted items. It can be a basic return if no change to the returned data is necessary.
	 * The method is however called in every case.
	 * @param items the list of item objects
	 * @return the list with the same objects, transformed if necessary
	 */
	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items);

}
