/**
 * 
 */
package fr.laclairiere.model.item;


import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * This class defines a StackOverflow User.
 * Not all attributes are necessary to describe a user, consequently it has multiple constructors, used in different cases.
 * @author HugoPopo
 *
 */
public class User extends Item {

	private int reputation;

	private int userId;

	private /*UserType*/ String userType;

	private String profileImageURL;

	private String displayName;

	private String profileLink;

	private int postCount;

	private int score;

	private ArrayList<Badge> badgeList;

//	private ArrayList<Badge> badgeList = new ArrayList<Badge>();
//
//	private ArrayList<Answer> answerList = new ArrayList<Answer>();

	/**
	 * 
	 * @param reputation
	 * @param userId
	 * @param userType
	 * @param profileImageURL
	 * @param displayName
	 * @param profileLink
	 * @param postCount
	 * @param score
	 * @throws MalformedURLException
	 */
	public User(String reputation,
			String userId,
			String userType,
			String profileImageURL,
			String displayName,
			String profileLink,
			String postCount,
			String score) throws MalformedURLException {
		super();
		this.reputation = Integer.parseInt(reputation);
		this.userId = Integer.parseInt(userId);
		this.userType = userType;
		this.profileImageURL = profileImageURL;
		this.displayName = displayName;
		this.profileLink = profileLink;
		this.postCount = Integer.parseInt(postCount);
		this.score = Integer.parseInt(score);
	}

	/**
	 * 
	 * @param reputation
	 * @param userId
	 * @param userType
	 * @param profileImage
	 * @param displayName
	 * @param link
	 * @throws MalformedURLException
	 */
	public User(String reputation, String userId, String userType, String profileImage, String displayName,
			String link) throws MalformedURLException {
		super();
		this.reputation = Integer.parseInt(reputation);
		this.userId = Integer.parseInt(userId);
		this.userType = userType;
		this.profileImageURL = profileImage;
		this.displayName = displayName;
		this.profileLink = link;
	}

	/**
	 * 
	 * @param userId
	 * @param reputation
	 * @param link
	 */
	public User(int userId, int reputation, String link) {
		this.userId = userId;
		this.reputation = reputation;
		this.profileLink = link;
	}

	public User() {
	}

	// TODO redefine this method for correct display
	@Override
	public String toString() {
		return "User :" + displayName + '\n' + "profileLink=" + profileLink + '\n' + "postCount="
				+ postCount + '\n' +"score=" + score + "\n\n";
	}

	public int getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return this.score;
	}

	public int getPostCount() {
		// TODO Auto-generated method stub
		return this.postCount;
	}

	public String getProfileLink() {
		// TODO Auto-generated method stub
		return this.profileLink;
	}

	/**
	 * @return the reputation
	 */
	public int getReputation() {
		return reputation;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @return the profileImageURL
	 */
	public String getProfileImageURL() {
		return profileImageURL;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	public void setScore(int score) {
		this.score = score;
	}
//	FOR FURTHER DEVELOPMENT
	
	/**
	 * @return the badgeList
	 */
	public ArrayList<Badge> getBadgeList() {
		return badgeList;
	}
	
	public void setBadgeList(ArrayList<Badge> badges) {
		this.badgeList = badges;
	}
//
//	/**
//	 * @return the answerList
//	 */
//	public ArrayList<Answer> getAnswerList() {
//		return answerList;
//	}
//
//	/**
//	 * @param badgeList the badgeList to set
//	 */
//	public void setBadgeList(ArrayList<Badge> badgeList) {
//		this.badgeList = badgeList;
//	}
//
//	public void getBadgesFromOnline() {
//		try	
//		{
//			String url = HTTPWrapper.generateURLFromUserId_Badge(this.userId);
//			String response = HTTPWrapper.sendGet(url);
//			this.badgeList = Parser.parseResponseUserBadges(response);
//		}catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Un probleme est survenu lors de la recuperation des badges.");
//		}
//	}
//	
//	public void getAnswersFromOnline() {
//		try	
//		{
//			String url = HTTPWrapper.generateURLFromUserIdToGetAnswers(this.userId);
//			String response = HTTPWrapper.sendGet(url);
//			this.answerList = Parser.parseResponseAnswers(response);
//		}catch (Exception e) {
//			System.out.println("Un probleme est survenu lors de la recuperation des reponses.");
//		}
//	}
//	
//	public Badge getBadge(int badgeId) {
//		return null;
//	}
//
//	public static ArrayList<User> getTop100Users() throws MalformedURLException {
//		String url = HTTPWrapper.generateURLUsers(100);
//		String response = HTTPWrapper.sendGet(url);
//		ArrayList<User> topList = new ArrayList();
//		try {
//			topList = Parser.parseResponseUsers(response);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ResponseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return topList;
//	}
}

