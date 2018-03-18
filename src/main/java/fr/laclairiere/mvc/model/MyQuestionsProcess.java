package fr.laclairiere.mvc.model;

import java.util.ArrayList;

import org.json.JSONArray;

import fr.laclairiere.model.item.Item;

public class MyQuestionsProcess extends ComplexProcess {

	public void run() {
		this.results = new ArrayList<Item>();
		TopAnswerTagsProcess topAnswerTags=new TopAnswerTagsProcess();
		NewUnansweredQuestionProcess newUnansweredQuestion=new NewUnansweredQuestionProcess();
		this.subProcesses.add(topAnswerTags);
		this.subProcesses.add(newUnansweredQuestion);
		this.subProcesses.get(0).setParameters(this.getParameters());
		Thread Thread1 = new Thread(this.subProcesses.get(0));
		Thread1.start();
		try {
			Thread1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> p=new ArrayList<String>();
		for(int i=0;i<3;i++){
			String pm=this.subProcesses.get(0).getResults().get(i).toString();
			p.add(pm);
		}
		this.subProcesses.get(1).setParameters(p);
		Thread Thread2 = new Thread(this.subProcesses.get(1));
		Thread2.start();
		try {
			Thread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.results=this.subProcesses.get(1).getResults();
	}

	public String generateURL(String arg, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<? extends Item> parseResponse(JSONArray itemArray) throws ResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<? extends Item> processItems(ArrayList<? extends Item> items) {
		// TODO Auto-generated method stub
		return null;
	}

}
