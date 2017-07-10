import org.junit.Test;

import static org.junit.Assert.*;

public class AnswersTest {
	
	@Test
	public void getZeroImportanceValueTest() {
		Answers answers = new Answers(0,0,null,0);
		assertEquals(0,answers.getImportanceValue());
	}
}
