package wabri.MMU_UniversityManager.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import wabri.MMU_UniversityManager.Course;
import wabri.MMU_UniversityManager.CourseAttendence;
import wabri.MMU_UniversityManager.Student;

public class CourseAttendenceTest {

	private CourseAttendence courseAttendence;
	private Student student;
	private Course course;
	
	@Before
	public void init() {
		student=mock(Student.class);
		course = mock(Course.class);
		courseAttendence = createNewTestCourseAttendence(student,course);
		
		when(student.getId()).thenReturn("idStudentTest");
		when(course.getId()).thenReturn("idCourseTest");
	}
	
	@Test
	public void testCourseAttendenceHasReferenceOfStudent() {
		assertEquals(student.getId(), courseAttendence.getIdStudent());
	}
	
	@Test
	public void testCourseAttendenceHasReferenceOfCourse() {
		assertEquals(course.getId(), courseAttendence.getIdCourse());
	}

	private CourseAttendence createNewTestCourseAttendence(Student student, Course course) {
		return new CourseAttendence(student, course);
	}

}
