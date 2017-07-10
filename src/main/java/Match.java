/***
 * The Match object allows you to get the score and profile id of an Answer that matches.
 * All Match objects contain a profile id, and score.
 * 
 * @author dwang1
 *
 */
public class Match {
	int profileId = -1;
	float score = -1;

	public Match(int profileId, float score){
		this.profileId = profileId;
		this.score = score;
	}

	public int getProfileId() {
		return profileId;
	}

	public float getScore(){
		return score;
	}
}