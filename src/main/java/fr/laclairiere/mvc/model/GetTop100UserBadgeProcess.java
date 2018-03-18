/**
 * 
 */
package fr.laclairiere.mvc.model;

import java.util.ArrayList;

import org.json.JSONArray;

import fr.laclairiere.model.item.Badge;
import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.User;

/**
 * This complex process gets the top 100 users of Stack Overflow
 * @author HugoPopo
 *
 */
public class GetTop100UserBadgeProcess extends ComplexProcess {

	/**
	 * 
	 */
	public GetTop100UserBadgeProcess() {
		super();
		this.subProcesses.add(new GetTop100UsersProcess());
		this.subProcesses.add(new GetUserBadgesProcess());
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		AbstractProcess getTop100Users = this.subProcesses.get(0);
		AbstractProcess getEachUserBadges = this.subProcesses.get(1);
		Thread getTop100UsersThread = new Thread(getTop100Users);
		getTop100UsersThread.start();
		try {
			getTop100UsersThread.join();
			ArrayList<Item> top100Users = getTop100Users.getResults();
			for(Item u : top100Users){
				ArrayList<String> param = new ArrayList<String>();
				param.add(((Integer)(((User)u).getUserId())).toString());
				getEachUserBadges.setParameters(param);
				Thread getBadgesUserThread = new Thread(getEachUserBadges);
				getBadgesUserThread.start();
				getBadgesUserThread.join();
				ArrayList<Item> items = getEachUserBadges.getResults();
				ArrayList<Badge> badges = new ArrayList<Badge>();
				for(Item i : items){
					if(i instanceof Badge){
						badges.add((Badge) i);
					}
				}
				((User)u).setBadgeList((ArrayList<Badge>)badges);
			}
		} catch (InterruptedException e) {
			this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
		}
		
		

	}

	/* (non-Javadoc)
	 * @see fr.laclairiere.mvc.model.Processable#generateURL(java.lang.String, int)
	 */
	public String generateURL(String arg, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.laclairiere.mvc.model.Processable#parseResponse(org.json.JSONArray)
	 */
	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.laclairiere.mvc.model.Processable#processItems(java.util.ArrayList)
	 */
	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO Auto-generated method stub
		return null;
	}

}
