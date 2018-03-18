package fr.laclairiere.mvc.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;


/**
 * This class is the generalization of the different possible operations to interact with the StackExchange REST API.
 * It suits the Component design of the Composite design pattern.
 * @author HugoPopo
 */
public abstract class AbstractProcess implements Runnable, Processable {

	/**
	 * First part of every URL of a request
	 */
	protected final static String URL_DOMAIN_NAME = "https://api.stackexchange.com/2.2";
	
	/**
	 * Parameter(s) of the request (often just one)
	 */
	protected ArrayList<String> parameters;

	/**
	 * Correct and displayable version of the objects returned by the API
	 */
	protected ArrayList<Item> results;
	
	public AbstractProcess(){
		super();
		this.parameters = null;
		this.results = new ArrayList<Item>();
	}

	/**
	 * Uncompress the received dates
	 * @param in the stream returned of the API response
	 * @return a raw string containing the response
	 * @throws IOExceptions
	 */
	public static String uncompress(GZIPInputStream in) throws IOException {   
		if (in == null) {   
			return "";   
		}   
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[256];
		int n;   
		while ((n = in.read(buffer))>= 0) {   
			out.write(buffer, 0, n);   
		}   
		// ToString () using the platform default encoding, can also be explicitly specified as toString ()   
		return out.toString("utf-8");
	}

	/**
	 * Sends the defined request to the StackExcahnge API
	 * @param url the url defining the request
	 * @return a raw string containing the response to the request
	 */
	public String sendGet(String url) {
		String result = "";
		GZIPInputStream in = null;

		try {
			URL realUrl = new URL(url);
			// Open the connection to servers with one URL
			URLConnection connection = realUrl.openConnection();
			// Set the generic request properties
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			// Establish the actual connection
			connection.connect();
			// Get all response header fields

			/*
            Map<String, List<String>> map = connection.getHeaderFields();
            // Traverse all the response header fields
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/

			// Define GZIPInputStream and uncompress responsive dates from servers
			in = new GZIPInputStream(connection.getInputStream());    
			result = uncompress(in);

			// Return
			return result;

		} catch (Exception e) {
			this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
		}

		// Use the 'finally' block to close the input stream
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			}
		}

		// Return
		return result;
	}
	
	/**
	 * Parses a raw string response into a array of JSON items
	 * @param response the raw string containing the response of the API
	 * @return JSONArray an array of JSON items
	 * @throws JSONException
	 */
	public static JSONArray transformResponseToItemArray(String response) throws JSONException {
		JSONObject jObject = new JSONObject(response);
		JSONArray itemArray = new JSONArray(jObject.getJSONArray("items").toString());
		return itemArray;
	}

	/**
	 * @return the parameters
	 */
	public ArrayList<String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(ArrayList<String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the results
	 */
	public ArrayList<Item> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(ArrayList<Item> results) {
		this.results = results;
	}

}
