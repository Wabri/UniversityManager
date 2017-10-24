package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CourseAttendenceTest {

	private CourseAttendence courseAttendence;
	private Student student;
	private Teacher teacher;
	
	@Before
	public void init() {
		student=mock(Student.class);
		teacher = mock(Teacher.class);
		courseAttendence = createNewTestCourseAttendence(student,teacher);
		
		when(student.getId()).thenReturn("idTest");
	}
	
	@Test
	public void testCourseAttendenceHasReferenceOfStudent() {
		assertEquals(student.getId(), courseAttendence.getIdStudent());
	}

	private CourseAttendence createNewTestCourseAttendence(Student student, Teacher teacher) {
		return new CourseAttendence(student, teacher);
	}

}
