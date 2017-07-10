import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class OkCupidTest {
	
	@Mock
	Profile profileMock, profileMock2;
	
	@Mock
	ArrayList<Answers> answersListMock;
	
	@Mock
	Answers answersMock;
	
	OkCupid okCupid = new OkCupid();
	
	@Rule 
	public MockitoRule rule = MockitoJUnit.rule();
	
	@Test
	public void okCupidTrueMatch(){
		
		okCupid.calcTrueMatch(profileMock, profileMock2);
		when(profileMock.getAnswers()).thenReturn(answersListMock);
	}
	
	@Test 
	public void calcPointsTest(){
		List<Integer> aAcceptAns = new ArrayList<Integer>();
		aAcceptAns.add(2);
		aAcceptAns.add(3);
		int aImpPts = 10;
		int bAns = 2;
		int calculatedPts = okCupid.calcPoints(aAcceptAns, aImpPts, bAns);
		assertEquals(10,calculatedPts);
	}
	
	@Test 
	public void calcPointsUnmatchedAnswersTest(){
		List<Integer> aAcceptAns = new ArrayList<Integer>();
		aAcceptAns.add(2);
		aAcceptAns.add(3);
		int aImpPts = 10;
		int bAns = 5;
		int calculatedPts = okCupid.calcPoints(aAcceptAns, aImpPts, bAns);
		assertEquals(0,calculatedPts);
	}
	
	@Test
	public void calcMaxPointsTest(){
		int [] impPts = new int[3];
		impPts[0] = 10;
		impPts[1] = 50;
		impPts[2] = 150;
		int calculatedMaxPts = okCupid.calcMaxPoints(impPts);
		assertEquals(210, calculatedMaxPts);
	}
	
	@Test
	public void getProfileObjectTest(){
		when(profileMock.getId()).thenReturn(Integer.valueOf(1));
		Map<Integer,Profile> verifyMap = new HashMap<>();
		List<Profile> profileList = new ArrayList<>();
		profileList.add(profileMock);
		Profiles profiles = new Profiles(profileList);
		verifyMap.put(1, profileMock);
		Map<Integer, Profile> generatedHashMap = okCupid.getProfileObject(profiles);
		assertEquals(verifyMap.isEmpty(),generatedHashMap.isEmpty());
		assertEquals(verifyMap.keySet().size(),generatedHashMap.keySet().size());
		assertNotNull(generatedHashMap.values());
		assertEquals(generatedHashMap.get(Integer.valueOf(1)).getId(), 1);
		assertEquals(generatedHashMap.get(1), profileMock);
	}
	
	@Test
	public void calculateSTest(){
		ArrayList<Answers> answers = new ArrayList<>();
		answers.add(answersMock);
		when(profileMock.getAnswers()).thenReturn(answers);
		when(profileMock2.getAnswerIdMatched(1)).thenReturn(answersMock);
		
		int calculatedMatch = okCupid.calculateS(profileMock, profileMock2);
		assertNotNull(calculatedMatch);
		assertEquals(0, calculatedMatch);
	}
	
	@Test
	public void calculateMatchPointsTest(){
		ArrayList<Answers> answers = new ArrayList<>();
		answers.add(answersMock);
		ArrayList<Integer> acceptableAnswers = new ArrayList<>();
		acceptableAnswers.add(1);
		when(profileMock.getAnswers()).thenReturn(answers);
		when(answersMock.getquestionId()).thenReturn(1);
		when(profileMock2.getAnswerIdMatched(1)).thenReturn(answersMock);
		when(answersMock.getImportanceValue()).thenReturn(10);
		when(answersMock.getAcceptableAnswers()).thenReturn(acceptableAnswers);
		when(answersMock.getAnswer()).thenReturn(1);
		
		float calculatedMatchPoints = okCupid.calculateMatchPoints(profileMock, profileMock2);
		assertNotNull(calculatedMatchPoints);
		assertEquals(1.0, calculatedMatchPoints, 0.0f);
	}
	
	@Test
	public void getTopTenTest(){
		List<Match> listofMatches = new ArrayList<Match>();
	}
}
