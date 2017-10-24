package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CourseAttendenceTest {

	private CourseAttendence courseAttendence;
	private Student student;
	private Course course;
	
	@Before
	public void init() {
		student=mock(Student.class);
		course = mock(Course.class);
		courseAttendence = createNewTestCourseAttendence(student,course);
		
		when(student.getId()).thenReturn("idTest");
	}
	
	@Test
	public void testCourseAttendenceHasReferenceOfStudent() {
		assertEquals(student.getId(), courseAttendence.getIdStudent());
	}
	
	@Test
	public void testCourseAttendenceHasReferenceOfCourse() {
		
	}

	private CourseAttendence createNewTestCourseAttendence(Student student, Course course) {
		return new CourseAttendence(student, course);
	}

}
