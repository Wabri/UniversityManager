package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CourseRequestTest {
	
	private CourseRequest courseRequest;
	private Course course;
	private Student student;

	@Before
	public void init() {
		course = mock(Course.class);
		student = mock(Student.class);
		courseRequest = createTestCourseRequest(student, course);
		
		when(student.getId()).thenReturn("idStudent");
		when(course.getId()).thenReturn("idCourse");
	}
	
	@Test
	public void testNewCourseRequestHasReferenceIdStudent() {		
		assertEquals(student.getId(), courseRequest.getIdStudent());
	}
	
	@Test
	public void testNewCourseRequestHasReferenceIdCourse() {
		assertEquals(course.getId(), courseRequest.getIdCourse());
	}
	
	private CourseRequest createTestCourseRequest(Student student, Course course) {
		return new CourseRequest(student,course);
	}

}
