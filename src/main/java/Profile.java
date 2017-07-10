import java.util.ArrayList;
import java.util.List;
/***
 * This class is the template for 1 profile within the list of all profiles.
 * All Profile objects contain an id, and list of answers.
 * Profile objects are allowed to, aside from default setters and getters, get answer ids of ones matched from the list of answers. 
 * 
 * @author dwang1
 *
 */

public class Profile {
	int id = -1;
	List<Answers> answers = null;

	public Profile(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Answers> getAnswers(){
		return (ArrayList<Answers>) answers;
	}

	public void setAnswers(List<Answers> answers) {
		this.answers = answers;
	}

	public Answers getAnswerIdMatched(int answerId){
		for (Answers answerObj: answers){
			if (answerObj.getquestionId() == answerId){
				return answerObj;
			}
		}
		return null;
	}

}