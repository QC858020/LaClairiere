/**
 * 
 */
package fr.laclairiere.model.item;

/**
 * @author HugoPopo
 *
 */
public class Answer extends Item{

	private int answerId;
	
	private int questionId;
	
	private int score;
	
	private String link;

	public Answer(int answerId, int questionId, int score, String link) {
		super();
		this.answerId = answerId;
		this.questionId = questionId;
		this.score = score;
		this.link = link;
	}

	/**
	 * @return the answerId
	 */
	public int getAnswerId() {
		return answerId;
	}

	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", questionId=" + questionId + ", score=" + score + "]\n";
	}

}
