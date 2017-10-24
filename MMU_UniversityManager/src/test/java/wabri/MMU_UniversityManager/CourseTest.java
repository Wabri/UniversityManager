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
	
	@Test
	public void testNewCourseHaveAName() {
		String name = "nameTest";
		course = createNewTestCourse("",name);
		
		assertEquals(name, course.getName());
	}

	private Course createNewTestCourse(String id, String name) {
		return new Course(id,name);
	}

	private Course createNewTestCourse(String id) {
		return new Course(id, "");
	}
	
}
