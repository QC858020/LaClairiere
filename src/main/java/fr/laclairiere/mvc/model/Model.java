package fr.laclairiere.mvc.model;

import java.util.ArrayList;
import java.util.Observable;

import fr.laclairiere.model.item.Item;


/**
 * Class corresponding to MVC implementation.
 * It contains a list of the different types of processes to call considering the different user stories, which corresponds to the strategy design pattern
 * 
 * @author HugoPopo
 *
 */
public class Model extends Observable {
	/**
	 * List of the different processes, each is unique.
	 */
	private ArrayList<AbstractProcess> userStories = new ArrayList<AbstractProcess>();

	public Model(){
		super();
		initUserStories();
	}

	/**
	 * Initializes the list of user stories.
	 */
	private void initUserStories() {
		TopTagProcess topTag = new TopTagProcess();
		TopSeveralTagsProcess topSeveralTags=new TopSeveralTagsProcess();
		TopContributorsProcess topContributors = new TopContributorsProcess();
		PrintAllOfMyAnswersProcess myAnswers = new PrintAllOfMyAnswersProcess();
		MyQuestionsProcess myQuestions = new MyQuestionsProcess();
		SearchQuestionProcess searchQuestion = new SearchQuestionProcess();
		NewQuestionsToFollowProcess newQuestions = new NewQuestionsToFollowProcess();
		UserVerifyProcess userVerify = new UserVerifyProcess();
		GetUsersMoreBadgedThanMe getUsersMoreBadgedThanMe = new GetUsersMoreBadgedThanMe();
		
		//Add userstories here.
		userStories.add(topTag);//0
		userStories.add(topSeveralTags);
		userStories.add(topContributors);
		userStories.add(myQuestions);
		userStories.add(myAnswers);//ArrayList(4)
		userStories.add(userVerify);
		userStories.add(searchQuestion);
		userStories.add(newQuestions);//ArrayList(7)
		userStories.add(getUsersMoreBadgedThanMe);//8
		
	}
	
	/**
	 * Runs the process corresponding to the selected user story with the given parameters.
	 * It initializes the process with parameters, runs the corresponding thread, gets the results and transmits them to the view.
	 * @param choice the corresponding number of the process to run
	 * @param parameters the parameters of the running of the process (often just one string)
	 * @throws InterruptedException
	 */
	public void processChoice(int choice, ArrayList<String> parameters) throws InterruptedException{
		AbstractProcess userStory = this.userStories.get(choice);
		userStory.setParameters(parameters);
		Thread userStoryMainThread = new Thread(userStory);
		userStoryMainThread.start();
		userStoryMainThread.join();
		ArrayList<Item> results = userStory.getResults();
		this.setChanged();
		this.notifyObservers(results);
	}
	
	

}
