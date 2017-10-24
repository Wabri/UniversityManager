package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TutorRequestTest {

	private TutorRequest tutorRequest;
	
	@Before
	public void init() {
		tutorRequest = createNewTestTutorRequest();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	private TutorRequest createNewTestTutorRequest() {
		return new TutorRequest();
	}

}
