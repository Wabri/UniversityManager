package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {

	private Student student;
	private MailService mailService;

	@Before
	public void init() {
		mailService = mock(MailService.class);
		student = createNewTestStudent("Name", "Surname", "ID");

		when(mailService.getMail(student)).thenReturn("Mail");
	}

	@Test
	public void testNewStudentHaveData() {
		String name = "Test Name";
		String surname = "Test Surname";
		String id = "IdTest";
		student = createNewTestStudent(name, surname, id);

		assertEquals(name, student.getName());
		assertEquals(surname, student.getSurname());
		assertEquals(id, student.getId());
	}
	
	@Test
	public void testAskMailWhenMailIsNull() {
		assertMailStudent("Mail");
	}

	@Test
	public void testAskMailWhenMailIsNotNull() {
		String mail = "Test Mail";
		student.setMail(mail);

		assertMailStudent(mail);
	}

	@Test
	public void testNewStudentHaveNoTutor() {
		assertEquals(null, student.getIdTutor());
	}

	@Test
	public void testNewStudentHasNoEnrolledCourse() {
		assertTrue(student.getEnrolledCourse().isEmpty());
	}

	@Test
	public void testAddSingleEnrolledCourse() {
		String id = "IdCourseTest";
		student.addEnrolledCourse(createTestCourse(id));

		assertEquals(1, student.getEnrolledCourse().size());
		assertEquals(id, student.getEnrolledCourse().get(0).getId());
	}

	@Test(expected = NoEnrolledCourseError.class)
	public void testRemoveEnrolledCourseWhenListIsEmptyThrowException() {
		student.removeEnrolledCourse("idTest");

		assertEquals(0, student.getEnrolledCourse().size());
	}

	@Test
	public void testRemoveEnrolledCourseWhenListIsNotEmpty() {
		student.addEnrolledCourse(createTestCourse("Id0"));
		student.removeEnrolledCourse("Id0");

		assertEquals(0, student.getEnrolledCourse().size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveEnrolledCourseWhenIdIsNotRightThrowException() {
		student.addEnrolledCourse(createTestCourse("Id0"));
		student.removeEnrolledCourse("Id1");

		assertEquals(1, student.getEnrolledCourse().size());
	}

	@Test
	public void testRemoveEnrolledCourseStopWhenFindTheRightCourse() {
		student.addEnrolledCourse(createTestCourse("ID0"));
		student.addEnrolledCourse(createTestCourse("ID1"));
		student.removeEnrolledCourse("ID1");
		
		assertEquals(1, student.getEnrolledCourse().size());
		assertEquals("ID0", student.getEnrolledCourse().get(0).getId());
	}

	private Course createTestCourse(String id) {
		return new Course(id, "NameCourseTest", createTestTeacher());
	}

	private Teacher createTestTeacher() {
		return new Teacher("nameTeacherTest", "surnameTeacherTest", "IdTeacherTest", mailService);
	}

	private void assertMailStudent(String expected) {
		student.askMail();
		assertEquals(expected, student.getMail());
	}

	private Student createNewTestStudent(String name, String surname, String id) {
		return new Student(name, surname, id, mailService);
	}

}
