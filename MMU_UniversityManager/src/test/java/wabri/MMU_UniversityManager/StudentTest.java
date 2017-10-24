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
	public void testNewStudentHaveAName() {
		String name = "Test Name";
		student = createNewTestStudent(name, "", "");

		assertEquals(name, student.getName());
	}

	@Test
	public void testNewStudentHaveASurname() {
		String surname = "Test Surname";
		student = createNewTestStudent("", surname, "");

		assertEquals(surname, student.getSurname());
	}

	@Test
	public void testNewStudentHaveAnId() {
		String id = "IdTest";
		student = createNewTestStudent("", "", id);

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
	public void testNoEnrolledCourse() {
		assertTrue(student.getEnrolledCourse().isEmpty());
	}
	
	@Test
	public void testAddSingleEnrolledCourse() {
		student.addEnrolledCourse(createTestCourse("IdCourseTest"));
		
		assertEquals(1, student.getEnrolledCourse().size());
	}
	
	@Test (expected = NoEnrolledCourseError.class)
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
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testRemoveEnrolledCourseWhenIdIsNotRightThrowException() {
		student.addEnrolledCourse(createTestCourse("Id0"));
		student.removeEnrolledCourse("Id1");
		
		assertEquals(1, student.getEnrolledCourse().size());
	}

	private Course createTestCourse(String id) {
		return new Course(id,"NameCourseTest",createTestTeacher());
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
