
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;

public class OkCupid {

	/***
	 * Description: calculates the importance points of profile B's answers that matches with profile A's
	 * @param aAcceptAns
	 * @param aImpPts
	 * @param bAns
	 * @return aImpPts if matched
	 */
	public int calcPoints(List<Integer> aAcceptAns, int aImpPts, int bAns) {
		if (aImpPts == 0) {
			return 0;
		}
		for (int answer: aAcceptAns){

			if (answer == bAns) {
				return aImpPts;
			} 
		}
		return 0;
	}

	/***
	 * Description: Calculates the max possible importance points of a given profile
	 * @param impPts
	 * @return totalImpPts
	 */
	public int calcMaxPoints (int [] impPts){
		int totalImpPts = 0;
		for (int pts: impPts){
			totalImpPts += pts;
		} 
		return totalImpPts;
	}

	/***
	 * Description: Calculates the satisfactory percentage of profile B's answers that matches with profile A's
	 * @param totalPts
	 * @param maxPts
	 * @return percentage matched
	 */
	public float calcPercentage (int totalPts, int maxPts){
		return (float)totalPts/maxPts;
	}

	/***
	 * Description: Calculates the percentage satisfactory of both matches.
	 * @param aPercentage
	 * @param bPercentage
	 * @return match percentage of both profiles
	 */
	public float calcMaxPercentage (float aPercentage, float bPercentage){
		return (float) Math.sqrt(aPercentage * bPercentage);
	}

	/***
	 * Description: Calculates the reasonable margin of error given the number of questions both profiles answered.
	 * @param s
	 * @return 1/s
	 */
	public float calcReasonableMarginError (int s){
		return 1.0f/s;
	}

	/***
	 * Description: True Match is calculated by subtracting the margin of error from the calculated match percentage.
	 * @param calcMaxPercentage
	 * @param calcReasonableMarginError
	 * @return
	 */
	public float calcTrueMatch (float calcMaxPercentage, float calcReasonableMarginError){
		return calcMaxPercentage - calcReasonableMarginError;
	}

	/***
	 * Description: Reads a json file and converts it to a Profiles object
	 * @return a Profiles object that contains a list of profiles
	 */
	public Profiles getJsonProfileList(){
		Gson gson = new Gson();
		Profiles profiles = null;
		try {
			FileReader file = new FileReader("/Users/dwang1/dev/workspaceAlgorithms/okCupidPackage/src/main/java/input.json");
			profiles = gson.fromJson(file, Profiles.class);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return profiles;
	}

	/***
	 * Description: Puts the profile list into a profile hash map of profile Ids and profile objects.
	 * @param profileList
	 * @return userProfilesMap
	 */
	public Map<Integer, Profile> getProfileObject(Profiles profileList){
		Map<Integer, Profile> userProfilesMap = new HashMap<Integer, Profile>();
		for (Profile profile: profileList.getProfiles()){
			userProfilesMap.put(Integer.valueOf(profile.getId()),profile);
		}
		return userProfilesMap;
	}

	/***
	 * Description: Counts the total set of questions both profiles answered given 2 profiles.
	 * @param profileA
	 * @param profileB
	 * @return totalQAnsTogether
	 */
	public int calculateS(Profile profileA, Profile profileB){
		int totalQAnsTogether = 0;
		for (Answers profileAAnsObj: profileA.getAnswers()) {
			int ansID = profileAAnsObj.getquestionId();
			Answers matchedAnswers = profileB.getAnswerIdMatched(ansID);
			if (matchedAnswers != null){
				totalQAnsTogether += 1;
			}
		}
		return totalQAnsTogether;
	}

	/***
	 * Description: Calculates the score (total importance points) of profile B's match to profile A, given 2 profiles.
	 * @param profileA
	 * @param profileB
	 * @return bMatchToAScore
	 */
	public float calculateMatchPoints(Profile profileA, Profile profileB){
		int pointsCalc = 0;
		int maxImpPoints = 0;
		for (Answers profileAAnsObj: profileA.getAnswers()){

			int ansID = profileAAnsObj.getquestionId();
			Answers matchedAnswers = profileB.getAnswerIdMatched(ansID);
			if (matchedAnswers != null) {
				maxImpPoints += profileAAnsObj.getImportanceValue();
				pointsCalc += calcPoints(profileAAnsObj.getAcceptableAnswers(), profileAAnsObj.getImportanceValue(), matchedAnswers.getAnswer());
			}
		}

		float bMatchToAScore = calcPercentage (pointsCalc, maxImpPoints);
		return bMatchToAScore;
	}
	
	/***
	 * Description: Calculates the True Match between 2 profiles.
	 * @param profileA
	 * @param profileB
	 * @return bMatchtoAObj
	 */
	public Match calcTrueMatch(Profile profileA, Profile profileB){
		float bMatchtoAPoints = calculateMatchPoints(profileA, profileB);
		float aMatchtoBPoints = calculateMatchPoints(profileB, profileA);

		float maxPercentage = calcMaxPercentage(bMatchtoAPoints,aMatchtoBPoints);
		int s = calculateS(profileA,profileB);
		float marginError = calcReasonableMarginError(s);
		float trueMatch = calcTrueMatch(maxPercentage, marginError);

		Match bMatchtoAObj = new Match(profileB.getId(), trueMatch);

		return bMatchtoAObj;
	}

	/***
	 * Description: Gets a list of true matches for profile A given a hashmap of profiles.
	 * @param profileA
	 * @param userProfilesMap
	 * @return matchedList
	 */
	public List<Match> getAMatchesList(Profile profileA, Map<Integer, Profile> userProfilesMap) {
		ArrayList<Match> matchedList = new ArrayList<Match>();
		Set<Integer> keyList = userProfilesMap.keySet();
		Iterator<Integer> keys = keyList.iterator();
		while (keys.hasNext()) {
			Integer key = keys.next();
			Profile profileObj = userProfilesMap.get(key);
			if (profileA.getId() != profileObj.getId()){
				Match matchedProfiles = calcTrueMatch(profileA, profileObj);
				matchedList.add(matchedProfiles);
			}
		}
		return matchedList;
	}

	/***
	 * Description: Given a full hash map of profiles, it returns the top ten matches per profile
	 * This is the main function that calculates the whole algorithm and called in App.java
	 * @param userProfilesMap
	 * @return listOfMatchedResults
	 */
	public List<Results> getResultsList (Map<Integer, Profile> userProfilesMap){
		List<Results> listOfMatchedResults = new ArrayList<Results>();
		Set<Integer> set = userProfilesMap.keySet();
		Iterator<Integer> resultEntries = set.iterator();
		while (resultEntries.hasNext()){
			Integer key = resultEntries.next();
			List<Match> listOfMatches = getTopTen(sortResultsList(getAMatchesList(
					userProfilesMap.get(key), userProfilesMap)));

			Results result = new Results(key, listOfMatches);
			listOfMatchedResults.add(result);
		}
		return listOfMatchedResults;
	}

	/***
	 * Description: Given a list of matched results, it sorts the list from highest score to lowest.
	 * @param listOfMatchedResults
	 * @return listOfMatchedResults
	 */
	public List<Match> sortResultsList(List<Match> listOfMatchedResults){
		int n = listOfMatchedResults.size();
		for (int i = 0; i < n-1; i++){
			for (int j = 0; j < n-i-1; j++){
				if (listOfMatchedResults.get(j).getScore() > listOfMatchedResults.get(j+1).getScore()){
					Match temp = listOfMatchedResults.get(j);
					listOfMatchedResults.set(j, listOfMatchedResults.get(j+1));
					listOfMatchedResults.set(j+1, temp);
				}
			}
		}
		return listOfMatchedResults;
	}

	/***
	 * Given the full list of sorted matches, it takes the top ten
	 * @param listofMatches
	 * @return top 10 list of Matches
	 */
	public List<Match> getTopTen(List<Match> listofMatches){
		if (listofMatches.size() < 10) {
			return listofMatches;
		} else {
			return listofMatches.subList(listofMatches.size()-11, listofMatches.size()-1);
		}
	}

}
