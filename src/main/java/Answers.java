import java.util.ArrayList;
import java.util.List;

/***
 * All Answers objects contain a question id, answer for the question, list of acceptable answers, and the importance point of the answer.
 * This class allows you to, aside from default getters and setters, and get importance values.
 * 
 * @author dwang1
 *
 */

public class Answers {
	int questionId = -1;
	int answer = -1;
	List <Integer> acceptableAnswers = new ArrayList<>();
	int importance = 0;
	
	public Answers(int questionId, int answer, List<Integer> acceptableAnswers, int importance){
		this.questionId = questionId;
		this.answer = answer;
		this.acceptableAnswers = acceptableAnswers;
		this.importance = importance;
	}
	
	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public void setId(int questionId) {
		this.questionId = questionId;
	}

	public void setAcceptableAnswers(List<Integer> acceptableAnswers) {
		this.acceptableAnswers = acceptableAnswers;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int getquestionId(){
		return questionId;
	}
	
	public List<Integer> getAcceptableAnswers() {
		return acceptableAnswers;
	}

	public int getImportance(){
		if (importance >0){
			return importance;
		} else {
			throw new IllegalArgumentException("Importance points has to be greater than 0.");
		}
	}

	/***
	 * Importance points that the user put for that particular answer
	 * @return importance points 
	 */
	public int getImportanceValue(){
		switch (importance) {
			case 0:
				return 0;
			case 1:
				return 1;
			case 2:
				return 10;
			case 3:
				return 50;
			case 4:
				return 250;
			default: 
				return -1;
		}
	}
}