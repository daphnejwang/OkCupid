import java.util.ArrayList;
import java.util.List;

/***
 * Results object contains the match results per profile.
 * All Results objects contain a profile id, and list of matched result.
 *  
 * @author dwang1
 *
 */

public class Results {
	int profileId = -1;
	List<Match> matchList= new ArrayList<Match>();

	public Results(int profileId, List<Match> matchList){
		this.profileId = profileId;
		this.matchList = matchList;
	}

	public int getProfileId(){
		return profileId;
	}

	public void setMatches(List<Match> matchesList) {
		this.matchList = matchesList;
	}

	public List<Match> getMatches(){
		return matchList;
	}
}