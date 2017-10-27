package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CourseTest {

	private Course course;
	private Teacher teacher;

	@Before
	public void init() {
		teacher = mock(Teacher.class);
		course = createNewTestCourse("ID", "name", teacher);
	}

	@Test
	public void testNewCourseHasInformation() {
		String id = "IDTest";
		String name = "nameTest";
		course = createNewTestCourse(id, name,teacher);

		assertEquals(id, course.getId());
		assertEquals(name, course.getName());
		assertEquals(teacher, course.getTeacher());
	}

	@Test
	public void testMailOfCourseIsTheTeacherMail() {
		String mail = "MailTest";
		when(teacher.getMail()).thenReturn(mail);

		assertEquals(mail, course.getMail());
	}

	@Test
	public void testCourseHasGetIdTeacher() {
		String id = "idTeacherTest";
		when(teacher.getId()).thenReturn(id);

		assertEquals(id, course.getIdTeacher());
	}
	
	@Test
	public void testListOfStudentsEnrollHasNoEnrolledStudents() {
		assertEquals(0, course.getEnrolledStudent().size());
	}

	private Course createNewTestCourse(String id, String name, Teacher teacher) {
		return new Course(id, name, teacher);
	}

}
