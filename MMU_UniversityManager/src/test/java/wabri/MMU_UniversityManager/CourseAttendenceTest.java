package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CourseAttendenceTest {

	private CourseAttendence courseAttendence;
	
	@Before
	public void init() {
		courseAttendence = createNewTestCourseAttendence();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	private CourseAttendence createNewTestCourseAttendence() {
		return new CourseAttendence();
	}

}
