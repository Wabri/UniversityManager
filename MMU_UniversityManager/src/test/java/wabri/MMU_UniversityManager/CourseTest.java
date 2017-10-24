package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CourseTest {

	private Course course;
	
	@Before
	public void init() {
		course = createNewTestCourse("IDTest");
	}

	@Test
	public void testNewCourseHaveAId() {
		String id = "IDTest";
		course = createNewTestCourse(id);
		
		assertEquals(id, course.getId());
	}

	private Course createNewTestCourse(String id) {
		return new Course(id);
	}
	
}
