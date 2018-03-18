/**
 * 
 */
package fr.laclairiere.mvc.model;

import java.util.ArrayList;

import org.json.JSONArray;

import fr.laclairiere.model.item.Badge;
import fr.laclairiere.model.item.ErrorItem;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.NamedBadge;
import fr.laclairiere.model.item.TagBasedBadge;
import fr.laclairiere.model.item.User;

/**
 * This complex process has 2 subprocesses: GetUserBadgesProcess, which gets a list of Badge objects corresponding to the badge of the connected user, and GetTop100UserBadges, another ComplexProcess.
 * As it takes much times and many requests to the API, the results of the process is stored when executed once with a certain user_id.
 * If the connected user changes, then the process will be re-run.
 * @author HugoPopo
 *
 */
public class GetUsersMoreBadgedThanMe extends ComplexProcess {

	private ArrayList<Item> lastQuery = null;

	private String lastParameter = null;

	/**
	 * 
	 */
	public GetUsersMoreBadgedThanMe() {
		super();
		this.results = new ArrayList<Item>(); 
		this.subProcesses.add(new GetUserBadgesProcess());
		this.subProcesses.add(new GetTop100UserBadgeProcess());
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		if(this.lastQuery==null || !(lastParameter.equals(this.parameters.get(0)))){
			AbstractProcess getMyBadges = this.subProcesses.get(0);
			AbstractProcess top100UsersWithBadges = this.subProcesses.get(1);
			this.lastQuery = new ArrayList<Item>();
			this.lastParameter = this.parameters.get(0);
			getMyBadges.setParameters(this.parameters);
			Thread getMyBadgesThread = new Thread(getMyBadges);
			getMyBadgesThread.start();
			Thread top100UsersWithBadgesThread = new Thread(top100UsersWithBadges);
			top100UsersWithBadgesThread.start();
			try {
				getMyBadgesThread.join();
				top100UsersWithBadgesThread.join();
				ArrayList<Item> myBadges = getMyBadges.getResults();
				ArrayList<Item> top100 = top100UsersWithBadges.getResults();
				for( Item b : myBadges) {
					this.results.add(b);
					this.lastQuery .add(b);
					for(Item u : top100) {
						if(((User)u).getUserId() != Integer.parseInt(this.lastParameter)) {
							for(Badge b2 : ((User)u).getBadgeList()) {
								if(((Badge)b).getBadgeId()==b2.getBadgeId()) {
									if(b instanceof TagBasedBadge) {
										if(((TagBasedBadge) b).isSuperiorOrEqual((TagBasedBadge)b2)) {
											this.results.add(u);
											lastQuery.add(u);
										}
									}
									if(b instanceof NamedBadge) {
										if((((NamedBadge) b).getAwardCount()-((NamedBadge) b2).getAwardCount())<0) {
											this.results.add(u);
											lastQuery.add(u);
										}
									}
								}
							}
						}
					}
				}
			} catch (InterruptedException e) {
				this.results.add(new ErrorItem("Une erreur est surveue lors de la récupératon des donnees."));
			}
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
