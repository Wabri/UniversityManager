package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CourseTest {

	private Course course;
	private Teacher teacher;
	private MailService mailService;

	@Before
	public void init() {
		teacher = mock(Teacher.class);
		mailService = mock(MailService.class);
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
		assertEnrolledStudents(0);
	}

	@Test
	public void testAddEnrolledStudent() {
		course.addEnrolledStudent(createNewTestStudent());
		
		assertEnrolledStudents(1);
	}
	
	@Test (expected = CourseAttendenceAlreadyActive.class)
	public void testAddEnrolledStudentWhenAlreadyStudentIsInTheListThrowError() {
		Student student = createNewTestStudent();
		course.addEnrolledStudent(student);
		course.addEnrolledStudent(student);
		
		assertEnrolledStudents(1);
	}

	private void assertEnrolledStudents(int expected) {
		assertEquals(expected, course.getEnrolledStudent().size());
	}
	
	private Student createNewTestStudent() {
		return new Student("nameTestStudent", "surnameTestStudent", "idTestStudent", mailService);
	}

	private Course createNewTestCourse(String id, String name, Teacher teacher) {
		return new Course(id, name, teacher);
	}

}
